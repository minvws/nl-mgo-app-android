package nl.rijksoverheid.mgo.data.organization

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import app.cash.turbine.test
import io.mockk.InternalPlatformDsl.toStr
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class OrganizationRepositoryTest {
  private val json = Json { ignoreUnknownKeys = true }
  private lateinit var context: Context
  private lateinit var organisationRepository: OrganizationRepository

  @Before
  fun setup() {
    context = ApplicationProvider.getApplicationContext()
    val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
    OrganizationsDatabase.Schema.create(driver)
    organisationRepository = OrganizationRepository(driver = driver, context = context)
  }

  @Test
  fun runBenchmark() =
    runTest {
      // Load organizations specific for the benchmark
      organisationRepository.sync("benchmark-organizations.json")

      // Read queries file
      val queriesJson =
        context.assets
          .open("benchmark-queries.json")
          .bufferedReader()
          .use { it.readText() }
      val queries: List<BenchmarkQuery> = json.decodeFromString(queriesJson)

      // Search for each query to create benchmark
      val meanReciprocalRanks = mutableListOf<Float>()
      val queryResults = mutableListOf<BenchmarkQueryResult>()
      for (benchmarkQuery in queries) {
        val organisations = organisationRepository.search(benchmarkQuery.query).first()
        val targetIndex = organisations.indexOfFirst { organisation -> organisation.id == benchmarkQuery.targetId }
        val meanReciprocalRank = if (targetIndex == -1) 0f else 1f / (targetIndex.toFloat().coerceAtLeast(1f))
        val queryResult =
          BenchmarkQueryResult(
            meanReciprocalRank = meanReciprocalRank.toStr(),
            query = benchmarkQuery.query,
            rank = "$targetIndex/${organisations.size}",
            targetId = benchmarkQuery.targetId,
          )
        meanReciprocalRanks.add(meanReciprocalRank)
        queryResults.add(queryResult)
      }

      // Create and print benchmark result
      val benchmarkResult =
        BenchmarkResult(
          failedQueries = queryResults.count { result -> result.rank.contains("-1") },
          meanReciprocalRank = meanReciprocalRanks.average().toFloat(),
          name = "native-search-Android",
          queries = queryResults,
        )
      val benchmarkResultJsonString = Json.encodeToString(benchmarkResult)
      println(benchmarkResultJsonString)

      // Assert the quality of the benchmark
      assertEquals(0.8705706f, benchmarkResult.meanReciprocalRank)
    }

  @Test
  fun testSaved() =
    runTest {
      // Load organizations specific for the benchmark
      organisationRepository.sync("benchmark-organizations.json")

      // Mark Het Huisartsenteam Van Beek-Schrijnemaekers as saved
      organisationRepository.save("agb:01009380")

      // Verify organization is added
      organisationRepository.getSaved().test {
        assertEquals(1, awaitItem().size)
      }
    }

  @Test
  fun testDelete() =
    runTest {
      // Load organizations specific for the benchmark
      organisationRepository.sync("benchmark-organizations.json")

      // Mark Het Huisartsenteam Van Beek-Schrijnemaekers as saved
      organisationRepository.save("agb:01009380")

      // Mark Het Huisartsenteam Van Beek-Schrijnemaekers as deleted
      organisationRepository.delete("agb:01009380")

      // Verify organization is deleted
      organisationRepository.getSaved().test {
        assertEquals(0, awaitItem().size)
      }
    }
}

@Serializable
data class BenchmarkQuery(
  val query: String,
  val targetId: String,
)

@Serializable
data class BenchmarkResult(
  val failedQueries: Int,
  val meanReciprocalRank: Float,
  val name: String,
  val queries: List<BenchmarkQueryResult>,
)

@Serializable
data class BenchmarkQueryResult(
  val meanReciprocalRank: String,
  val query: String,
  val rank: String,
  val targetId: String,
)
