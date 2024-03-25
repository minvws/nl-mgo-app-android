package nl.rijksoverheid.mgo.component.theme

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * A column with a button fixed to the bottom. This button will automatically add elevation when the column is scrollable (as per design).
 */
@Composable
fun ColumnWithButton(
    buttonText: String,
    onButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
    columnContent: @Composable ColumnScope.() -> Unit,
) {
    val scrollState = rememberScrollState()
    Column(modifier = modifier.fillMaxHeight()) {
        Column(modifier = Modifier.weight(1f).verticalScroll(scrollState), content = columnContent)
        val elevation = if (scrollState.canScrollForward) 10.dp else 0.dp
        Surface(color = MaterialTheme.colors.background, elevation = elevation) {
            Button(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(all = 16.dp),
                contentPadding = PaddingValues(vertical = 12.dp, horizontal = 24.dp),
                content = { Text(text = buttonText) },
                onClick = onButtonClick,
            )
        }
    }
}
