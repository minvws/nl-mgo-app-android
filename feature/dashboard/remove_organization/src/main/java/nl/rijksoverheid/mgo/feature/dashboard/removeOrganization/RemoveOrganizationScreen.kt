package nl.rijksoverheid.mgo.feature.dashboard.removeOrganization

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.flow.collectLatest
import nl.rijksoverheid.mgo.component.mgo.MgoBottomButton
import nl.rijksoverheid.mgo.component.mgo.MgoBottomButtons
import nl.rijksoverheid.mgo.component.mgo.snackbar.LocalDashboardSnackbarPresenter
import nl.rijksoverheid.mgo.component.theme.BackgroundsSecondary
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.StatesCritical
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

@Composable
fun RemoveOrganizationScreen(
  providerId: String,
  providerName: String,
  onNavigateBack: () -> Unit,
  onNavigateToDashboard: () -> Unit,
) {
  val snackbarPresenter = LocalDashboardSnackbarPresenter.current
  val viewModel: RemoveOrganizationScreenViewModel = hiltViewModel()
  LaunchedEffect(Unit) {
    viewModel.providerDeleted.collectLatest {
      onNavigateToDashboard()
    }
  }
  RemoveOrganizationScreenContent(
    providerName = providerName,
    onNavigateBack = onNavigateBack,
    onDeleteProvider = {
      viewModel.delete(snackbarPresenter, providerId)
    },
  )
}

@Composable
private fun RemoveOrganizationScreenContent(
  providerName: String,
  onNavigateBack: () -> Unit,
  onDeleteProvider: () -> Unit,
) {
  val scrollState = rememberScrollState()
  Scaffold(
    content = { contentPadding ->
      Column(modifier = Modifier.padding(contentPadding)) {
        Column(
          modifier =
            Modifier
              .weight(1f)
              .verticalScroll(scrollState)
              .padding(16.dp),
        ) {
          Box(
            modifier =
              Modifier
                .padding(top = TopAppBarDefaults.LargeAppBarCollapsedHeight)
                .size(102.dp)
                .background(MaterialTheme.colorScheme.StatesCritical(), CircleShape)
                .align(Alignment.CenterHorizontally),
            contentAlignment = Alignment.Center,
          ) {
            Icon(
              modifier =
                Modifier
                  .size(61.dp),
              painter = painterResource(id = R.drawable.ic_delete),
              tint = MaterialTheme.colorScheme.BackgroundsSecondary(),
              contentDescription = null,
            )
          }

          Text(
            modifier = Modifier.padding(top = 32.dp),
            text = stringResource(id = CopyR.string.remove_organization_heading, providerName),
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
          )

          Text(
            modifier = Modifier.padding(top = 16.dp),
            text =
              stringResource(
                id = CopyR.string.remove_organization_subheading,
                providerName,
              ),
            style = MaterialTheme.typography.bodyMedium,
          )
        }

        MgoBottomButtons(
          primaryButton =
            MgoBottomButton(
              text = stringResource(id = CopyR.string.remove_organization_no_cancel),
              onClick = onNavigateBack,
            ),
          secondaryButton =
            MgoBottomButton(
              text = stringResource(id = CopyR.string.remove_organization_yes_delete),
              onClick = onDeleteProvider,
            ),
          hasNavigationBarsPadding = false,
          isElevated = scrollState.canScrollForward,
        )
      }
    },
  )
}

@PreviewLightDark
@Composable
fun RemoveOrganizationScreenPreview() {
  MgoTheme {
    RemoveOrganizationScreenContent(
      providerName = "UMC Groningen",
      onNavigateBack = {},
      onDeleteProvider = {},
    )
  }
}
