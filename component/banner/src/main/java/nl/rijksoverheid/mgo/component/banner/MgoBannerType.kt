package nl.rijksoverheid.mgo.component.banner

import androidx.annotation.DrawableRes
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import nl.rijksoverheid.mgo.component.theme.notificationError
import nl.rijksoverheid.mgo.component.theme.notificationInformation
import nl.rijksoverheid.mgo.component.theme.notificationSuccess
import nl.rijksoverheid.mgo.component.theme.notificationWarning

enum class MgoBannerType {
    INFO,
    SUCCESS,
    WARNING,
    ERROR,
}

@DrawableRes
fun MgoBannerType.getIcon(): Int {
    return when (this) {
        MgoBannerType.INFO -> R.drawable.ic_banner_info
        MgoBannerType.SUCCESS -> R.drawable.ic_banner_success
        MgoBannerType.WARNING -> R.drawable.ic_banner_warning
        MgoBannerType.ERROR -> R.drawable.ic_banner_error
    }
}

@Composable
fun MgoBannerType.getIconColor(): Color {
    return when (this) {
        MgoBannerType.INFO -> MaterialTheme.colors.notificationInformation()
        MgoBannerType.SUCCESS -> MaterialTheme.colors.notificationSuccess()
        MgoBannerType.WARNING -> MaterialTheme.colors.notificationWarning()
        MgoBannerType.ERROR -> MaterialTheme.colors.notificationError()
    }
}
