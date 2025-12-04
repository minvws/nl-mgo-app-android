package nl.rijksoverheid.mgo.feature.dashboard.healthCategories.banner

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import nl.rijksoverheid.mgo.component.mgo.MgoCard
import nl.rijksoverheid.mgo.component.theme.CategoriesRijkslint
import nl.rijksoverheid.mgo.component.theme.DefaultPreviews
import nl.rijksoverheid.mgo.component.theme.LabelsSecondary
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

@Composable
fun HealthCategoriesBannerLoading(modifier: Modifier = Modifier) {
  MgoCard(modifier = modifier) {
    Column(
      modifier = Modifier.fillMaxWidth().defaultMinSize(minHeight = 156.dp).padding(16.dp),
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
@DefaultPreviews
internal fun HealthCategoriesBannerLoadingPreview() {
  MgoTheme {
    HealthCategoriesBannerLoading()
  }
}
