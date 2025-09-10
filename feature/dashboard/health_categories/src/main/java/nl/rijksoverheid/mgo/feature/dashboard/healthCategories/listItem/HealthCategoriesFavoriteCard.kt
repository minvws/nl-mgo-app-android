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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import nl.rijksoverheid.mgo.component.healthCareCategory.getIcon
import nl.rijksoverheid.mgo.component.healthCareCategory.getIconColor
import nl.rijksoverheid.mgo.component.healthCareCategory.getTitle
import nl.rijksoverheid.mgo.component.mgo.MgoCard
import nl.rijksoverheid.mgo.component.theme.DefaultPreviews
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.backgroundTertiary
import nl.rijksoverheid.mgo.component.theme.symbolsSecondary
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.category.HealthCareCategoryId
import java.time.format.TextStyle

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
  MgoCard(modifier = modifier.width(182.dp), onClick = onClick) {
    Column(modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp)) {
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

      // We want this Text composable to always reserve space for two lines.
      // Setting minLines = 2 achieves that easily.
      // However, Jetpack Compose does not provide a way to vertically align the text
      // within that space when the content is shorter than two lines.
      // To work around this, we use a layout modifier to check if the text occupies only one line,
      // and if so, we adjust its position so it is vertically centered.
      val textMeasurer = rememberTextMeasurer()
      val text = stringResource(category.getTitle())
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
      category = HealthCareCategoryId.MEDICATIONS,
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
