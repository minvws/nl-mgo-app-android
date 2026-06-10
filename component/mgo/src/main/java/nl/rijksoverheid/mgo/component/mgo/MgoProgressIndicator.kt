package nl.rijksoverheid.mgo.component.mgo

import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import nl.rijksoverheid.mgo.component.theme.CategoriesRijkslint
import nl.rijksoverheid.mgo.component.theme.MgoTheme

@Composable
fun MgoProgressIndicator(
  type: MgoProgressIndicatorType,
  modifier: Modifier = Modifier,
) {
  val size =
    when (type) {
      MgoProgressIndicatorType.SMALL -> 24.dp
      MgoProgressIndicatorType.LARGE -> 48.dp
    }
  val strokeWidth =
    when (type) {
      MgoProgressIndicatorType.SMALL -> 2.dp
      MgoProgressIndicatorType.LARGE -> 4.dp
    }

  CircularProgressIndicator(
    modifier =
      modifier
        .size(size),
    strokeWidth = strokeWidth,
    trackColor = MaterialTheme.colorScheme.CategoriesRijkslint().copy(alpha = 0.15f),
    color = MaterialTheme.colorScheme.CategoriesRijkslint(),
  )
}

@Composable
@PreviewLightDark
internal fun MgoProgressIndicatorSmallPreview() {
  MgoTheme {
    MgoProgressIndicator(MgoProgressIndicatorType.SMALL)
  }
}

@Composable
@PreviewLightDark
internal fun MgoProgressIndicatorLargePreview() {
  MgoTheme {
    MgoProgressIndicator(MgoProgressIndicatorType.LARGE)
  }
}

enum class MgoProgressIndicatorType {
  SMALL,
  LARGE,
}
