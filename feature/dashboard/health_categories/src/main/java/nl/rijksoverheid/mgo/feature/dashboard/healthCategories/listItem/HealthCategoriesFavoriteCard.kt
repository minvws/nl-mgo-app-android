package nl.rijksoverheid.mgo.feature.dashboard.healthCategories.listItem

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import nl.rijksoverheid.mgo.component.healthCareCategory.getIcon
import nl.rijksoverheid.mgo.component.healthCareCategory.getIconColor
import nl.rijksoverheid.mgo.component.healthCareCategory.getTitle
import nl.rijksoverheid.mgo.component.mgo.MgoCard
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.category.HealthCareCategoryId

@Composable
fun HealthCategoriesFavoriteCard(
  category: HealthCareCategoryId,
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
) {
  MgoCard(modifier = modifier.width(182.dp).height(102.dp).clickable { onClick() }) {
    Column(modifier = Modifier.padding(16.dp)) {
      Icon(painterResource(category.getIcon()), tint = category.getIconColor(), contentDescription = null)
      Text(modifier = Modifier.fillMaxWidth().padding(top = 16.dp), text = stringResource(category.getTitle()), maxLines = 1, overflow = TextOverflow.Ellipsis)
    }
  }
}

@PreviewLightDark
@Composable
internal fun HealthCategoriesFavoriteCardPreview() {
  MgoTheme {
    HealthCategoriesFavoriteCard(
      category = HealthCareCategoryId.MEDICATIONS,
        onClick = {}
    )
  }
}
