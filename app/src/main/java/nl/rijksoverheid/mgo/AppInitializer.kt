package nl.rijksoverheid.mgo

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import nl.rijksoverheid.mgo.data.fhirParser.js.JsRuntimeRepository
import nl.rijksoverheid.mgo.data.healthcare.binary.FhirBinaryRepository
import nl.rijksoverheid.mgo.framework.featuretoggle.dataSource.FeatureToggleLocalDataSource
import nl.rijksoverheid.mgo.framework.featuretoggle.repository.FeatureToggleRepository
import javax.inject.Inject
import javax.inject.Named

class AppInitializer
  @Inject
  constructor(
    private val featureToggleRepository: FeatureToggleRepository,
    private val featureToggleLocalDataSource: FeatureToggleLocalDataSource,
    private val fhirBinaryRepository: FhirBinaryRepository,
    private val jsRuntimeRepository: JsRuntimeRepository,
    @Named("ioDispatcher") private val ioDispatcher: CoroutineDispatcher,
  ) {
    operator fun invoke(coroutineScope: CoroutineScope) {
      // Initialize feature toggles
      runBlocking { featureToggleLocalDataSource.init(featureToggleRepository.getAll()) }

      coroutineScope.launch(ioDispatcher) {
        jsRuntimeRepository.load()

        // Remove any left over downloaded files on each app launch
        launch { fhirBinaryRepository.cleanup() }
      }
    }
  }
