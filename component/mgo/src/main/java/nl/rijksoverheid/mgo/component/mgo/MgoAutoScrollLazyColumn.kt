package nl.rijksoverheid.mgo.component.mgo

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.unit.dp

/**
 * A custom LazyColumn composable that exposes whether vertical scrolling is possible
 * via the `canScroll` parameter in the content lambda.
 *
 * This allows the content inside the LazyColumn to adjust its layout or behavior
 * depending on whether scrolling is necessary. Useful for example if you sometimes have
 * just one item, that you want to center (loading state for example).
 *
 * @param modifier modifier the [Modifier] to be applied.
 * @param state The scroll state of the LazyColumn.
 * @param contentPadding Padding to be applied around the LazyColumn's content.
 */
@Composable
fun MgoAutoScrollLazyColumn(
  modifier: Modifier = Modifier,
  state: LazyListState = rememberLazyListState(),
  contentPadding: PaddingValues = PaddingValues(0.dp),
  content: LazyListScope.(canScroll: Boolean) -> Unit,
) {
  // False if we are looking at previews so that they render correctly
  val canScrollDefault = !LocalInspectionMode.current
  var canScroll by remember { mutableStateOf(canScrollDefault) }

  LaunchedEffect(Unit) {
    canScroll = state.canScrollForward
  }

  LazyColumn(
    modifier = modifier,
    state = state,
    contentPadding = contentPadding,
    content = {
      content(canScroll)
    },
  )
}
