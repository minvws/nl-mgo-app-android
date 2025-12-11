package nl.rijksoverheid.mgo.feature.dashboard.healthCategories.banner

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import nl.rijksoverheid.mgo.component.mgo.MgoButton
import nl.rijksoverheid.mgo.component.mgo.MgoCard
import nl.rijksoverheid.mgo.component.theme.CategoriesRijkslint
import nl.rijksoverheid.mgo.component.theme.DefaultPreviews
import nl.rijksoverheid.mgo.component.theme.LabelsPrimary
import nl.rijksoverheid.mgo.component.theme.LabelsSecondary
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.SymbolsPrimary
import nl.rijksoverheid.mgo.feature.dashboard.healthCategories.R
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

@Composable
internal fun HealthCategoriesBannerLoading(modifier: Modifier = Modifier) {
  MgoCard(modifier = modifier) {
    Column(
      modifier =
        Modifier
          .fillMaxWidth()
          .defaultMinSize(minHeight = 156.dp)
          .padding(16.dp),
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally,
    ) {
      CircularProgressIndicator(
        modifier = Modifier.size(40.dp),
        strokeWidth = 4.dp,
        trackColor = MaterialTheme.colorScheme.CategoriesRijkslint().copy(alpha = 0.15f),
        color = MaterialTheme.colorScheme.CategoriesRijkslint(),
      )

      Text(
        modifier = Modifier.padding(top = 16.dp),
        text = stringResource(CopyR.string.errorstate_loading),
        color = MaterialTheme.colorScheme.LabelsSecondary(),
        style = MaterialTheme.typography.bodyMedium,
      )
    }
  }
}

@Composable
internal fun HealthCategoriesBannerError(
  state: HealthCategoriesBannerState.Error,
  onClickRetry: () -> Unit,
  modifier: Modifier = Modifier,
) {
  MgoCard(modifier = modifier) {
    Column(modifier = Modifier.padding(16.dp)) {
      Row {
        Icon(painter = painterResource(R.drawable.ic_sync_problem), contentDescription = null, tint = MaterialTheme.colorScheme.SymbolsPrimary())

        Column(modifier = Modifier.padding(start = 8.dp)) {
          val headingText =
            if (state.partial) {
              CopyR.string.errorstate_partial_error
            } else {
              CopyR.string.errorstate_error
            }

          Text(
            text = stringResource(headingText),
            color = MaterialTheme.colorScheme.LabelsPrimary(),
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
          )

          val subheadingText =
            when (state) {
              is HealthCategoriesBannerState.Error.ServerError -> CopyR.string.errorstate_serverside_heading
              is HealthCategoriesBannerState.Error.UserError -> CopyR.string.errorstate_clientside_heading
            }

          Text(
            modifier = Modifier.padding(top = 4.dp),
            text = stringResource(subheadingText),
            color = MaterialTheme.colorScheme.LabelsSecondary(),
            style = MaterialTheme.typography.bodyMedium,
          )
        }
      }
      MgoButton(
        modifier =
          Modifier
            .fillMaxWidth()
            .heightIn(min = 38.dp)
            .padding(top = 16.dp),
        buttonHeight = 36.dp,
        buttonText = stringResource(CopyR.string.common_try_again),
        onClick = onClickRetry,
      )
    }
  }
}

@Composable
@DefaultPreviews
internal fun HealthCategoriesBannerLoadingPreview() {
  MgoTheme {
    HealthCategoriesBannerLoading()
  }
}

@Composable
@DefaultPreviews
internal fun HealthCategoriesBannerUserErrorPreview() {
  MgoTheme {
    HealthCategoriesBannerError(HealthCategoriesBannerState.Error.UserError(false), onClickRetry = { })
  }
}

@Composable
@DefaultPreviews
internal fun HealthCategoriesBannerUserErrorPartialPreview() {
  MgoTheme {
    HealthCategoriesBannerError(HealthCategoriesBannerState.Error.UserError(true), onClickRetry = {})
  }
}

@Composable
@DefaultPreviews
internal fun HealthCategoriesBannerServerErrorPreview() {
  MgoTheme {
    HealthCategoriesBannerError(HealthCategoriesBannerState.Error.ServerError(false), onClickRetry = {})
  }
}

@Composable
@DefaultPreviews
internal fun HealthCategoriesBannerServerErrorPartialPreview() {
  MgoTheme {
    HealthCategoriesBannerError(HealthCategoriesBannerState.Error.ServerError(true), onClickRetry = {})
  }
}
