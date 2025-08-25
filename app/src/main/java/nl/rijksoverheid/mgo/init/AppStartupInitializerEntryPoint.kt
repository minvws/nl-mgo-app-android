package nl.rijksoverheid.mgo.init

import dagger.hilt.InstallIn
import dagger.hilt.android.EarlyEntryPoint
import dagger.hilt.components.SingletonComponent
import nl.rijksoverheid.mgo.data.fhirParser.js.JsRuntimeRepository
import nl.rijksoverheid.mgo.framework.featuretoggle.dataSource.FeatureToggleLocalDataSource
import nl.rijksoverheid.mgo.framework.featuretoggle.repository.FeatureToggleRepository
import nl.rijksoverheid.mgo.framework.storage.file.CacheFileStore

@EarlyEntryPoint
@InstallIn(SingletonComponent::class)
interface AppStartupInitializerEntryPoint {
  fun featureToggleRepository(): FeatureToggleRepository

  fun featureToggleLocalDataSource(): FeatureToggleLocalDataSource

  fun cacheFileStore(): CacheFileStore

  fun jsRuntimeRepository(): JsRuntimeRepository
}
