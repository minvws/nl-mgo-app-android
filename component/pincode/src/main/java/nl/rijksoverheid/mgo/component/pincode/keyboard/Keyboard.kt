package nl.rijksoverheid.mgo.component.pincode.keyboard

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import nl.rijksoverheid.mgo.component.pincode.R
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

/**
 * Composable that shows a keyboard.
 * @param onPressNumber Called when a number has been clicked on the keyboard.
 * @param onPressBackspace Called when the backspace has been clicked on the keyboard.
 * @param modifier the [Modifier] to be applied.
 * @param hasBiometric If the biometric button should be visible.
 * @param onPressBiometric Called when the biometric button is clicked.
 */
@Composable
internal fun Keyboard(
  onPressNumber: (number: Int) -> Unit,
  onPressBackspace: () -> Unit,
  modifier: Modifier = Modifier,
  hasBiometric: Boolean = false,
  onPressBiometric: (() -> Unit)? = null,
) {
  Box(modifier = modifier) {
    Column(
      modifier =
        modifier
          .wrapContentWidth()
          .height(IntrinsicSize.Min),
      verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
      Row(
        modifier = Modifier.weight(1f),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
      ) {
        KeyboardItemNumberInstance(number = 1, onPressNumber = onPressNumber)
        KeyboardItemNumberInstance(number = 2, onPressNumber = onPressNumber)
        KeyboardItemNumberInstance(number = 3, onPressNumber = onPressNumber)
      }
      Row(
        modifier = Modifier.weight(1f),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
      ) {
        KeyboardItemNumberInstance(number = 4, onPressNumber = onPressNumber)
        KeyboardItemNumberInstance(number = 5, onPressNumber = onPressNumber)
        KeyboardItemNumberInstance(number = 6, onPressNumber = onPressNumber)
      }
      Row(
        modifier = Modifier.weight(1f),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
      ) {
        KeyboardItemNumberInstance(number = 7, onPressNumber = onPressNumber)
        KeyboardItemNumberInstance(number = 8, onPressNumber = onPressNumber)
        KeyboardItemNumberInstance(number = 9, onPressNumber = onPressNumber)
      }
      Row(
        modifier = Modifier.weight(1f),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
      ) {
        val biometricIconAlpha = if (hasBiometric) 1f else 0f
        val biometricContentDescriptionLabel =
          stringResource(CopyR.string.pincode_biometric_voiceover)
        KeyboardItemIconInstance(
          modifier =
            Modifier
              .alpha(biometricIconAlpha)
              .semantics { contentDescription = biometricContentDescriptionLabel },
          icon = R.drawable.ic_keyboard_fingerprint,
          onPressIcon = { onPressBiometric?.invoke() },
        )
        KeyboardItemNumberInstance(number = 0, onPressNumber = onPressNumber)

        val backspaceContentDescriptionLabel =
          stringResource(CopyR.string.pincode_erase_voiceover)
        KeyboardItemIconInstance(
          modifier =
            Modifier
              .semantics { contentDescription = backspaceContentDescriptionLabel },
          icon = R.drawable.ic_keyboard_backspace,
          onPressIcon = { onPressBackspace() },
        )
      }
    }
  }
}

@Composable
private fun RowScope.KeyboardItemNumberInstance(
  number: Int,
  onPressNumber: (number: Int) -> Unit,
) {
  KeyboardItemNumber(
    modifier =
      Modifier
        .weight(1f)
        .aspectRatio(2.25f),
    onClick = { onPressNumber(number) },
    number = number,
  )
}

@Composable
private fun RowScope.KeyboardItemIconInstance(
  @DrawableRes icon: Int,
  onPressIcon: () -> Unit,
  modifier: Modifier = Modifier,
) {
  KeyboardItemIcon(
    modifier =
      modifier
        .weight(1f)
        .aspectRatio(2.25f),
    onClick = onPressIcon,
    icon = icon,
  )
}

@PreviewLightDark
@Composable
internal fun KeyboardPreview() {
  MgoTheme {
    Keyboard(onPressNumber = { }, onPressBackspace = {})
  }
}

@PreviewLightDark
@Composable
internal fun KeyboardWithBiometricPreview() {
  MgoTheme {
    Keyboard(onPressNumber = { }, onPressBackspace = {}, hasBiometric = true)
  }
}
