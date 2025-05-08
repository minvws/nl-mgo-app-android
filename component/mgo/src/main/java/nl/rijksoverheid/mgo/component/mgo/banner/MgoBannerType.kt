package nl.rijksoverheid.mgo.component.mgo.banner

import androidx.annotation.DrawableRes
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import nl.rijksoverheid.mgo.component.mgo.R
import nl.rijksoverheid.mgo.component.theme.sentimentCritical
import nl.rijksoverheid.mgo.component.theme.sentimentInformative
import nl.rijksoverheid.mgo.component.theme.sentimentPositive
import nl.rijksoverheid.mgo.component.theme.sentimentWarning

/**
 * Determines the appearance of a [MgoBanner].
 */
enum class MgoBannerType {
  INFO,
  SUCCESS,
  WARNING,
  ERROR,
}

/**
 * @receiver The [MgoBannerType] for which the icon is needed.
 * @return The icon to show in [MgoBanner].
 */
@DrawableRes
fun MgoBannerType.getIcon(): Int {
  return when (this) {
    MgoBannerType.INFO -> R.drawable.ic_banner_info
    MgoBannerType.SUCCESS -> R.drawable.ic_banner_success
    MgoBannerType.WARNING -> R.drawable.ic_banner_warning
    MgoBannerType.ERROR -> R.drawable.ic_banner_error
  }
}

/**
 * @receiver The [MgoBannerType] for which the icon color is needed.
 * @return The icon color to color the icon in [MgoBanner].
 */
@Composable
fun MgoBannerType.getIconColor(): Color {
  return when (this) {
    MgoBannerType.INFO -> MaterialTheme.colorScheme.sentimentInformative()
    MgoBannerType.SUCCESS -> MaterialTheme.colorScheme.sentimentPositive()
    MgoBannerType.WARNING -> MaterialTheme.colorScheme.sentimentWarning()
    MgoBannerType.ERROR -> MaterialTheme.colorScheme.sentimentCritical()
  }
}
