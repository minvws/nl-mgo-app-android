package nl.rijksoverheid.mgo.feature.dashboard.healthCategories.listItem

import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.category.HealthCareCategoryId

@Composable
fun HealthCategoriesFavoriteCard(
  category: HealthCareCategoryId,
  modifier: Modifier = Modifier,
) {
  Card(modifier = modifier) {
    Text(category.id)
  }
}
