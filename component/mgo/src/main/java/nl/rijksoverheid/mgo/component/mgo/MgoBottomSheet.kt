package nl.rijksoverheid.mgo.component.mgo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.window.DialogWindowProvider
import androidx.core.view.WindowCompat
import nl.rijksoverheid.mgo.component.theme.theme.LocalAppThemeProvider
import nl.rijksoverheid.mgo.component.theme.theme.isDarkTheme

@Composable
fun MgoBottomSheet(
  sheetState: SheetState,
  onDismissRequest: () -> Unit,
  modifier: Modifier = Modifier,
  content: @Composable ColumnScope.() -> Unit,
) {
  val maxHeight = LocalWindowInfo.current.containerDpSize.height
  ModalBottomSheet(
    modifier = modifier,
    contentWindowInsets = { WindowInsets(0) },
    onDismissRequest = onDismissRequest,
    sheetState = sheetState,
    dragHandle = { BottomSheetDefaults.DragHandle() },
  ) {
    // Don't let bottom sheet go inside the status bar as per design.
    Column(modifier = Modifier.fillMaxHeight(0.95f)) {
      SetCorrectStatusBarIconColor()
      content()
    }
  }
}

/**
 * Since we have a override in the app where you can select if you want to view the app in dark or light mode,
 * we also need to adjust the status bar color in a bottom sheet ourselves. If we don't do that it will just follow the system settings.
 */
@Composable
private fun SetCorrectStatusBarIconColor() {
  val view = LocalView.current
  (view.parent as? DialogWindowProvider)?.window?.let { window ->
    WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !LocalAppThemeProvider.current.appTheme.isDarkTheme()
  }
}
