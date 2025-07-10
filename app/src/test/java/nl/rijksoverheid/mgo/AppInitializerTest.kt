package nl.rijksoverheid.mgo

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import nl.rijksoverheid.mgo.data.fhirParser.js.TestJsRuntimeRepository
import nl.rijksoverheid.mgo.framework.featuretoggle.TestFeatureToggleRepository
import nl.rijksoverheid.mgo.framework.featuretoggle.dataSource.FeatureToggleLocalDataSource
import nl.rijksoverheid.mgo.framework.featuretoggle.flagSkipPinFeatureToggle
import nl.rijksoverheid.mgo.framework.storage.keyvalue.TestCacheFileStore
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class AppInitializerTest {
  private val featureToggles = listOf(flagSkipPinFeatureToggle)
  private val featureToggleRepository = TestFeatureToggleRepository(featureToggles)
  private val featureToggleLocalDataSource: FeatureToggleLocalDataSource = mockk()
  private val jsRuntimeRepository = TestJsRuntimeRepository()
  private val cacheFileStore = TestCacheFileStore()
  private val appInitializer =
    AppInitializer(
      featureToggleRepository = featureToggleRepository,
      featureToggleLocalDataSource = featureToggleLocalDataSource,
      jsRuntimeRepository = jsRuntimeRepository,
      ioDispatcher = Dispatchers.Main,
      cacheFileStore = cacheFileStore,
    )

  private val testDispatcher = StandardTestDispatcher()
  private val testScope = TestScope(testDispatcher)

  @OptIn(ExperimentalCoroutinesApi::class)
  @Before
  fun setUp() {
    cacheFileStore.saveFile("test.pdf", contentType = "application/pdf", base64Content = "")
    coEvery { featureToggleLocalDataSource.init(featureToggles) } answers { }
    Dispatchers.setMain(testDispatcher)
  }

  @OptIn(ExperimentalCoroutinesApi::class)
  @After
  fun tearDown() {
    Dispatchers.resetMain()
  }

  @OptIn(ExperimentalCoroutinesApi::class)
  @Test
  fun testAppInit() =
    runTest(testDispatcher) {
      // When app is initialized
      appInitializer(this)

      testScope.runCurrent()

      // Then classes are initialized
      coVerify { featureToggleLocalDataSource.init(featureToggles) }
      assertTrue(jsRuntimeRepository.assertIsLoaded())
      assertTrue(cacheFileStore.assertNoFilesSaved())
    }
}
