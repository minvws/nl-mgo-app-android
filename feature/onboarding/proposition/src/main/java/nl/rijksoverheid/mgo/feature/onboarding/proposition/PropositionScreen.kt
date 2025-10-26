package nl.rijksoverheid.mgo.feature.onboarding.proposition

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import nl.rijksoverheid.mgo.component.mgo.MgoBottomButton
import nl.rijksoverheid.mgo.component.mgo.MgoBottomButtons
import nl.rijksoverheid.mgo.component.mgo.MgoHtmlText
import nl.rijksoverheid.mgo.component.mgo.MgoLargeTopAppBar
import nl.rijksoverheid.mgo.component.mgo.getMgoAppBarScrollBehaviour
import nl.rijksoverheid.mgo.component.theme.CategoriesRijkslint
import nl.rijksoverheid.mgo.component.theme.DefaultPreviews
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

/**
 * Composable that shows a screen displaying the privacy policy.
 *
 * @param onNavigateBack Called when requested to navigate back.
 * @param onOnboardingFinished Called when the onboarding is considered done.
 */
@Composable
fun PropositionOverviewScreen(
  onNavigateBack: () -> Unit,
  onOnboardingFinished: () -> Unit,
) {
  val viewModel: PropositionScreenViewModel = hiltViewModel()
  PropositionOverviewScreenContent(
    url = viewModel.getPrivacyUrl(),
    onNavigateBack = onNavigateBack,
    onClickNext = {
      viewModel.setHasSeenOnboarding()
      onOnboardingFinished()
    },
  )
}

@Composable
internal fun PropositionOverviewScreenContent(
  url: String,
  onNavigateBack: () -> Unit,
  onClickNext: () -> Unit,
) {
  val scrollState = rememberScrollState()
  val scrollBehavior = getMgoAppBarScrollBehaviour(scrollState.canScrollForward, scrollState.canScrollBackward)

  Scaffold(
    modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
    contentWindowInsets = WindowInsets.statusBars,
    topBar = {
      MgoLargeTopAppBar(
        title = stringResource(id = CopyR.string.proposition_heading),
        onNavigateBack = onNavigateBack,
        scrollBehavior = scrollBehavior,
      )
    },
    content = { contentPadding ->
      Column(modifier = Modifier.padding(contentPadding)) {
        Column(
          modifier =
            Modifier
              .weight(1f)
              .verticalScroll(scrollState)
              .padding(16.dp),
        ) {
          MgoHtmlText(
            html = stringResource(id = CopyR.string.proposition_subheading, url),
            style = MaterialTheme.typography.bodyMedium,
          )
          ListItem(
            modifier = Modifier.padding(top = 16.dp),
            icon = R.drawable.ic_privacy_overview_encrypted,
            text = stringResource(id = CopyR.string.proposition_statement_1),
          )
          ListItem(
            modifier = Modifier.padding(top = 24.dp),
            icon = R.drawable.ic_privacy_overview_health_and_safety,
            text = stringResource(id = CopyR.string.proposition_statement_2),
          )
          ListItem(
            modifier = Modifier.padding(top = 24.dp),
            icon = R.drawable.ic_privacy_overview_verified_user,
            text = stringResource(id = CopyR.string.proposition_statement_3),
          )
          ListItem(
            modifier = Modifier.padding(top = 24.dp),
            icon = R.drawable.ic_privacy_overview_gpp_bad,
            text = stringResource(id = CopyR.string.proposition_statement_4),
          )
        }

        MgoBottomButtons(
          primaryButton =
            MgoBottomButton(
              text = stringResource(id = CopyR.string.common_next),
              onClick = onClickNext,
            ),
          isElevated = scrollState.canScrollForward,
        )
      }
    },
  )
}

@Composable
private fun ListItem(
  @DrawableRes icon: Int,
  text: String,
  modifier: Modifier = Modifier,
) {
  Row(modifier = modifier) {
    Icon(painter = painterResource(id = icon), contentDescription = null, tint = MaterialTheme.colorScheme.CategoriesRijkslint())
    MgoHtmlText(
      modifier = Modifier.padding(horizontal = 16.dp),
      html = text,
      style = MaterialTheme.typography.bodyMedium,
    )
  }
}

@DefaultPreviews
@Composable
internal fun PropositionScreenPreview() {
  MgoTheme {
    PropositionOverviewScreenContent(
      url = "https://www.google.nl",
      onNavigateBack = {},
      onClickNext = {},
    )
  }
}
