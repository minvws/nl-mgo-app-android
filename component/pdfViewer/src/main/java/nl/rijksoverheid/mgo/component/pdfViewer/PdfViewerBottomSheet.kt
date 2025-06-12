package nl.rijksoverheid.mgo.component.pdfViewer

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import net.engawapg.lib.zoomable.ExperimentalZoomableApi
import net.engawapg.lib.zoomable.rememberZoomState
import net.engawapg.lib.zoomable.zoomableWithScroll
import nl.rijksoverheid.mgo.component.mgo.MgoCard
import nl.rijksoverheid.mgo.component.mgo.MgoTopAppBar
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.contentSecondary
import sendFileToOtherApp
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

/**
 * Displays a bottom sheet that renders a PDF file.
 *
 * This composable presents the contents of the given PDF file inside a modal bottom sheet.
 *
 * @param appBarTitle The text to display in the app bar.
 * @param state The state of the PDF file. Either loading or providing a pdf file to display.
 * @param onDismissRequest Callback invoked when the user requests to dismiss the sheet.
 */
@Composable
fun PdfViewerBottomSheet(
  appBarTitle: String,
  state: PdfViewerState,
  onDismissRequest: () -> Unit,
) {
  val context = LocalContext.current
  val coroutineScope = rememberCoroutineScope()
  val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

  ModalBottomSheet(
    contentWindowInsets = { WindowInsets(0) },
    onDismissRequest = onDismissRequest,
    sheetState = sheetState,
    dragHandle = { BottomSheetDefaults.DragHandle() },
  ) {
    Scaffold(
      modifier = Modifier.fillMaxWidth().fillMaxHeight(0.95f),
      topBar = {
        MgoTopAppBar(
          title = appBarTitle,
          windowInsets = WindowInsets(0),
          navigationIcon = Icons.Default.Close,
          containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
          onNavigateBack = {
            coroutineScope.launch {
              sheetState.hide()
              onDismissRequest()
            }
          },
          actions = {
            if (state is PdfViewerState.Loaded) {
              IconButton({ context.sendFileToOtherApp(file = state.file, contentType = "application/pdf") }) {
                Icon(Icons.Outlined.Share, null)
              }
            }
          },
        )
      },
      content = { contentPadding ->
        when (state) {
          is PdfViewerState.Loading -> {
            PdfLoadingContent()
          }
          is PdfViewerState.Loaded -> {
            var bitmaps: List<Bitmap> by remember { mutableStateOf(listOf()) }
            LaunchedEffect(Unit) {
              withContext(Dispatchers.IO) {
                bitmaps = createBitmaps(state.file)
              }
            }

            Box(modifier = Modifier.fillMaxSize().padding(contentPadding)) {
              if (bitmaps.isEmpty()) {
                PdfLoadingContent()
              } else {
                PdfLoadedContent(bitmaps)
              }
            }
          }
        }
      },
    )
  }
}

@Composable
private fun PdfLoadingContent() {
  Column(modifier = Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
    CircularProgressIndicator(
      modifier = Modifier.size(48.dp),
      strokeWidth = 6.dp,
    )
    Text(
      modifier = Modifier.padding(top = 16.dp),
      text = stringResource(CopyR.string.pdf_viewer_loading),
      color = MaterialTheme.colorScheme.contentSecondary(),
      style = MaterialTheme.typography.bodyMedium,
    )
  }
}

@OptIn(ExperimentalZoomableApi::class)
@Composable
private fun PdfLoadedContent(bitmaps: List<Bitmap>) {
  LazyColumn(modifier = Modifier.zoomableWithScroll(rememberZoomState()), contentPadding = PaddingValues(top = 16.dp)) {
    items(bitmaps.size) { position ->
      val bitmap = bitmaps[position]
      MgoCard(
        modifier =
          Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
        shape = RectangleShape,
      ) {
        Image(
          modifier = Modifier.fillMaxWidth(),
          bitmap = bitmap.asImageBitmap(),
          contentDescription = null,
          contentScale = ContentScale.FillWidth,
        )
      }
    }
  }
}

@Preview
@Composable
internal fun PdfViewerLoadingPreview() {
  MgoTheme {
    PdfLoadingContent()
  }
}
