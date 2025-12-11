package nl.rijksoverheid.mgo.component.mgo

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import nl.rijksoverheid.mgo.component.theme.MgoTheme

@Composable
fun MgoCard(
  modifier: Modifier = Modifier,
  shape: Shape = CardDefaults.shape,
  onClick: (() -> Unit)? = null,
  content: @Composable ColumnScope.() -> Unit,
) {
  if (onClick == null) {
    Card(
      modifier = modifier,
      shape = shape,
      colors = CardDefaults.outlinedCardColors(),
      elevation = CardDefaults.elevatedCardElevation(),
      content = content,
    )
  } else {
    Card(
      modifier = modifier,
      shape = shape,
      colors = CardDefaults.outlinedCardColors(),
      elevation = CardDefaults.elevatedCardElevation(),
      onClick = onClick,
      content = content,
    )
  }
}

@PreviewLightDark
@Composable
internal fun MgoCardPreview() {
  MgoTheme {
    MgoCard {
      Box(modifier = Modifier.padding(16.dp)) {
        Text(text = "Hello World")
      }
    }
  }
}
