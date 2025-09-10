package nl.rijksoverheid.mgo.feature.dashboard.healthCategories.listItem

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import nl.rijksoverheid.mgo.component.healthCareCategory.getIcon
import nl.rijksoverheid.mgo.component.healthCareCategory.getIconColor
import nl.rijksoverheid.mgo.component.healthCareCategory.getTitle
import nl.rijksoverheid.mgo.component.mgo.MgoCard
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.backgroundTertiary
import nl.rijksoverheid.mgo.component.theme.symbolsSecondary
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.category.HealthCareCategoryId

@Composable
fun HealthCategoriesFavoriteCard(
  category: HealthCareCategoryId,
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
) {
  val loading =
    if (LocalInspectionMode.current) {
      false
    } else {
      val viewModel =
        hiltViewModel<HealthCategoriesFavoriteCardViewModel, HealthCategoriesFavoriteCardViewModel.Factory>(
          creationCallback = { factory -> factory.create(category = category) },
          key = "favorite_card_$category",
        )
      val loading by viewModel.isLoading.collectAsStateWithLifecycle()
      loading
    }
  HealthCategoriesFavoriteCardContent(
    loading = loading,
    category = category,
    onClick = onClick,
    modifier = modifier,
  )
}

@Composable
private fun HealthCategoriesFavoriteCardContent(
  loading: Boolean,
  category: HealthCareCategoryId,
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
) {
  MgoCard(modifier = modifier.width(182.dp).height(116.dp), onClick = onClick) {
    Column(modifier = Modifier.padding(16.dp)) {
      Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Icon(painterResource(category.getIcon()), tint = category.getIconColor(), contentDescription = null)
        if (loading) {
          CircularProgressIndicator(
            modifier = Modifier.size(24.dp),
            strokeWidth = 2.dp,
            trackColor = MaterialTheme.colorScheme.backgroundTertiary().copy(alpha = 0.5f),
            color = MaterialTheme.colorScheme.symbolsSecondary(),
          )
        }
      }
      Spacer(modifier = Modifier.weight(1f))
      Text(
        modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
        text = stringResource(category.getTitle()),
        maxLines = 2,
        overflow = TextOverflow.Ellipsis,
        style = MaterialTheme.typography.bodyMedium,
      )
    }
  }
}

@PreviewLightDark
@Composable
internal fun HealthCategoriesFavoriteCardPreview() {
  MgoTheme {
    HealthCategoriesFavoriteCardContent(
      category = HealthCareCategoryId.MEDICATIONS,
      onClick = {},
      loading = false,
    )
  }
}

@PreviewLightDark
@Composable
internal fun HealthCategoriesFavoriteMultilineCardPreview() {
  MgoTheme {
    HealthCategoriesFavoriteCardContent(
      category = HealthCareCategoryId.PATIENT,
      onClick = {},
      loading = false,
    )
  }
}

@PreviewLightDark
@Composable
internal fun HealthCategoriesFavoriteCardLoadingPreview() {
  MgoTheme {
    HealthCategoriesFavoriteCardContent(
      category = HealthCareCategoryId.MEDICATIONS,
      onClick = {},
      loading = true,
    )
  }
}
