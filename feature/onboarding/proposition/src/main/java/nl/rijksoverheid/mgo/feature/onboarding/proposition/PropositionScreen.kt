package nl.rijksoverheid.mgo.feature.onboarding.proposition

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import nl.rijksoverheid.mgo.component.mgo.MgoBottomButton
import nl.rijksoverheid.mgo.component.mgo.MgoBottomButtons
import nl.rijksoverheid.mgo.component.mgo.MgoLargeTopAppBar
import nl.rijksoverheid.mgo.component.mgo.getMgoAppBarScrollBehaviour
import nl.rijksoverheid.mgo.component.theme.CategoriesRijkslint
import nl.rijksoverheid.mgo.component.theme.DefaultPreviews
import nl.rijksoverheid.mgo.component.theme.LabelsPrimary
import nl.rijksoverheid.mgo.component.theme.LabelsSecondary
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.framework.util.launchBrowser
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

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
  val context = LocalContext.current
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
          Text(
            text = stringResource(CopyR.string.proposition_subheading),
            style = MaterialTheme.typography.bodyMedium,
          )
          ListItem(
            modifier = Modifier.padding(top = 16.dp),
            icon = R.drawable.ic_proposition_statement_1,
            heading = CopyR.string.proposition_statement_heading_1,
            subHeading = CopyR.string.proposition_statement_subheading_1,
          )
          ListItem(
            modifier = Modifier.padding(top = 16.dp),
            icon = R.drawable.ic_proposition_statement_2,
            heading = CopyR.string.proposition_statement_heading_2,
            subHeading = CopyR.string.proposition_statement_subheading_2,
          )
          ListItem(
            modifier = Modifier.padding(top = 16.dp),
            icon = R.drawable.ic_proposition_statement_3,
            heading = CopyR.string.proposition_statement_heading_3,
            subHeading = CopyR.string.proposition_statement_subheading_3,
          )
          ListItem(
            modifier = Modifier.padding(top = 16.dp),
            icon = R.drawable.ic_proposition_statement_4,
            heading = CopyR.string.proposition_statement_heading_4,
            subHeading = CopyR.string.proposition_statement_subheading_4,
          )
        }

        MgoBottomButtons(
          primaryButton =
            MgoBottomButton(
              text = stringResource(id = CopyR.string.common_next),
              onClick = onClickNext,
            ),
          secondaryButton =
            MgoBottomButton(
              text = stringResource(id = CopyR.string.proposition_open_privacy_button),
              onClick = { context.launchBrowser(url) },
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
  @StringRes heading: Int,
  @StringRes subHeading: Int,
  modifier: Modifier = Modifier,
) {
  Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
    Icon(painter = painterResource(id = icon), contentDescription = null, tint = MaterialTheme.colorScheme.CategoriesRijkslint())
    Column(modifier = modifier.padding(horizontal = 16.dp)) {
      Text(
        text = stringResource(heading),
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.LabelsPrimary(),
        fontWeight = FontWeight.Bold,
      )
      Text(
        text = stringResource(subHeading),
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.LabelsSecondary(),
      )
    }
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
