package nl.rijksoverheid.mgo.component.theme.composable

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
    buttonTheme: MgoButtonTheme = MgoButtonTheme.Primary,
) {
    val buttonColors = ButtonDefaults.buttonColors(backgroundColor = buttonTheme.getColor())
    Button(
        modifier = modifier,
        contentPadding = PaddingValues(vertical = 12.dp, horizontal = 24.dp),
        content = { Text(text = buttonText, style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold) },
        onClick = onClick,
        colors = buttonColors,
    )
}

sealed class MgoButtonTheme {
    @Composable
    abstract fun getColor(): Color

    data object Primary : MgoButtonTheme() {
        @Composable
        override fun getColor(): Color {
            return MaterialTheme.colors.primary
        }
    }

    data object Secondary : MgoButtonTheme() {
        @Composable
        override fun getColor(): Color {
            return MaterialTheme.colors.secondary
        }
    }
}

@PreviewLightDark
@Composable
internal fun MgoButtonPrimaryPreview() {
    MgoTheme {
        MgoButton(modifier = Modifier.padding(16.dp), buttonText = "Click me", onClick = { }, buttonTheme = MgoButtonTheme.Primary)
    }
}

@PreviewLightDark
@Composable
internal fun MgoButtonSecondaryPreview() {
    MgoTheme {
        MgoButton(modifier = Modifier.padding(16.dp), buttonText = "Click me", onClick = { }, buttonTheme = MgoButtonTheme.Secondary)
    }
}
