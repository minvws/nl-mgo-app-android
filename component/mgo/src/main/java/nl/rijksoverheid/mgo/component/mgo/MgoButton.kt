package nl.rijksoverheid.mgo.component.mgo

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
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

@Composable
fun MgoButton(
    buttonText: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    buttonTheme: MgoButtonTheme = MgoButtonTheme.PRIMARY_DEFAULT,
    buttonIcon: Int? = null,
) {
    val backgroundColor = buttonTheme.getBackgroundColor()
    val buttonColors =
        ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = buttonTheme.getContentColor(),
        )
    if (backgroundColor == Color.Transparent) {
        TextButton(
            modifier = modifier.heightIn(min = 48.dp),
            contentPadding = PaddingValues(vertical = 12.dp, horizontal = 24.dp),
            content = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    buttonIcon?.let { buttonIcon ->
                        Icon(
                            modifier = Modifier.padding(end = 8.dp),
                            painter = painterResource(buttonIcon),
                            contentDescription = null
                        )
                    }
                    Text(text = buttonText, style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold)
                }
            },
            onClick = onClick,
            colors = buttonColors,
        )
    } else {
        Button(
            modifier = modifier.heightIn(min = 48.dp),
            contentPadding = PaddingValues(vertical = 12.dp, horizontal = 24.dp),
            content = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    buttonIcon?.let { buttonIcon ->
                        Icon(
                            modifier = Modifier.padding(end = 8.dp),
                            painter = painterResource(buttonIcon),
                            contentDescription = null
                        )
                    }
                    Text(text = buttonText, style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold)
                }
            },
            onClick = onClick,
            colors = buttonColors,
        )
    }
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
private fun MgoButtonTheme.getBackgroundColor(): Color {
    return when (this) {
        MgoButtonTheme.PRIMARY_DEFAULT -> MaterialTheme.colorScheme.actionPrimaryDefaultBackground()
        MgoButtonTheme.PRIMARY_NEGATIVE -> MaterialTheme.colorScheme.actionPrimaryNegativeBackground()
        MgoButtonTheme.SECONDARY_DEFAULT -> MaterialTheme.colorScheme.actionSecondaryDefaultBackground()
        MgoButtonTheme.SECONDARY_NEGATIVE -> MaterialTheme.colorScheme.actionSecondaryNegativeBackground()
        MgoButtonTheme.TERTIARY_DEFAULT -> Color.Transparent
        MgoButtonTheme.TERTIARY_NEGATIVE -> Color.Transparent
    }
}

@Composable
private fun MgoButtonTheme.getContentColor(): Color {
    return when (this) {
        MgoButtonTheme.PRIMARY_DEFAULT -> MaterialTheme.colorScheme.actionPrimaryDefaultText()
        MgoButtonTheme.PRIMARY_NEGATIVE -> MaterialTheme.colorScheme.actionPrimaryNegativeText()
        MgoButtonTheme.SECONDARY_DEFAULT -> MaterialTheme.colorScheme.actionSecondaryDefaultText()
        MgoButtonTheme.SECONDARY_NEGATIVE -> MaterialTheme.colorScheme.actionSecondaryNegativeText()
        MgoButtonTheme.TERTIARY_DEFAULT -> MaterialTheme.colorScheme.actionTertiaryDefaultText()
        MgoButtonTheme.TERTIARY_NEGATIVE -> MaterialTheme.colorScheme.actionTertiaryNegativeText()
    }
}

@PreviewLightDark
@Composable
internal fun MgoButtonPrimaryDefaultPreview() {
    MgoTheme {
        MgoButton(modifier = Modifier.padding(16.dp), buttonText = "Click me", onClick = { }, buttonTheme = MgoButtonTheme.PRIMARY_DEFAULT)
    }
}

@PreviewLightDark
@Composable
internal fun MgoButtonPrimaryDefaultWithIconPreview() {
    MgoTheme {
        MgoButton(
            modifier = Modifier.padding(16.dp),
            buttonText = "Click me",
            onClick = { },
            buttonTheme = MgoButtonTheme.PRIMARY_DEFAULT,
            buttonIcon = R.drawable.ic_snackbar_info,
        )
    }
}

@PreviewLightDark
@Composable
internal fun MgoButtonPrimaryNegativePreview() {
    MgoTheme {
        MgoButton(modifier = Modifier.padding(16.dp), buttonText = "Click me", onClick = { }, buttonTheme = MgoButtonTheme.PRIMARY_NEGATIVE)
    }
}

@PreviewLightDark
@Composable
internal fun MgoButtonSecondaryDefaultPreview() {
    MgoTheme {
        MgoButton(
            modifier = Modifier.padding(16.dp),
            buttonText = "Click me",
            onClick = { },
            buttonTheme = MgoButtonTheme.SECONDARY_DEFAULT,
        )
    }
}

@PreviewLightDark
@Composable
internal fun MgoButtonSecondaryNegativePreview() {
    MgoTheme {
        MgoButton(
            modifier = Modifier.padding(16.dp),
            buttonText = "Click me",
            onClick = { },
            buttonTheme = MgoButtonTheme.SECONDARY_NEGATIVE,
        )
    }
}

@PreviewLightDark
@Composable
internal fun MgoButtonTertiaryDefaultPreview() {
    MgoTheme {
        MgoButton(
            modifier = Modifier.padding(16.dp),
            buttonText = "Click me",
            onClick = { },
            buttonTheme = MgoButtonTheme.TERTIARY_DEFAULT,
        )
    }
}

@PreviewLightDark
@Composable
internal fun MgoButtonTertiaryDefaultWithIconPreview() {
    MgoTheme {
        MgoButton(
            modifier = Modifier.padding(16.dp),
            buttonText = "Click me",
            onClick = { },
            buttonTheme = MgoButtonTheme.TERTIARY_DEFAULT,
            buttonIcon = R.drawable.ic_snackbar_info,
        )
    }
}

@PreviewLightDark
@Composable
internal fun MgoButtonTertiaryNegativePreview() {
    MgoTheme {
        MgoButton(
            modifier = Modifier.padding(16.dp),
            buttonText = "Click me",
            onClick = { },
            buttonTheme = MgoButtonTheme.TERTIARY_NEGATIVE,
        )
    }
}
