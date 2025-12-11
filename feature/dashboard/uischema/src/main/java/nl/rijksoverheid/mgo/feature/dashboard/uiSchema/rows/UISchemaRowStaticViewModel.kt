package nl.rijksoverheid.mgo.feature.dashboard.uiSchema.rows

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import nl.rijksoverheid.mgo.data.pft.Pft
import nl.rijksoverheid.mgo.data.pft.PftRepository
import nl.rijksoverheid.mgo.data.pft.PftSnomedCode
import javax.inject.Named

@HiltViewModel(assistedFactory = UISchemaRowStaticViewModel.Factory::class)
internal class UISchemaRowStaticViewModel
  @AssistedInject
  constructor(
    @Assisted private val snomedCode: String?,
    private val pftRepository: PftRepository,
    @Named("ioDispatcher") private val ioDispatcher: CoroutineDispatcher,
  ) : ViewModel() {
    @AssistedFactory
    interface Factory {
      fun create(snomedCode: String?): UISchemaRowStaticViewModel
    }

    private val _pft = MutableStateFlow<Pft?>(null)
    val pft = _pft.asStateFlow()

    init {
      if (snomedCode != null) {
        viewModelScope.launch(ioDispatcher) {
          pftRepository.observe(PftSnomedCode(snomedCode)).collectLatest {
            _pft.tryEmit(it)
          }
        }
      }
    }
  }
