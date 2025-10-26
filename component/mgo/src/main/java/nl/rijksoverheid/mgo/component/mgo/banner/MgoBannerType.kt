package nl.rijksoverheid.mgo.component.mgo.banner

import androidx.annotation.DrawableRes
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import nl.rijksoverheid.mgo.component.mgo.R
import nl.rijksoverheid.mgo.component.theme.StatesCritical
import nl.rijksoverheid.mgo.component.theme.StatesInformative
import nl.rijksoverheid.mgo.component.theme.StatesPositive
import nl.rijksoverheid.mgo.component.theme.StatesWarning

enum class MgoBannerType {
  INFO,
  SUCCESS,
  WARNING,
  ERROR,
}

@DrawableRes
fun MgoBannerType.getIcon(): Int =
  when (this) {
    MgoBannerType.INFO -> R.drawable.ic_banner_info
    MgoBannerType.SUCCESS -> R.drawable.ic_banner_success
    MgoBannerType.WARNING -> R.drawable.ic_banner_warning
    MgoBannerType.ERROR -> R.drawable.ic_banner_error
  }

@Composable
fun MgoBannerType.getIconColor(): Color =
  when (this) {
    MgoBannerType.INFO -> MaterialTheme.colorScheme.StatesInformative()
    MgoBannerType.SUCCESS -> MaterialTheme.colorScheme.StatesPositive()
    MgoBannerType.WARNING -> MaterialTheme.colorScheme.StatesWarning()
    MgoBannerType.ERROR -> MaterialTheme.colorScheme.StatesCritical()
  }
