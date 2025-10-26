package nl.rijksoverheid.mgo.component.pincode.pincode

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import nl.rijksoverheid.mgo.component.theme.ActionsGhostText
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.StatesCritical

/**
 * Composable that represents pin code input. It shows five circles that can be filled or outlined.
 * @param pinCode The pin code to display. For example if the list has 3 integers, the first three circles will be
 * filled, and the last two outlined.
 * @param modifier the [Modifier] to be applied.
 * @param error If set to true, will color the circles [MaterialTheme.colorScheme.notificationError] else
 * [MaterialTheme.colorScheme.actionPrimaryDefaultBackground].
 */
@Composable
internal fun PinCode(
  pinCode: List<Int>,
  modifier: Modifier = Modifier,
  error: Boolean = false,
) {
  val color =
    if (error) {
      MaterialTheme.colorScheme.StatesCritical()
    } else {
      MaterialTheme.colorScheme.ActionsGhostText()
    }
  Row(modifier = modifier, horizontalArrangement = Arrangement.spacedBy(16.dp)) {
    PinCodeItemInstance(
      color = color,
      position = 1,
      error = error,
      fill = pinCode.isNotEmpty(),
    )
    PinCodeItemInstance(
      color = color,
      position = 2,
      error = error,
      fill = pinCode.size > 1,
    )
    PinCodeItemInstance(
      color = color,
      position = 3,
      error = error,
      fill = pinCode.size > 2,
    )
    PinCodeItemInstance(
      color = color,
      position = 4,
      error = error,
      fill = pinCode.size > 3,
    )
    PinCodeItemInstance(
      color = color,
      position = 5,
      error = error,
      fill = pinCode.size > 4,
    )
  }
}

@Composable
private fun PinCodeItemInstance(
  color: Color,
  error: Boolean,
  fill: Boolean,
  position: Int,
  modifier: Modifier = Modifier,
) {
  PinCodeItem(
    modifier = modifier.size(32.dp),
    color = color,
    error = error,
    position = position,
    fill = fill,
  )
}

@PreviewLightDark
@Composable
internal fun PinCodeEmptyPreview() {
  MgoTheme {
    PinCode(pinCode = listOf())
  }
}

@PreviewLightDark
@Composable
internal fun PinCodeHalfFilledPreview() {
  MgoTheme {
    PinCode(pinCode = listOf(1, 2, 3))
  }
}
