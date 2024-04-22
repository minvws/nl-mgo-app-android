package nl.rijksoverheid.mgo.component.theme.composable

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.bodySmall

@Composable
fun MgoButton(
    buttonText: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Button(
        modifier = modifier,
        contentPadding = PaddingValues(vertical = 12.dp, horizontal = 24.dp),
        content = { Text(text = buttonText, style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold) },
        onClick = onClick,
    )
}

@PreviewLightDark
@Composable
internal fun MgoButtonPreview() {
    MgoTheme {
        MgoButton(modifier = Modifier.padding(16.dp), buttonText = "Click me", onClick = { })
    }
}
