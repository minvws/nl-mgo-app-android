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
import androidx.compose.ui.unit.dp

@Composable
fun MgoAutoScrollLazyColumn(
  modifier: Modifier = Modifier,
  state: LazyListState = rememberLazyListState(),
  contentPadding: PaddingValues = PaddingValues(0.dp),
  content: LazyListScope.(canScroll: Boolean) -> Unit,
) {
  var canScroll by remember { mutableStateOf(true) }

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
