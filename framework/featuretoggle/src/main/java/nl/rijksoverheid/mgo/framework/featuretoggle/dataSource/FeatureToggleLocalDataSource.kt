package nl.rijksoverheid.mgo.framework.featuretoggle.dataSource

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import nl.rijksoverheid.mgo.framework.featuretoggle.FeatureToggle
import nl.rijksoverheid.mgo.framework.featuretoggle.FeatureToggleId
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KEY_LOCAL_FEATURE_TOGGLES_INITIALISED
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KeyValueStore
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

/**
 * Contains the state of feature toggles.
 *
 * @param keyValueStore The [KeyValueStore] where the feature toggles are stored.
 */
@Singleton
class FeatureToggleLocalDataSource
  @Inject
  constructor(
    @Named("keyValueStore") private val keyValueStore: KeyValueStore,
  ) :
  FeatureToggleDataSource {
    private var flows: Map<FeatureToggleId, MutableStateFlow<Boolean>> = mapOf()

    /**
     * Call this once to initialize the feature toggles.
     * After this is done the [get] and [observe] methods can be used to retrieve the status of the feature toggles.
     *
     * @param featureToggles The list of feature toggles to set.
     */
    suspend fun init(featureToggles: List<FeatureToggle>) {
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
        featureToggles.associate { featureToggle ->
          featureToggle.id to
            MutableStateFlow(
              keyValueStore.getBoolean
                (featureToggle.preferenceKey),
            )
        }
    }

    /**
     * @param id The [FeatureToggleId] of the [FeatureToggle].
     * @return True if the [FeatureToggle] is enabled.
     */
    override fun get(id: FeatureToggleId): Boolean {
      return runBlocking { flows[id]?.value == true }
    }

    /**
     * @param id The [FeatureToggleId] of the [FeatureToggle].
     * @return Emits true if the [FeatureToggle] is enabled.
     */
    override fun observe(id: FeatureToggleId): Flow<Boolean> {
      return flows[id] ?: flow { emit(false) }
    }

    /**
     * Toggle the state of a feature toggle.
     *
     * @param toggle The [FeatureToggle] to change.
     * @param enabled True if the [FeatureToggle] is enabled.
     */
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
