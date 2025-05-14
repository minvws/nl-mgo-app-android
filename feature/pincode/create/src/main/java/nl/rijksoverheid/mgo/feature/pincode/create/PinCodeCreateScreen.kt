package nl.rijksoverheid.mgo.feature.pincode.create

import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import nl.rijksoverheid.mgo.component.mgo.MgoAutoScrollColumn
import nl.rijksoverheid.mgo.component.mgo.MgoLargeTopAppBar
import nl.rijksoverheid.mgo.component.mgo.getMgoAppBarScrollBehaviour
import nl.rijksoverheid.mgo.component.pincode.PinCodeWithKeyboard
import nl.rijksoverheid.mgo.component.theme.DefaultPreviews
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

/**
 * Composable that shows a screen where you can enter a pin code for storage.
 */
@Composable
fun PinCodeCreateScreen(
  hasBackButton: Boolean,
  onPinEntered: (pinCode: List<Int>) -> Unit,
  onNavigateBack: () -> Unit,
) {
  val viewModel: PinCodeCreateScreenViewModel = hiltViewModel()
  LaunchedEffect(Unit) {
    viewModel.navigateToConfirm.collectLatest { pinCode ->
      onPinEntered(pinCode)
    }
  }
  val viewState by viewModel.viewState.collectAsStateWithLifecycle()
  PinCodeCreateScreenContent(
    viewState = viewState,
    hasBackButton = hasBackButton,
    onPinCodeEntered = { pinCode ->
      viewModel.validatePinCode(pinCode)
    },
    onResetError = {
      viewModel.resetError()
    },
    onNavigateBack = onNavigateBack,
  )
}

@Composable
private fun PinCodeCreateScreenContent(
  viewState: PinCodeCreateScreenViewState,
  hasBackButton: Boolean,
  onPinCodeEntered: (pinCode: List<Int>) -> Unit,
  onResetError: () -> Unit,
  onNavigateBack: () -> Unit,
) {
  val scrollState = rememberScrollState()
  val scrollBehavior = getMgoAppBarScrollBehaviour(scrollState.canScrollForward, scrollState.canScrollBackward)
  val coroutineScope = rememberCoroutineScope()
  val focusManager = LocalFocusManager.current
  val subHeadingFocusRequester = remember { FocusRequester() }
  Scaffold(
    modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
    topBar = {
      MgoLargeTopAppBar(
        title = stringResource(id = CopyR.string.pincode_create_heading),
        scrollBehavior = scrollBehavior,
        onNavigateBack = if (hasBackButton) onNavigateBack else null,
      )
    },
    content = { contentPadding ->
      MgoAutoScrollColumn(modifier = Modifier.padding(contentPadding).padding(16.dp), scrollState = scrollState) {
        Text(
          modifier = Modifier.focusRequester(subHeadingFocusRequester).focusable(),
          text = stringResource(id = CopyR.string.pincode_create_subheading),
          style = MaterialTheme.typography.bodyMedium,
        )
        PinCodeWithKeyboard(
          onPinCodeEntered = onPinCodeEntered,
          onResetError = {
            onResetError()
            coroutineScope.launch {
              // Seems to be a bug where if you request focus it only works once.
              // Doing it like this fixes that.
              focusManager.clearFocus()
              delay(100)
              subHeadingFocusRequester.requestFocus()
            }
          },
          error = if (viewState.error) stringResource(CopyR.string.pincode_create_tooweak) else null,
        )
      }
    },
  )
}

@DefaultPreviews
@Composable
internal fun PinCodeCreateScreenPreview() {
  MgoTheme {
    PinCodeCreateScreenContent(
      viewState =
        PinCodeCreateScreenViewState(
          error = false,
        ),
      hasBackButton = true,
      onPinCodeEntered = {},
      onResetError = {},
      onNavigateBack = {},
    )
  }
}

@DefaultPreviews
@Composable
internal fun PinCodeCreateScreenErrorPreview() {
  MgoTheme {
    PinCodeCreateScreenContent(
      viewState =
        PinCodeCreateScreenViewState(
          error = true,
        ),
      hasBackButton = true,
      onPinCodeEntered = {},
      onResetError = {},
      onNavigateBack = {},
    )
  }
}
