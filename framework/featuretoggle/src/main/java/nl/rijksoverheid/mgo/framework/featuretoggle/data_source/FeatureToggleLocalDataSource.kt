package nl.rijksoverheid.mgo.framework.featuretoggle.data_source

import nl.rijksoverheid.mgo.framework.featuretoggle.FeatureToggleId
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KEY_FLAG_SECURE_ENABLED
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KeyValueStore
import javax.inject.Inject
import javax.inject.Named
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking

internal class FeatureToggleLocalDataSource
    @Inject
    constructor(
        @Named("keyValueStore") private val keyValueStore: KeyValueStore,
    ) : FeatureToggleDataSource {
        val featureToggles =
            mutableMapOf<FeatureToggleId, MutableStateFlow<Boolean>>(
                FeatureToggleId.FlagSecureEnabled to
                    MutableStateFlow(
                        keyValueStore.getBoolean(
                            KEY_FLAG_SECURE_ENABLED,
                        ),
                    ),
            )

        override fun get(id: FeatureToggleId): Boolean {
            return runBlocking { featureToggles[id]?.firstOrNull() == true }
        }

        override fun observe(id: FeatureToggleId): Flow<Boolean> {
            return featureToggles[id] ?: flow { emit(false) }
        }

        override suspend fun set(
            id: FeatureToggleId,
            enabled: Boolean,
        ) {
            keyValueStore.setBoolean(KEY_FLAG_SECURE_ENABLED, enabled)
            featureToggles[id]?.value = enabled
        }
    }
