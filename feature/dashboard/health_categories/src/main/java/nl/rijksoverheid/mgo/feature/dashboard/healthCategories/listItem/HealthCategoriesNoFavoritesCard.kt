package nl.rijksoverheid.mgo.feature.dashboard.healthCategories.listItem

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.borderPrimary
import nl.rijksoverheid.mgo.component.theme.supportRijkslint
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

@Composable
fun HealthCategoriesNoFavoriteCard(
  onClickAddFavorite: () -> Unit,
  modifier: Modifier = Modifier,
) {
  val borderColor = MaterialTheme.colorScheme.borderPrimary()
  Column(
    modifier =
      modifier
        .drawBehind {
          val pathEffect = PathEffect.dashPathEffect(floatArrayOf(5.dp.toPx(), 5.dp.toPx()), 0f)
          drawRoundRect(
            color = borderColor,
            style = Stroke(width = 1.dp.toPx(), pathEffect = pathEffect),
            cornerRadius = CornerRadius(12.dp.toPx()),
          )
        }.padding(start = 32.dp, end = 32.dp, top = 32.dp, bottom = 20.dp),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally,
  ) {
    Text(text = stringResource(CopyR.string.overview_favorites_empty_heading), style = MaterialTheme.typography.bodyMedium.copy(lineHeight = 20.sp))
    TextButton(onClickAddFavorite) {
      Text(
        text = stringResource(CopyR.string.overview_favorites_empty_action),
        style = MaterialTheme.typography.bodyMedium,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.supportRijkslint(),
      )
    }
  }
}

@PreviewLightDark
@Composable
internal fun HealthCategoriesNoFavoriteCardPreview() {
  MgoTheme {
    HealthCategoriesNoFavoriteCard(onClickAddFavorite = {})
  }
}
