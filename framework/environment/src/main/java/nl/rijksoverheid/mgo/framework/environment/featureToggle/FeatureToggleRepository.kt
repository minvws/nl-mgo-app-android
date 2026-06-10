package nl.rijksoverheid.mgo.framework.environment.featureToggle

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import nl.rijksoverheid.mgo.framework.storage.keyvalue.MgoKeyValueStorage
import javax.inject.Inject
import javax.inject.Named

val FEATURE_TOGGLE_FLAG_SECURE =
  FeatureToggle(
    id = "FEATURE_TOGGLE_FLAG_SECURE",
    name = "Flag secure",
    description = "Schermafbeeldingen zijn uitgeschakeld in productie om privacy te waarborgen. Schakel dit in om screenshots te kunnen maken.",
    initialValue = true,
  )

val FEATURE_TOGGLE_SKIP_DIGID_LOGIN =
  FeatureToggle(
    id = "FEATURE_TOGGLE_SKIP_DIGID_LOGIN",
    name = "Skip login",
    description = "Sla de DigiD-inlogstap over bij het eerste opstarten van de app.",
    initialValue = false,
  )

private val featureToggles = listOf(FEATURE_TOGGLE_FLAG_SECURE, FEATURE_TOGGLE_SKIP_DIGID_LOGIN)

class FeatureToggleRepository
  @Inject
  constructor(
    @Named("sharedPreferencesMgoKeyValueStorage") private val keyValueStore: MgoKeyValueStorage,
  ) {
    fun get(): List<FeatureToggleEntry<*>> =
      featureToggles.map { toggle ->
        FeatureToggleEntry(
          toggle = toggle,
          value = keyValueStore.get(toggle.id) ?: toggle.initialValue,
        )
      }

    fun <T : Any> get(toggle: FeatureToggle<T>): FeatureToggleEntry<T> =
      FeatureToggleEntry(
        toggle = toggle,
        value = keyValueStore.get(toggle.id) ?: toggle.initialValue,
      )

    fun observe(): Flow<List<FeatureToggleEntry<*>>> {
      val flows = featureToggles.map { toggle -> observe(toggle) }
      return combine(flows) { it.toList() }
    }

    fun <T : Any> observe(toggle: FeatureToggle<T>): Flow<FeatureToggleEntry<T>> =
      keyValueStore.observe<T>(toggle.id).map { value ->
        FeatureToggleEntry(
          toggle = toggle,
          value = value ?: toggle.initialValue,
        )
      }

    fun <T : Any> set(
      toggle: FeatureToggle<out Any?>,
      value: T,
    ) {
      keyValueStore.save(toggle.id, value)
    }
  }
