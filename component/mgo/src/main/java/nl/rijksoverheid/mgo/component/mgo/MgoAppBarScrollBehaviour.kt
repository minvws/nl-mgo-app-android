package nl.rijksoverheid.mgo.component.mgo

import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable

/**
 * This method returns a top app bar scroll behaviour that only scrolls if the content scrollable.
 * The [canScrollForward] and [canScrollBackward] are usually provided by the scroll state of a Column or LazyColumn.
 *
 * @param canScrollForward If the content can be scrolled forwards.
 * @param canScrollBackward If the content can be scrolled backwards.
 * @return The scroll behaviour to be applied to the TopAppBar.
 */
@Composable
fun getMgoAppBarScrollBehaviour(
  canScrollForward: Boolean,
  canScrollBackward: Boolean,
): TopAppBarScrollBehavior =
  TopAppBarDefaults.enterAlwaysScrollBehavior(
    canScroll = { canScrollForward || canScrollBackward },
    state = rememberTopAppBarState(),
  )
