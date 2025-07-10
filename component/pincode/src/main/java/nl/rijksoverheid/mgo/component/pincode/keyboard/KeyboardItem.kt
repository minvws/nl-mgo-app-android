package nl.rijksoverheid.mgo.component.pincode.keyboard

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import nl.rijksoverheid.mgo.component.pincode.R
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.contentPrimary

object KeyboardItemNumberTestTag {
  fun button(number: Int): String {
    return "keyboardItemButton+$number"
  }
}

/**
 * Composable that shows a number in a keyboard, to use in [Keyboard].
 * @param number The number to show.
 * @param onClick Called when number is clicked.
 * @param modifier the [Modifier] to be applied.
 */
@Composable
internal fun KeyboardItemNumber(
  number: Int,
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
) {
  Button(
    modifier = modifier.testTag(KeyboardItemNumberTestTag.button(number)),
    colors =
      ButtonDefaults.buttonColors(
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface,
      ),
    shape = RoundedCornerShape(25.dp),
    elevation = ButtonDefaults.buttonElevation(1.dp),
    onClick = onClick,
  ) {
    Text(
      text = number.toString(),
      style = MaterialTheme.typography.bodyMedium.copy(fontSize = 28.sp),
    )
  }
}

@Composable
internal fun KeyboardItemIcon(
  @DrawableRes icon: Int,
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
) {
  Button(
    modifier = modifier,
    colors =
      ButtonDefaults.buttonColors(
        containerColor = Color.Transparent,
        contentColor = MaterialTheme.colorScheme.contentPrimary(),
      ),
    elevation = ButtonDefaults.buttonElevation(0.dp, 0.dp, 0.dp, 0.dp),
    shape = RoundedCornerShape(25.dp),
    onClick = onClick,
  ) {
    Icon(
      painter = painterResource(icon),
      contentDescription = null,
    )
  }
}

@PreviewLightDark
@Composable
internal fun KeyboardItemNumberPreview() {
  MgoTheme {
    KeyboardItemNumber(
      modifier =
        Modifier
          .size(100.dp)
          .padding(16.dp),
      number = 1,
      onClick = {},
    )
  }
}

@PreviewLightDark
@Composable
internal fun KeyboardItemIconPreview() {
  MgoTheme {
    KeyboardItemIcon(
      modifier =
        Modifier
          .size(100.dp)
          .padding(16.dp),
      onClick = {},
      icon = R.drawable.ic_keyboard_backspace,
    )
  }
}
