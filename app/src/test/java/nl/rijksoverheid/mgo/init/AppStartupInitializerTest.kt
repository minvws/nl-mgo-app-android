import android.content.Context
import dagger.hilt.android.EarlyEntryPoints
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import nl.rijksoverheid.mgo.data.fhirParser.js.JsRuntimeRepository
import nl.rijksoverheid.mgo.framework.featuretoggle.dataSource.FeatureToggleLocalDataSource
import nl.rijksoverheid.mgo.framework.featuretoggle.repository.FeatureToggleRepository
import nl.rijksoverheid.mgo.framework.storage.file.CacheFileStore
import nl.rijksoverheid.mgo.init.AppStartupInitializer
import nl.rijksoverheid.mgo.init.AppStartupInitializerEntryPoint
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AppStartupInitializerTest {
  private lateinit var initializer: AppStartupInitializer

  private val mockContext: Context = mockk(relaxed = true)
  private val mockAppContext: Context = mockk(relaxed = true)

  private val mockEntryPoint: AppStartupInitializerEntryPoint = mockk()
  private val mockFeatureToggleRepo: FeatureToggleRepository = mockk()
  private val mockFeatureToggleLocalDataSource: FeatureToggleLocalDataSource = mockk(relaxed = true)
  private val mockJsRuntimeRepo: JsRuntimeRepository = mockk(relaxed = true)
  private val mockCacheFileStore: CacheFileStore = mockk(relaxed = true)

  @Before
  fun setup() {
    initializer = AppStartupInitializer()

    every { mockContext.applicationContext } returns mockAppContext
    every { mockEntryPoint.featureToggleRepository() } returns mockFeatureToggleRepo
    every { mockEntryPoint.featureToggleLocalDataSource() } returns mockFeatureToggleLocalDataSource
    every { mockEntryPoint.jsRuntimeRepository() } returns mockJsRuntimeRepo
    every { mockEntryPoint.cacheFileStore() } returns mockCacheFileStore
    every { mockFeatureToggleRepo.getAll() } returns listOf()
    mockkStatic(EarlyEntryPoints::class)
    every {
      EarlyEntryPoints.get(mockAppContext, AppStartupInitializerEntryPoint::class.java)
    } returns mockEntryPoint
  }

  @After
  fun tearDown() {
    unmockkStatic(EarlyEntryPoints::class)
  }

  @Test
  fun `initializer should init feature toggles`() =
    runTest {
      initializer.create(mockContext)

      coVerify { mockFeatureToggleLocalDataSource.init(any()) }
    }

  @Test
  fun `initializer should load js runtime`() =
    runTest {
      initializer.create(mockContext)

      coVerify { mockJsRuntimeRepo.load() }
    }

  @Test
  fun `initializer should clear cache`() =
    runTest {
      initializer.create(mockContext)

      coVerify { mockCacheFileStore.deleteAll() }
    }

  @Test
  fun `dependencies should be empty`() {
    assert(initializer.dependencies().isEmpty())
  }
}
