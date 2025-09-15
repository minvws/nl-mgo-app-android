package nl.rijksoverheid.mgo.feature.digid

import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import nl.rijksoverheid.mgo.data.digid.DigidRepository
import nl.rijksoverheid.mgo.framework.util.base64.Base64Util
import timber.log.Timber
import javax.inject.Inject

/**
 * The [ViewModel] for [DigidLoginScreen].
 *
 * @param digidRepository The [DigidRepository] that is responsible for authenticating with DigiD.
 * @param base64Util The [Base64Util] used to decode the response that is returned from the authentication process.
 */
@HiltViewModel
internal class DigidLoginScreenViewModel
  @Inject
  constructor(
    private val digidRepository: DigidRepository,
    private val base64Util: Base64Util,
  ) : ViewModel() {
    private val _viewState = MutableStateFlow(DigidLoginScreenViewState(false))
    val viewState = _viewState.asStateFlow()

    private val _navigateToUrl = MutableSharedFlow<String>(extraBufferCapacity = 1)
    val navigateToUrl = _navigateToUrl.asSharedFlow()

    private val _loginFailed = MutableSharedFlow<Unit>(extraBufferCapacity = 1)
    val loginFailed = _loginFailed.asSharedFlow()

    private val _loginFinished = MutableSharedFlow<Unit>(extraBufferCapacity = 1)
    val loginFinished = _loginFinished.asSharedFlow()

    /**
     * Start the authentication process with DigiD. Reflect the current state in the UI, and if successful open the browser to start the
     * authentication.
     */
    fun login() {
      viewModelScope.launch {
        _viewState.update { viewState -> viewState.copy(loading = true) }
        digidRepository
          .login()
          .onSuccess { url ->
            _viewState.update { viewState -> viewState.copy(loading = false) }
            _navigateToUrl.tryEmit(url)
          }.onFailure { error ->
            _viewState.update { viewState -> viewState.copy(loading = false) }
            Timber.e(error, "Failed to get url to login to digid")
          }
      }
    }

    /**
     * Called when returning from the authentication process in the browser.
     * Currently only reads the "userinfo" object, but does not do anything with it yet.
     *
     * @param uriString The uri of the deeplink that the app opened, containing the user info as a query parameter.
     */
    fun handleDeeplink(uriString: String?) {
      viewModelScope.launch {
        val uri = uriString?.toUri()
        val userInfoBase64 = uri?.getQueryParameter("userinfo")
        if (userInfoBase64 == null) {
          _loginFailed.tryEmit(Unit)
        } else {
          val userInfo = base64Util.decode(userInfoBase64)
          Timber.v("User info: $userInfo")
          _loginFinished.tryEmit(Unit)
        }
      }
    }
  }
