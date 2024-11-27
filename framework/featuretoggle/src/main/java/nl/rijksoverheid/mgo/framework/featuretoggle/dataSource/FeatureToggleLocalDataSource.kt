package nl.rijksoverheid.mgo.framework.featuretoggle.dataSource

import nl.rijksoverheid.mgo.framework.featuretoggle.FeatureToggle
import nl.rijksoverheid.mgo.framework.featuretoggle.FeatureToggleId
import nl.rijksoverheid.mgo.framework.featuretoggle.featureToggles
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KEY_LOCAL_FEATURE_TOGGLES_INITIALISED
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KeyValueStore
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking

@Singleton
class FeatureToggleLocalDataSource
    @Inject
    constructor(
        @Named("keyValueStore") private val keyValueStore: KeyValueStore,
    ) :
    FeatureToggleDataSource {
        private var flows: Map<FeatureToggleId, MutableStateFlow<Boolean>> = mapOf()

        suspend fun init() {
            // If not executed yet, set the default values for the feature toggles in local storage
            val initialized = keyValueStore.getBoolean(KEY_LOCAL_FEATURE_TOGGLES_INITIALISED)
            if (!initialized) {
                for (featureToggle in featureToggles) {
                    keyValueStore.setBoolean(featureToggle.preferenceKey, featureToggle.initialValue)
                }
                keyValueStore.setBoolean(KEY_LOCAL_FEATURE_TOGGLES_INITIALISED, true)
            }

            // Create our flows
            this.flows =
                featureToggles.map { featureToggle ->
                    featureToggle.id to
                        MutableStateFlow(
                            keyValueStore.getBoolean
                                (featureToggle.preferenceKey),
                        )
                }.toMap()
        }

        override fun get(id: FeatureToggleId): Boolean {
            return runBlocking { flows[id]?.value == true }
        }

        override fun observe(id: FeatureToggleId): Flow<Boolean> {
            return flows[id] ?: flow { emit(false) }
        }

        override suspend fun set(
            toggle: FeatureToggle,
            enabled: Boolean,
        ) {
            // Update local storage
            keyValueStore.setBoolean(toggle.preferenceKey, enabled)

            // Update flow
            flows[toggle.id]?.emit(enabled)
        }
    }
