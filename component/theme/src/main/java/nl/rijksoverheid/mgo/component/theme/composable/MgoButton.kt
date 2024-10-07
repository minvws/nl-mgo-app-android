package nl.rijksoverheid.mgo.component.theme.composable

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.heightIn
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
import nl.rijksoverheid.mgo.component.theme.actionPrimaryDefaultBackground
import nl.rijksoverheid.mgo.component.theme.actionPrimaryDefaultText
import nl.rijksoverheid.mgo.component.theme.actionPrimaryNegativeBackground
import nl.rijksoverheid.mgo.component.theme.actionPrimaryNegativeText
import nl.rijksoverheid.mgo.component.theme.actionSecondaryDefaultBackground
import nl.rijksoverheid.mgo.component.theme.actionSecondaryDefaultText
import nl.rijksoverheid.mgo.component.theme.actionSecondaryNegativeBackground
import nl.rijksoverheid.mgo.component.theme.actionSecondaryNegativeText
import nl.rijksoverheid.mgo.component.theme.actionTertiaryDefaultText
import nl.rijksoverheid.mgo.component.theme.actionTertiaryNegativeText
import nl.rijksoverheid.mgo.component.theme.bodySmall

@Composable
fun MgoButton(
    buttonText: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    buttonTheme: MgoButtonTheme = MgoButtonTheme.PRIMARY_DEFAULT,
) {
    val buttonColors =
        ButtonDefaults.buttonColors(
            backgroundColor = buttonTheme.getBackgroundColor(),
            contentColor = buttonTheme.getContentColor(),
        )
    Button(
        modifier = modifier.heightIn(min = 48.dp),
        contentPadding = PaddingValues(vertical = 12.dp, horizontal = 24.dp),
        content = { Text(text = buttonText, style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold) },
        onClick = onClick,
        colors = buttonColors,
    )
}

enum class MgoButtonTheme {
    PRIMARY_DEFAULT,
    PRIMARY_NEGATIVE,
    SECONDARY_DEFAULT,
    SECONDARY_NEGATIVE,
    TERTIARY_DEFAULT,
    TERTIARY_NEGATIVE,
}

@Composable
fun MgoButtonTheme.getBackgroundColor(): Color {
    return when (this) {
        MgoButtonTheme.PRIMARY_DEFAULT -> MaterialTheme.colors.actionPrimaryDefaultBackground()
        MgoButtonTheme.PRIMARY_NEGATIVE -> MaterialTheme.colors.actionPrimaryNegativeBackground()
        MgoButtonTheme.SECONDARY_DEFAULT -> MaterialTheme.colors.actionSecondaryDefaultBackground()
        MgoButtonTheme.SECONDARY_NEGATIVE -> MaterialTheme.colors.actionSecondaryNegativeBackground()
        MgoButtonTheme.TERTIARY_DEFAULT -> Color.Transparent
        MgoButtonTheme.TERTIARY_NEGATIVE -> Color.Transparent
    }
}

@Composable
fun MgoButtonTheme.getContentColor(): Color {
    return when (this) {
        MgoButtonTheme.PRIMARY_DEFAULT -> MaterialTheme.colors.actionPrimaryDefaultText()
        MgoButtonTheme.PRIMARY_NEGATIVE -> MaterialTheme.colors.actionPrimaryNegativeText()
        MgoButtonTheme.SECONDARY_DEFAULT -> MaterialTheme.colors.actionSecondaryDefaultText()
        MgoButtonTheme.SECONDARY_NEGATIVE -> MaterialTheme.colors.actionSecondaryNegativeText()
        MgoButtonTheme.TERTIARY_DEFAULT -> MaterialTheme.colors.actionTertiaryDefaultText()
        MgoButtonTheme.TERTIARY_NEGATIVE -> MaterialTheme.colors.actionTertiaryNegativeText()
    }
}

@PreviewLightDark
@Composable
internal fun MgoButtonPrimaryPreview() {
    MgoTheme {
        MgoButton(modifier = Modifier.padding(16.dp), buttonText = "Click me", onClick = { }, buttonTheme = MgoButtonTheme.PRIMARY_DEFAULT)
    }
}

@PreviewLightDark
@Composable
internal fun MgoButtonSecondaryPreview() {
    MgoTheme {
        MgoButton(
            modifier = Modifier.padding(16.dp),
            buttonText = "Click me",
            onClick = { },
            buttonTheme = MgoButtonTheme.SECONDARY_DEFAULT,
        )
    }
}
