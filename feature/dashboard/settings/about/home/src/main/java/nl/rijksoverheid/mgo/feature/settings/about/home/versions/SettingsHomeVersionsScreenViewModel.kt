package nl.rijksoverheid.mgo.feature.settings.about.home.versions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import nl.rijksoverheid.mgo.data.pft.PftRepository
import nl.rijksoverheid.mgo.framework.util.file.ReadLocalFile
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
internal class SettingsHomeVersionsScreenViewModel
  @Inject
  constructor(
    @Named("ioDispatcher") private val ioDispatcher: CoroutineDispatcher,
    private val pftRepository: PftRepository,
    private val readLocalFile: ReadLocalFile,
  ) : ViewModel() {
    private val initialViewState =
      SettingsHomeVersionsScreenViewState(
        hcimPackageVersion = null,
        hcimPackageDate = null,
        hcimPackageGitRef = null,
        healthCategoriesConfigVersion = null,
        healthCategoriesConfigDate = null,
        healthCategoriesConfigGitRef = null,
        patientFriendlyTermsETag = null,
      )
    private val _viewState =
      MutableStateFlow(initialViewState)

    val viewState = _viewState.stateIn(viewModelScope, SharingStarted.Lazily, initialViewState)

    init {
      viewModelScope.launch(ioDispatcher) {
        launch { setHcimPackage() }
        launch { setHealthCategoriesConfig() }
        launch { setETag() }
      }
    }

    private fun setHcimPackage() {
      // Get json file as string
      val hcimPackageJsonString = readLocalFile("mgo-fhir-data.iife.version.json")

      // Parse to json
      val hcimPackageJson = Json.parseToJsonElement(hcimPackageJsonString).jsonObject

      // Update view state with contents of json
      _viewState.update { viewState ->
        viewState.copy(
          hcimPackageVersion = hcimPackageJson["version"]?.jsonPrimitive?.content,
          hcimPackageDate = hcimPackageJson["created"]?.jsonPrimitive?.content,
          hcimPackageGitRef = hcimPackageJson["git_ref"]?.jsonPrimitive?.content?.take(7),
        )
      }
    }

    private fun setHealthCategoriesConfig() {
      // Get json file as string
      val healthCategoriesConfigJsonString = readLocalFile("version.json")

      // Parse to json
      val healthCategoriesConfigJson = Json.parseToJsonElement(healthCategoriesConfigJsonString).jsonObject

      // Update view state with contents of json
      _viewState.update { viewState ->
        viewState.copy(
          healthCategoriesConfigVersion = healthCategoriesConfigJson["version"]?.jsonPrimitive?.content,
          healthCategoriesConfigDate = healthCategoriesConfigJson["created"]?.jsonPrimitive?.content,
          healthCategoriesConfigGitRef = healthCategoriesConfigJson["git_ref"]?.jsonPrimitive?.content?.take(7),
        )
      }
    }

    private suspend fun setETag() {
      pftRepository.observeETag().collectLatest { eTag ->
        _viewState.update { viewState -> viewState.copy(patientFriendlyTermsETag = eTag) }
      }
    }
  }
