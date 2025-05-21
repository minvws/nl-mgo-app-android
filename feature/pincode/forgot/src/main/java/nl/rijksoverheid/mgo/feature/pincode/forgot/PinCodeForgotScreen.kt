package nl.rijksoverheid.mgo.feature.pincode.forgot

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.flow.collectLatest
import nl.rijksoverheid.mgo.component.mgo.MgoAlertDialog
import nl.rijksoverheid.mgo.component.mgo.MgoBottomButton
import nl.rijksoverheid.mgo.component.mgo.MgoBottomButtons
import nl.rijksoverheid.mgo.component.mgo.MgoLargeTopAppBar
import nl.rijksoverheid.mgo.component.mgo.getMgoAppBarScrollBehaviour
import nl.rijksoverheid.mgo.component.theme.DefaultPreviews
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.framework.copy.R
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

/**
 * Composable that shows the screen where you can reset the pin code if you forgot it. Resetting here means clearing locally stored data
 * and restarting the app again.
 *
 * @param onNavigateToPinCodeDeleted Called when requested to navigate to the pin code deleted screen.
 * @param onNavigateBack Called when requested to navigate back.
 */
@Composable
fun PinCodeForgotScreen(
  onNavigateToPinCodeDeleted: () -> Unit,
  onNavigateBack: () -> Unit,
) {
  val viewModel: PinCodeForgotScreenViewModel = hiltViewModel()
  LaunchedEffect(Unit) {
    viewModel.navigateToPinCodeCreate.collectLatest {
      onNavigateToPinCodeDeleted()
    }
  }
  PinCodeForgotScreenContent(
    onCreateNewAccount = { viewModel.createNewAccount() },
    onNavigateBack = onNavigateBack,
  )
}

@Composable
private fun PinCodeForgotScreenContent(
  onCreateNewAccount: () -> Unit,
  onNavigateBack: () -> Unit,
) {
  var showDialog by remember { mutableStateOf(false) }
  if (showDialog) {
    MgoAlertDialog(
      heading = stringResource(id = R.string.forgot_pincode_dialog_heading),
      subHeading = stringResource(id = R.string.forgot_pincode_dialog_subheading),
      onDismissRequest = { showDialog = false },
      positiveButtonText = stringResource(id = R.string.common_yes),
      onClickPositiveButton = {
        onCreateNewAccount()
        showDialog = false
      },
      negativeButtonText = stringResource(id = R.string.common_no),
      onClickNegativeButton = {
        showDialog = false
      },
    )
  }

  val scrollState = rememberScrollState()
  val scrollBehavior = getMgoAppBarScrollBehaviour(canScrollForward = scrollState.canScrollForward, canScrollBackward = scrollState.canScrollBackward)

  Scaffold(
    modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
    topBar = {
      MgoLargeTopAppBar(
        title = stringResource(id = CopyR.string.forgot_pincode_heading),
        onNavigateBack = onNavigateBack,
        scrollBehavior = scrollBehavior,
      )
    },
    contentWindowInsets = WindowInsets.statusBars,
    content = { contentPadding ->
      Column(modifier = Modifier.padding(contentPadding)) {
        Column(
          modifier =
            Modifier
              .weight(1f)
              .verticalScroll(scrollState)
              .padding(16.dp),
        ) {
          Text(
            modifier = Modifier.padding(top = 16.dp),
            text = stringResource(id = CopyR.string.forgot_pincode_subheading),
            style = MaterialTheme.typography.bodyMedium,
          )
        }

        MgoBottomButtons(
          primaryButton =
            MgoBottomButton(
              text = stringResource(id = CopyR.string.common_cancel),
              onClick = onNavigateBack,
            ),
          secondaryButton =
            MgoBottomButton(
              text = stringResource(id = CopyR.string.forgot_pincode_button),
              onClick = { showDialog = true },
            ),
          isElevated = scrollState.canScrollForward,
        )
      }
    },
  )
}

@DefaultPreviews
@Composable
internal fun PinCodeForgotScreenPreview() {
  MgoTheme {
    PinCodeForgotScreenContent(
      onCreateNewAccount = {},
      onNavigateBack = {},
    )
  }
}
