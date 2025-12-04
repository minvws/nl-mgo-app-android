package nl.rijksoverheid.mgo.component.mgo

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ListItemDefaults.contentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import nl.rijksoverheid.mgo.component.theme.ActionsGhostText
import nl.rijksoverheid.mgo.component.theme.ActionsSolidBackground
import nl.rijksoverheid.mgo.component.theme.ActionsSolidText
import nl.rijksoverheid.mgo.component.theme.ActionsTonalBackground
import nl.rijksoverheid.mgo.component.theme.ActionsTonalText
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

@Composable
fun MgoButton(
  buttonText: String,
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
  buttonTheme: MgoButtonTheme = MgoButtonTheme.SOLID,
  buttonHeight: Dp = 48.dp,
  isLoading: Boolean = false,
  @DrawableRes icon: Int? = null,
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
              icon = icon,
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
      modifier = modifier.heightIn(min = buttonHeight),
      contentPadding = PaddingValues(vertical = buttonHeight / 4, horizontal = 24.dp),
      content = {
        if (isLoading) {
          LoadingButtonContent(buttonTheme.getContentColor())
        } else {
          IdleButtonContent(
            buttonText = buttonText,
            icon = icon,
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
  @DrawableRes icon: Int?,
  modifier: Modifier = Modifier,
) {
  Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
    if (icon != null) {
      Image(
        modifier = Modifier.padding(end = 8.dp),
        painter = painterResource(icon),
        contentDescription = null,
      )
    }

    Text(
      text = buttonText,
      style = MaterialTheme.typography.bodyMedium,
      fontWeight = FontWeight.Bold,
    )
  }
}

enum class MgoButtonTheme {
  SOLID,
  TONAL,
  GHOST,
}

@Composable
private fun MgoButtonTheme.getBackgroundColor(): Color =
  when (this) {
    MgoButtonTheme.SOLID -> MaterialTheme.colorScheme.ActionsSolidBackground()
    MgoButtonTheme.TONAL -> MaterialTheme.colorScheme.ActionsTonalBackground()
    MgoButtonTheme.GHOST -> Color.Transparent
  }

@Composable
private fun MgoButtonTheme.getContentColor(): Color =
  when (this) {
    MgoButtonTheme.SOLID -> MaterialTheme.colorScheme.ActionsSolidText()
    MgoButtonTheme.TONAL -> MaterialTheme.colorScheme.ActionsTonalText()
    MgoButtonTheme.GHOST -> MaterialTheme.colorScheme.ActionsGhostText()
  }

@PreviewLightDark
@Composable
internal fun MgoButtonPrimarySolidPreview() {
  MgoTheme {
    MgoButton(modifier = Modifier.padding(16.dp), buttonText = "Click me", onClick = {
    }, buttonTheme = MgoButtonTheme.SOLID)
  }
}

@PreviewLightDark
@Composable
internal fun MgoButtonPrimarySolidWithIconPreview() {
  MgoTheme {
    MgoButton(modifier = Modifier.padding(16.dp), buttonText = "Click me", onClick = {
    }, buttonTheme = MgoButtonTheme.SOLID, icon = R.drawable.ic_digid)
  }
}

@PreviewLightDark
@Composable
internal fun MgoButtonPrimarySolidLoadingPreview() {
  MgoTheme {
    MgoButton(
      modifier = Modifier.padding(16.dp),
      buttonText = "Click me",
      onClick = { },
      buttonTheme = MgoButtonTheme.SOLID,
      isLoading = true,
    )
  }
}

@PreviewLightDark
@Composable
internal fun MgoButtonPrimaryTonalPreview() {
  MgoTheme {
    MgoButton(modifier = Modifier.padding(16.dp), buttonText = "Click me", onClick = {
    }, buttonTheme = MgoButtonTheme.TONAL)
  }
}

@PreviewLightDark
@Composable
internal fun MgoButtonPrimaryGhostPreview() {
  MgoTheme {
    MgoButton(modifier = Modifier.padding(16.dp), buttonText = "Click me", onClick = {
    }, buttonTheme = MgoButtonTheme.GHOST)
  }
}
