package nl.rijksoverheid.mgo.feature.dashboard.pdfViewer

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch
import nl.rijksoverheid.mgo.component.mgo.MgoTopAppBar

@Composable
fun PdfViewerBottomSheet(
  openSheet: Boolean,
  onDismissRequest: () -> Unit,
) {
  val coroutineScope = rememberCoroutineScope()
  val sheetState = rememberModalBottomSheetState()

  if (openSheet) {
    ModalBottomSheet(
      onDismissRequest = onDismissRequest,
      sheetState = sheetState,
      dragHandle = { BottomSheetDefaults.DragHandle() },
    ) {
      Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
          MgoTopAppBar(
            title = "Medicijnen",
            windowInsets = WindowInsets(0),
            navigationIcon = Icons.Default.Close,
            onNavigateBack = { coroutineScope.launch { sheetState.hide() } },
          )
        },
        content = {
        },
      )
    }
  }
}
