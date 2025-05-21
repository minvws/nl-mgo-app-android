package nl.rijksoverheid.mgo.component.mgo

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.OpenInNew
import androidx.compose.material.icons.outlined.OpenInNew
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItemDefaults.contentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.digid
import nl.rijksoverheid.mgo.component.theme.interactivePrimaryCriticalBackground
import nl.rijksoverheid.mgo.component.theme.interactivePrimaryCriticalText
import nl.rijksoverheid.mgo.component.theme.interactivePrimaryDefaultBackground
import nl.rijksoverheid.mgo.component.theme.interactivePrimaryDefaultText
import nl.rijksoverheid.mgo.component.theme.interactiveSecondaryCriticalBackground
import nl.rijksoverheid.mgo.component.theme.interactiveSecondaryCriticalText
import nl.rijksoverheid.mgo.component.theme.interactiveSecondaryDefaultBackground
import nl.rijksoverheid.mgo.component.theme.interactiveSecondaryDefaultText
import nl.rijksoverheid.mgo.component.theme.interactiveTertiaryCriticalText
import nl.rijksoverheid.mgo.component.theme.interactiveTertiaryDefaultText
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

/**
 * Composable that shows a button.
 * @param buttonText The text to show in the button.
 * @param onClick Called when clicking on the button.
 * @param modifier the [Modifier] to be applied.
 * @param buttonTheme The theme for this button. Defaults to [MgoButtonTheme.PRIMARY_DEFAULT].
 * @param isLoading If set to true, will display a progress loader next to the button text.
 */
@Composable
fun MgoButton(
  buttonText: String,
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
  buttonTheme: MgoButtonTheme = MgoButtonTheme.PRIMARY_DEFAULT,
  isLoading: Boolean = false,
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
          if (isLoading) {
            LoadingButtonContent(contentColor)
          } else {
            IdleButtonContent(
              buttonText = buttonText,
              materialIcon = buttonTheme.getMaterialIcon(),
              icon = buttonTheme.getIcon(),
            )
          }
        }
      },
      onClick = {
        if (!isLoading) {
          onClick()
        }
      },
      colors = buttonColors,
    )
  } else {
    Button(
      modifier = modifier.heightIn(min = 48.dp),
      contentPadding = PaddingValues(vertical = 12.dp, horizontal = 24.dp),
      content = {
        if (isLoading) {
          LoadingButtonContent(buttonTheme.getContentColor())
        } else {
          IdleButtonContent(
            buttonText = buttonText,
            materialIcon = buttonTheme.getMaterialIcon(),
            icon = buttonTheme.getIcon(),
          )
        }
      },
      onClick = {
        if (!isLoading) {
          onClick()
        }
      },
      colors = buttonColors,
    )
  }
}

@Composable
private fun LoadingButtonContent(
  contentColor: Color,
  modifier: Modifier = Modifier,
) {
  Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
    CircularProgressIndicator(
      modifier = Modifier.size(22.dp),
      strokeWidth = 3.dp,
      trackColor = contentColor.copy(alpha = 0.5f),
      color = contentColor,
    )
    Text(
      modifier = Modifier.padding(start = 8.dp),
      text = stringResource(CopyR.string.common_loading),
      style = MaterialTheme.typography.bodyMedium,
      fontWeight = FontWeight.Bold,
    )
  }
}

@Composable
private fun IdleButtonContent(
  buttonText: String,
  materialIcon: ImageVector?,
  @DrawableRes icon: Int?,
  modifier: Modifier = Modifier,
) {
  Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
    when {
      materialIcon != null -> {
        Icon(
          modifier = Modifier.padding(end = 8.dp),
          imageVector = materialIcon,
          contentDescription = null,
        )
      }

      icon != null -> {
        Image(
          modifier = Modifier.padding(end = 8.dp),
          painter = painterResource(icon),
          contentDescription = null,
        )
      }
    }

    Text(
      text = buttonText,
      style = MaterialTheme.typography.bodyMedium,
      fontWeight = FontWeight.Bold,
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
  LINK,
  DIGID,
}

@Composable
private fun MgoButtonTheme.getBackgroundColor(): Color {
  return when (this) {
    MgoButtonTheme.PRIMARY_DEFAULT -> MaterialTheme.colorScheme.interactivePrimaryDefaultBackground()
    MgoButtonTheme.PRIMARY_NEGATIVE -> MaterialTheme.colorScheme.interactivePrimaryCriticalBackground()
    MgoButtonTheme.SECONDARY_DEFAULT -> MaterialTheme.colorScheme.interactiveSecondaryDefaultBackground()
    MgoButtonTheme.SECONDARY_NEGATIVE -> MaterialTheme.colorScheme.interactiveSecondaryCriticalBackground()
    MgoButtonTheme.DIGID -> MaterialTheme.colorScheme.digid()
    MgoButtonTheme.TERTIARY_DEFAULT -> Color.Transparent
    MgoButtonTheme.TERTIARY_NEGATIVE -> Color.Transparent
    MgoButtonTheme.LINK -> MaterialTheme.colorScheme.interactiveSecondaryDefaultBackground()
  }
}

private fun MgoButtonTheme.getMaterialIcon(): ImageVector? {
  return when (this) {
    MgoButtonTheme.LINK -> Icons.AutoMirrored.Outlined.OpenInNew
    else -> null
  }
}

@DrawableRes
private fun MgoButtonTheme.getIcon(): Int? {
  return when (this) {
    MgoButtonTheme.DIGID -> R.drawable.ic_digid
    else -> null
  }
}

@Composable
private fun MgoButtonTheme.getContentColor(): Color {
  return when (this) {
    MgoButtonTheme.PRIMARY_DEFAULT -> MaterialTheme.colorScheme.interactivePrimaryDefaultText()
    MgoButtonTheme.PRIMARY_NEGATIVE -> MaterialTheme.colorScheme.interactivePrimaryCriticalText()
    MgoButtonTheme.SECONDARY_DEFAULT -> MaterialTheme.colorScheme.interactiveSecondaryDefaultText()
    MgoButtonTheme.SECONDARY_NEGATIVE -> MaterialTheme.colorScheme.interactiveSecondaryCriticalText()
    MgoButtonTheme.TERTIARY_DEFAULT -> MaterialTheme.colorScheme.interactiveTertiaryDefaultText()
    MgoButtonTheme.TERTIARY_NEGATIVE -> MaterialTheme.colorScheme.interactiveTertiaryCriticalText()
    MgoButtonTheme.DIGID -> MaterialTheme.colorScheme.interactivePrimaryDefaultText(true)
    MgoButtonTheme.LINK -> MaterialTheme.colorScheme.interactiveSecondaryDefaultText()
  }
}

@PreviewLightDark
@Composable
internal fun MgoButtonPrimaryDefaultPreview() {
  MgoTheme {
    MgoButton(modifier = Modifier.padding(16.dp), buttonText = "Click me", onClick = {
    }, buttonTheme = MgoButtonTheme.PRIMARY_DEFAULT)
  }
}

@PreviewLightDark
@Composable
internal fun MgoButtonPrimaryDefaultLoadingPreview() {
  MgoTheme {
    MgoButton(
      modifier = Modifier.padding(16.dp),
      buttonText = "Click me",
      onClick = { },
      buttonTheme = MgoButtonTheme.PRIMARY_DEFAULT,
      isLoading = true,
    )
  }
}

@PreviewLightDark
@Composable
internal fun MgoButtonPrimaryNegativePreview() {
  MgoTheme {
    MgoButton(modifier = Modifier.padding(16.dp), buttonText = "Click me", onClick = {
    }, buttonTheme = MgoButtonTheme.PRIMARY_NEGATIVE)
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
internal fun MgoButtonTertiaryDefaultLoadingPreview() {
  MgoTheme {
    MgoButton(
      modifier = Modifier.padding(16.dp),
      buttonText = "Click me",
      onClick = { },
      buttonTheme = MgoButtonTheme.TERTIARY_DEFAULT,
      isLoading = true,
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

@PreviewLightDark
@Composable
internal fun MgoButtonDigidPreview() {
  MgoTheme {
    MgoButton(
      modifier = Modifier.padding(16.dp),
      buttonText = "Click me",
      onClick = { },
      buttonTheme = MgoButtonTheme.DIGID,
    )
  }
}

@PreviewLightDark
@Composable
internal fun MgoButtonLinkPreview() {
  MgoTheme {
    MgoButton(
      modifier = Modifier.padding(16.dp),
      buttonText = "Click me",
      onClick = { },
      buttonTheme = MgoButtonTheme.LINK,
    )
  }
}
