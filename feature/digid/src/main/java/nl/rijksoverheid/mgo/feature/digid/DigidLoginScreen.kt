package nl.rijksoverheid.mgo.feature.digid

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.util.Consumer
import androidx.fragment.app.FragmentActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.collectLatest
import nl.rijksoverheid.mgo.component.mgo.MgoBottomButton
import nl.rijksoverheid.mgo.component.mgo.MgoBottomButtons
import nl.rijksoverheid.mgo.component.mgo.MgoButtonTheme
import nl.rijksoverheid.mgo.component.mgo.MgoHtmlText
import nl.rijksoverheid.mgo.component.theme.DefaultPreviews
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.framework.util.launchBrowser
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

/**
 * Composable that shows a screen where you can start the authentication process with DigiD.
 *
 * @param onNavigateToDigidMock Called when requested to navigate to a DigiD mock screen (temporary).
 */
@Composable
fun DigidLoginScreen(onNavigateToDigidMock: () -> Unit) {
  val activity = LocalContext.current as FragmentActivity
  val viewModel: DigidLoginScreenViewModel = hiltViewModel()
  val viewState by viewModel.viewState.collectAsStateWithLifecycle()

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
    viewModel.loginFinished.collectLatest {
      onNavigateToDigidMock()
    }
  }

  DigidLoginScreenContent(
    viewState = viewState,
    onLoginClicked = {
      viewModel.login()
    },
  )
}

@Composable
private fun DigidLoginScreenContent(
  viewState: DigidLoginScreenViewState,
  onLoginClicked: () -> Unit,
) {
  val scrollState = rememberScrollState()
  Scaffold(
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
          Image(
            modifier =
              Modifier
                .fillMaxWidth()
                .padding(top = TopAppBarDefaults.LargeAppBarCollapsedHeight)
                .align(Alignment.CenterHorizontally),
            painter = painterResource(id = R.drawable.illustration_login),
            contentDescription = null,
          )

          Text(
            modifier = Modifier.padding(top = 32.dp),
            text = stringResource(id = CopyR.string.login_heading),
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
          )

          MgoHtmlText(
            modifier = Modifier.padding(top = 16.dp),
            html = stringResource(id = CopyR.string.login_subheading),
            style = MaterialTheme.typography.bodyMedium,
          )
        }

        MgoBottomButtons(
          primaryButton =
            MgoBottomButton(
              text = stringResource(id = CopyR.string.login_digid),
              onClick = onLoginClicked,
              isLoading = viewState.loading,
              overrideTheme = MgoButtonTheme.DIGID,
            ),
          isElevated = scrollState.canScrollForward,
        )
      }
    },
  )
}

@DefaultPreviews
@Composable
internal fun DigidLoginScreenIdlePreview() {
  MgoTheme {
    DigidLoginScreenContent(
      viewState = DigidLoginScreenViewState(false),
      onLoginClicked = {},
    )
  }
}

@DefaultPreviews
@Composable
internal fun DigidLoginScreenLoadingPreview() {
  MgoTheme {
    DigidLoginScreenContent(
      viewState = DigidLoginScreenViewState(true),
      onLoginClicked = {},
    )
  }
}
