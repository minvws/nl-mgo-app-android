package nl.rijksoverheid.mgo.feature.settings.about.safety

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Represents a list item in [SettingsAboutSafetyScreen].
 */
data class SafetyItem(
  val icon: ImageVector,
  @StringRes val heading: Int,
  @StringRes val subHeading: Int,
)
