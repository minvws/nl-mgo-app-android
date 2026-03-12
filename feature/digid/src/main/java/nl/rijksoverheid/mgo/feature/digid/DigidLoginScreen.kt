package nl.rijksoverheid.mgo.feature.digid

import android.content.Intent
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.util.Consumer
import androidx.fragment.app.FragmentActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.collectLatest
import nl.rijksoverheid.mgo.component.mgo.MgoAlertDialog
import nl.rijksoverheid.mgo.component.mgo.MgoAutoScrollColumn
import nl.rijksoverheid.mgo.component.mgo.MgoBottomButton
import nl.rijksoverheid.mgo.component.mgo.MgoBottomButtons
import nl.rijksoverheid.mgo.component.mgo.MgoHtmlText
import nl.rijksoverheid.mgo.component.mgo.MgoLargeTopAppBar
import nl.rijksoverheid.mgo.component.mgo.MgoTopAppBar
import nl.rijksoverheid.mgo.component.mgo.getMgoAppBarScrollBehaviour
import nl.rijksoverheid.mgo.component.theme.ActionsGhostText
import nl.rijksoverheid.mgo.component.theme.DefaultPreviews
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.framework.util.launchBrowser
import timber.log.Timber
import nl.rijksoverheid.mgo.component.mgo.R as ComponentR
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

@Composable
fun DigidLoginScreen(
  fromOnboarding: Boolean,
  onNavigateBack: (() -> Unit)?,
  onFinishedLogin: () -> Unit,
) {
  val activity = LocalActivity.current as FragmentActivity
  val viewModel: DigidLoginScreenViewModel = hiltViewModel()
  val viewState by viewModel.viewState.collectAsStateWithLifecycle()

  var showLoginFailedDialog by remember { mutableStateOf(false) }
  if (showLoginFailedDialog) {
    MgoAlertDialog(
      heading = stringResource(CopyR.string.login_failed_dialog_heading),
      subHeading = stringResource(CopyR.string.login_failed_dialog_subheading),
      positiveButtonText = stringResource(CopyR.string.common_ok),
      positiveButtonTextColor = MaterialTheme.colorScheme.ActionsGhostText(),
      onClickPositiveButton = {
        showLoginFailedDialog = false
      },
      onDismissRequest = {
        showLoginFailedDialog = false
      },
    )
  }

  DisposableEffect(Unit) {
    val listener =
      Consumer<Intent> { intent ->
        viewModel.handleDeeplink(intent.dataString)
      }
    activity.addOnNewIntentListener(listener)
    onDispose { activity.removeOnNewIntentListener(listener) }
  }

  LaunchedEffect(Unit) {
    viewModel.navigateToUrl.collectLatest { url ->
      activity.launchBrowser(url)
    }
  }

  LaunchedEffect(Unit) {
    viewModel.loginFailed.collectLatest {
      showLoginFailedDialog = true
    }
  }

  LaunchedEffect(Unit) {
    viewModel.loginFinished.collectLatest {
      onFinishedLogin()
    }
  }

  DigidLoginScreenContent(
    viewState = viewState,
    fromOnboarding = fromOnboarding,
    onNavigateBack = onNavigateBack,
    onLoginClicked = {
      viewModel.login()
    },
  )
}

@Composable
private fun DigidLoginScreenContent(
  viewState: DigidLoginScreenViewState,
  fromOnboarding: Boolean,
  onNavigateBack: (() -> Unit)?,
  onLoginClicked: () -> Unit,
) {
  val scrollState = rememberScrollState()
  val scrollBehavior = getMgoAppBarScrollBehaviour(scrollState.canScrollForward, scrollState.canScrollBackward)
  Scaffold(
    modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
    topBar = {
      MgoLargeTopAppBar(
        title = stringResource(CopyR.string.login_heading),
        onNavigateBack = onNavigateBack,
        scrollBehavior = scrollBehavior,
      )
    },
    contentWindowInsets = WindowInsets.statusBars,
    content = { contentPadding ->
      Column(modifier = Modifier.padding(contentPadding)) {
        MgoAutoScrollColumn(
          modifier =
            Modifier
              .weight(1f),
          scrollState = scrollState,
        ) {
          Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = stringResource(id = CopyR.string.login_subheading),
            style = MaterialTheme.typography.bodyMedium,
          )

          Spacer(modifier = Modifier.weight(1f))

          Timber.v("From onboarding: " + fromOnboarding)
          val image = if (fromOnboarding) R.drawable.illustration_passport else R.drawable.illustration_phone
          Image(
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp).then(if (fromOnboarding) Modifier.padding(start = 32.dp) else Modifier),
            contentScale = ContentScale.Crop,
            painter = painterResource(id = image),
            contentDescription = null,
            alignment = Alignment.BottomCenter,
          )
        }

        MgoBottomButtons(
          primaryButton =
            MgoBottomButton(
              text = stringResource(id = CopyR.string.login_digid),
              onClick = onLoginClicked,
              isLoading = viewState.loading,
              icon = ComponentR.drawable.ic_digid,
            ),
          isElevated = scrollState.canScrollForward,
        )
      }
    },
  )
}

@DefaultPreviews
@Composable
internal fun DigidLoginScreenFromOnboardingPreview() {
  MgoTheme {
    DigidLoginScreenContent(
      viewState = DigidLoginScreenViewState(false),
      fromOnboarding = true,
      onLoginClicked = {},
      onNavigateBack = {},
    )
  }
}

@DefaultPreviews
@Composable
internal fun DigidLoginScreenPreview() {
  MgoTheme {
    DigidLoginScreenContent(
      viewState = DigidLoginScreenViewState(false),
      fromOnboarding = false,
      onLoginClicked = {},
      onNavigateBack = {},
    )
  }
}
