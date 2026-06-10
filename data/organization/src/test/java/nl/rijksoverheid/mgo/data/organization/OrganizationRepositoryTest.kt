package nl.rijksoverheid.mgo.data.organization

import app.cash.turbine.test
import io.mockk.InternalPlatformDsl.toStr
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import nl.rijksoverheid.mgo.component.organization.TEST_MGO_ORGANIZATION
import nl.rijksoverheid.mgo.data.organization.api.OrganizationApiResponse
import nl.rijksoverheid.mgo.framework.test.getResource
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class OrganizationRepositoryTest {
  private val json = Json { ignoreUnknownKeys = true }

  private val apiClient = TestOrganizationApiClient()
  private lateinit var organisationRepository: OrganizationRepository

  @Before
  fun setup() {
    organisationRepository = createOrganizationRepositoryForJvm(apiClient)
  }

  @Test
  fun runBenchmark() =
    runTest {
      // Sync benchmark organizations
      apiClient.setOrganizationsResult(
        Result.success(
          OrganizationApiResponse(
            response = getResource("benchmark-organizations.json"),
            cached = false,
          ),
        ),
      )

      // Sync benchmark endpoints
      apiClient.setEndpointsResult(
        Result.success(
          OrganizationApiResponse(
            response = getResource("benchmark-endpoints.json"),
            cached = false,
          ),
        ),
      )

      // Sync organizations
      organisationRepository.sync()

      // Read queries file
      val queriesJson =
        javaClass.classLoader!!
          .getResourceAsStream("benchmark-queries.json")
          .bufferedReader()
          .use { it.readText() }
      val queries: List<BenchmarkQuery> = json.decodeFromString(queriesJson)

      // Search for each query to create benchmark
      val meanReciprocalRanks = mutableListOf<Float>()
      val queryResults = mutableListOf<BenchmarkQueryResult>()
      for (benchmarkQuery in queries) {
        val organisations = organisationRepository.search(query = benchmarkQuery.query, context = coroutineContext).first()
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
      assertEquals(0.8811686f, benchmarkResult.meanReciprocalRank)
    }

  @Test
  fun testSyncCached() =
    runTest {
      // Given: Organizations are cached
      apiClient.setOrganizationsResult(
        Result.success(
          OrganizationApiResponse(
            response = getResource("organizations.json"),
            cached = true,
          ),
        ),
      )

      // Given: Endpoints are cached
      apiClient.setEndpointsResult(
        Result.success(
          OrganizationApiResponse(
            response = getResource("endpoints.json"),
            cached = true,
          ),
        ),
      )

      // When: Calling sync
      val success = organisationRepository.sync()

      // Then: Sync is success
      assertTrue(success)
    }

  @Test
  fun testSyncFailed() =
    runTest {
      // Given: Organizations are cached
      apiClient.setOrganizationsResult(
        Result.failure(IllegalStateException("Something went wrong")),
      )

      // Given: Endpoints are cached
      apiClient.setEndpointsResult(Result.failure(IllegalStateException("Something went wrong")))

      // When: Calling sync
      val success = organisationRepository.sync()

      // Then: Sync is success
      assertFalse(success)
    }

  @Test
  fun testSaved() =
    runTest {
      // Add and save organization
      organisationRepository.addAndSave(TEST_MGO_ORGANIZATION)

      // Mark as saved
      organisationRepository.save(TEST_MGO_ORGANIZATION.id)

      // Verify organization is added
      organisationRepository.getSaved(coroutineContext).test {
        assertEquals(1, awaitItem().size)
      }
    }

  @Test
  fun testDelete() =
    runTest {
      // Add and save organization
      organisationRepository.addAndSave(TEST_MGO_ORGANIZATION)

      // Mark as saved
      organisationRepository.save(TEST_MGO_ORGANIZATION.id)

      // Mark as deleted
      organisationRepository.delete(TEST_MGO_ORGANIZATION.id)

      // Verify organization is deleted
      organisationRepository.getSaved(coroutineContext).test {
        assertEquals(0, awaitItem().size)
      }
    }

  @Test
  fun testDeleteAllSaved() =
    runTest {
      // Add and save organizations
      organisationRepository.addAndSave(TEST_MGO_ORGANIZATION)
      organisationRepository.addAndSave(TEST_MGO_ORGANIZATION.copy(id = "2"))

      // Mark as saved
      organisationRepository.save(TEST_MGO_ORGANIZATION.id)
      organisationRepository.save("2")

      // Delete all saved
      organisationRepository.deleteAllSaved()

      // Verify no organizations exist
      organisationRepository.getSaved(coroutineContext).test {
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
