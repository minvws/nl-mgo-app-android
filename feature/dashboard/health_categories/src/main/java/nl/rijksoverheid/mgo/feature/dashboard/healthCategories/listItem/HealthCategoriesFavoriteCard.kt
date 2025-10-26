package nl.rijksoverheid.mgo.feature.dashboard.healthCategories.listItem

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import nl.rijksoverheid.mgo.component.healthCategories.getColor
import nl.rijksoverheid.mgo.component.healthCategories.getDrawable
import nl.rijksoverheid.mgo.component.healthCategories.getString
import nl.rijksoverheid.mgo.component.mgo.MgoCard
import nl.rijksoverheid.mgo.component.theme.BackgroundsTertiary
import nl.rijksoverheid.mgo.component.theme.DefaultPreviews
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.SymbolsSecondary
import nl.rijksoverheid.mgo.data.healthCategories.models.HealthCategoryGroup
import nl.rijksoverheid.mgo.data.healthCategories.models.TEST_HEALTH_CATEGORY_PROBLEMS

@Composable
fun HealthCategoriesFavoriteCard(
  category: HealthCategoryGroup.HealthCategory,
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
  category: HealthCategoryGroup.HealthCategory,
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
) {
  MgoCard(modifier = modifier.width(182.dp), onClick = onClick) {
    Column(modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp)) {
      Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Box(
          modifier = Modifier.size(32.dp).background(color = category.icon.getColor().copy(alpha = 0.15f), shape = RoundedCornerShape(8.dp)),
          contentAlignment = Alignment.Center,
        ) {
          Icon(painterResource(category.icon.getDrawable()), tint = category.icon.getColor(), contentDescription = null)
        }
        if (loading) {
          CircularProgressIndicator(
            modifier = Modifier.size(24.dp),
            strokeWidth = 2.dp,
            trackColor = MaterialTheme.colorScheme.BackgroundsTertiary().copy(alpha = 0.5f),
            color = MaterialTheme.colorScheme.SymbolsSecondary(),
          )
        }
      }

      // We want this Text composable to always reserve space for two lines.
      // Setting minLines = 2 achieves that easily.
      // However, Jetpack Compose does not provide a way to vertically align the text
      // within that space when the content is shorter than two lines.
      // To work around this, we use a layout modifier to check if the text occupies only one line,
      // and if so, we adjust its position so it is vertically centered.
      val textMeasurer = rememberTextMeasurer()
      val text = LocalContext.current.getString(category.heading)
      val textStyle = MaterialTheme.typography.bodyMedium
      Text(
        modifier =
          Modifier.fillMaxWidth().padding(top = 16.dp, bottom = 16.dp).layout { measurable, constraints ->
            val textLayoutResult: TextLayoutResult =
              textMeasurer.measure(
                text = text,
                style = textStyle,
                constraints = constraints,
              )
            val placeable = measurable.measure(constraints)
            if (textLayoutResult.lineCount == 1) {
              layout(placeable.width, placeable.height) {
                placeable.placeRelative(0, placeable.height / 2)
              }
            } else {
              layout(placeable.width, placeable.height) {
                placeable.placeRelative(0, 0)
              }
            }
          },
        text = text,
        minLines = 2,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis,
        style = textStyle,
      )
    }
  }
}

@PreviewLightDark
@Composable
internal fun HealthCategoriesFavoriteCardPreview() {
  MgoTheme {
    HealthCategoriesFavoriteCardContent(
      category = TEST_HEALTH_CATEGORY_PROBLEMS,
      onClick = {},
      loading = false,
    )
  }
}

@DefaultPreviews
@Composable
internal fun HealthCategoriesFavoriteMultilineCardPreview() {
  MgoTheme {
    HealthCategoriesFavoriteCardContent(
      category = TEST_HEALTH_CATEGORY_PROBLEMS,
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
      category = TEST_HEALTH_CATEGORY_PROBLEMS,
      onClick = {},
      loading = true,
    )
  }
}
