package nl.rijksoverheid.mgo.component.theme.composable

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MgoCard(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    // Very weird, but the MaterialTheme.colors.backgroundSecondary set to the surface color is changed in dark mode
    // when elevation is set. The elevation does not affect the color in light mode, or when in both light and dark when settings
    // the color to Color.Red. For now I chose to just disable the elevation in dark mode since this sets the correct colors,
    // and dark mode does not need elevation as per design.
    // TODO Figure out why this is happening?
    val elevation = if (isSystemInDarkTheme()) 0.dp else 1.dp
    Card(modifier = modifier, elevation = elevation, content = content)
}
