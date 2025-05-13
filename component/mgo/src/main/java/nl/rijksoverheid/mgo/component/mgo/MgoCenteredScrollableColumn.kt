package nl.rijksoverheid.mgo.component.mgo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity

@Composable
fun MgoCenteredScrollableColumn(
  modifier: Modifier = Modifier,
  verticalArrangement: Arrangement.Vertical = Arrangement.Top,
  horizontalAlignment: Alignment.Horizontal = Alignment.Start,
  content: @Composable ColumnScope.() -> Unit,
) {
  BoxWithConstraints(modifier = modifier) {
    val maxHeightDp = maxHeight
    val density = LocalDensity.current
    var contentHeightPx by remember { mutableIntStateOf(Int.MAX_VALUE) }
    val canScroll = with(density) { contentHeightPx.toDp() > maxHeightDp }

    Column(
      modifier =
        Modifier
          .then(if (canScroll) Modifier.verticalScroll(rememberScrollState()) else Modifier)
          .onGloballyPositioned {
            contentHeightPx = it.size.height
          },
      verticalArrangement = verticalArrangement,
      horizontalAlignment = horizontalAlignment,
      content = content,
    )
  }
}
