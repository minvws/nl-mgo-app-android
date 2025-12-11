package nl.rijksoverheid.mgo.component.mgo

import androidx.annotation.DrawableRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import nl.rijksoverheid.mgo.component.theme.MgoTheme

data class MgoBottomButton(
  val text: String,
  val onClick: () -> Unit,
  val isLoading: Boolean = false,
  @DrawableRes val icon: Int? = null,
)

object MgoBottomButtonsTestTag {
  const val PRIMARY_BUTTON = "MgoBottomButtonsPrimaryButton"
  const val SECONDARY_BUTTON = "MgoBottomButtonsSecondaryButton"
}

@Composable
fun MgoBottomButtons(
  primaryButton: MgoBottomButton,
  isElevated: Boolean,
  modifier: Modifier = Modifier,
  hasNavigationBarsPadding: Boolean = true,
  secondaryButton: MgoBottomButton? = null,
) {
  val background = if (isElevated) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.background
  val animatedBackground by animateColorAsState(
    targetValue = background,
    animationSpec = tween(durationMillis = 250),
  )
  Box(
    modifier =
      modifier
        .background(if (LocalInspectionMode.current) background else animatedBackground),
  ) {
    if (isElevated) {
      HorizontalDivider(color = DividerDefaults.color.copy(alpha = 0.25f))
    }
    Column(modifier = Modifier.then(if (hasNavigationBarsPadding) Modifier.navigationBarsPadding() else Modifier).padding(16.dp)) {
      if (secondaryButton != null) {
        MgoButton(
          modifier =
            Modifier
              .fillMaxWidth()
              .padding(bottom = 16.dp)
              .testTag(MgoBottomButtonsTestTag.SECONDARY_BUTTON),
          buttonText = secondaryButton.text,
          onClick = secondaryButton.onClick,
          buttonTheme = MgoButtonTheme.TONAL,
          icon = secondaryButton.icon,
        )
      }
      MgoButton(
        modifier =
          Modifier
            .fillMaxWidth()
            .testTag(MgoBottomButtonsTestTag.PRIMARY_BUTTON),
        buttonText = primaryButton.text,
        onClick = primaryButton.onClick,
        buttonTheme = MgoButtonTheme.SOLID,
        isLoading = primaryButton.isLoading,
        icon = primaryButton.icon,
      )
    }
  }
}

@PreviewLightDark
@Composable
private fun MgoBottomButtonsPrimaryButtonPreview() {
  MgoTheme {
    MgoBottomButtons(
      primaryButton = MgoBottomButton(text = "Primary Button", onClick = {}),
      isElevated = true,
    )
  }
}

@PreviewLightDark
@Composable
private fun MgoBottomButtonsPrimaryAndSecondaryButtonsPreview() {
  MgoTheme {
    MgoBottomButtons(
      primaryButton = MgoBottomButton(text = "Primary Button", onClick = {}),
      secondaryButton = MgoBottomButton(text = "Secondary Button", onClick = {}),
      isElevated = true,
    )
  }
}
