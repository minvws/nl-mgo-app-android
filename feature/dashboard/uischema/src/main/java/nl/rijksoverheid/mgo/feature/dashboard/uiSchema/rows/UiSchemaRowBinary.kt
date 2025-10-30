package nl.rijksoverheid.mgo.feature.dashboard.uiSchema.rows

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import nl.rijksoverheid.mgo.component.pdfViewer.PdfViewerBottomSheet
import nl.rijksoverheid.mgo.component.pdfViewer.PdfViewerState
import nl.rijksoverheid.mgo.component.theme.ActionsGhostText
import nl.rijksoverheid.mgo.component.theme.BackgroundsTertiary
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.StatesCritical
import nl.rijksoverheid.mgo.component.theme.StatesInformative
import nl.rijksoverheid.mgo.component.uiSchema.UISchemaRow
import nl.rijksoverheid.mgo.data.fhir.FhirBinary
import nl.rijksoverheid.mgo.feature.dashboard.uiSchema.R
import java.io.File
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

@Composable
internal fun UiSchemaRowBinary(
  row: UISchemaRow.Binary,
  onClick: (row: UISchemaRow.Binary.NotDownloaded) -> Unit,
  modifier: Modifier = Modifier,
) {
  var openPdfViewer: File? by remember { mutableStateOf(null) }
  openPdfViewer?.let { file ->
    if (!LocalInspectionMode.current) {
      PdfViewerBottomSheet(
        appBarTitle = file.name,
        state = PdfViewerState.Loaded(file),
        onDismissRequest = {
          openPdfViewer = null
        },
      )
    }
  }

  // Immediately share file when it is finished downloading
  LaunchedEffect(row) {
    if (row is UISchemaRow.Binary.Downloaded) {
      openPdfViewer = row.binary.file
    }
  }

  when (row) {
    is UISchemaRow.Binary.NotDownloaded.Idle -> {
      UiSchemaRowBinary(row = row, loading = false, modifier = modifier.clickable { onClick(row) })
    }

    is UISchemaRow.Binary.Loading -> {
      UiSchemaRowBinary(row = row, loading = true, modifier = modifier)
    }

    is UISchemaRow.Binary.Downloaded -> {
      UiSchemaRowBinary(
        row = row,
        loading = false,
        modifier =
          modifier.clickable {
            openPdfViewer = row.binary.file
          },
      )
    }
    is UISchemaRow.Binary.NotDownloaded.Error -> {
      UISchemaRowError(
        icon = R.drawable.ic_error,
        iconTint = MaterialTheme.colorScheme.StatesCritical(),
        heading = CopyR.string.hc_documents_error,
        onTryAgain = {
          onClick(row)
        },
      )
    }

    is UISchemaRow.Binary.Empty -> {
      UISchemaRowError(
        icon = R.drawable.ic_info,
        iconTint = MaterialTheme.colorScheme.StatesInformative(),
        heading = CopyR.string.hc_documents_no_document,
        onTryAgain = null,
      )
    }
  }
}

@Composable
private fun UiSchemaRowBinary(
  row: UISchemaRow.Binary,
  loading: Boolean,
  modifier: Modifier = Modifier,
) {
  Row(
    modifier =
      modifier
        .fillMaxWidth()
        .padding(16.dp),
    verticalAlignment = Alignment.CenterVertically,
  ) {
    Text(
      modifier =
        Modifier
          .weight(1f)
          .padding(end = 8.dp),
      color = MaterialTheme.colorScheme.ActionsGhostText(),
      text = row.value,
      style = MaterialTheme.typography.bodyMedium,
    )

    if (loading) {
      CircularProgressIndicator(
        modifier = Modifier.size(24.dp),
        strokeWidth = 3.dp,
        trackColor = MaterialTheme.colorScheme.ActionsGhostText(),
        color = MaterialTheme.colorScheme.BackgroundsTertiary(),
      )
    } else {
      Icon(
        painter = painterResource(R.drawable.ic_attachment),
        tint = MaterialTheme.colorScheme.ActionsGhostText(),
        contentDescription = null,
      )
    }
  }
}

@Composable
private fun UISchemaRowError(
  @DrawableRes icon: Int,
  iconTint: Color,
  @StringRes heading: Int,
  onTryAgain: (() -> Unit)?,
  modifier: Modifier = Modifier,
) {
  Column(
    modifier =
      modifier
        .fillMaxWidth()
        .padding(24.dp),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally,
  ) {
    Icon(painter = painterResource(icon), tint = iconTint, contentDescription = null)
    Text(
      modifier = Modifier.padding(top = 8.dp),
      text = stringResource(heading),
      textAlign = TextAlign.Center,
      style = MaterialTheme.typography.bodyMedium,
    )
    if (onTryAgain != null) {
      TextButton(onClick = onTryAgain) {
        Text(
          text = stringResource(CopyR.string.common_try_again),
          textAlign = TextAlign.Center,
          style = MaterialTheme.typography.bodyMedium,
          fontWeight = FontWeight.Bold,
          color = MaterialTheme.colorScheme.ActionsGhostText(),
        )
      }
    }
  }
}

@PreviewLightDark
@Composable
internal fun UiSchemaRowBinaryIdlePreview() {
  MgoTheme {
    UiSchemaRowBinary(
      row = UISchemaRow.Binary.NotDownloaded.Idle(heading = "Heading", value = "Value", binary = ""),
      onClick = {},
    )
  }
}

@PreviewLightDark
@Composable
internal fun UiSchemaRowBinaryLoadingPreview() {
  MgoTheme {
    UiSchemaRowBinary(
      row = UISchemaRow.Binary.Loading(heading = "Heading", value = "Value"),
      onClick = {},
    )
  }
}

@PreviewLightDark
@Composable
internal fun UiSchemaRowBinaryDownloadedPreview() {
  MgoTheme {
    UiSchemaRowBinary(
      row = UISchemaRow.Binary.Downloaded(heading = "Heading", value = "Value", binary = FhirBinary(file = File(""), contentType = "application/pdf")),
      onClick = {},
    )
  }
}

@PreviewLightDark
@Composable
internal fun UiSchemaRowBinaryEmptyPreview() {
  MgoTheme {
    UiSchemaRowBinary(
      row = UISchemaRow.Binary.Empty(heading = "Heading", value = "Value"),
      onClick = {},
    )
  }
}

@PreviewLightDark
@Composable
internal fun UiSchemaRowBinaryErrorPreview() {
  MgoTheme {
    UiSchemaRowBinary(
      row = UISchemaRow.Binary.NotDownloaded.Error(heading = "Heading", value = "Value", binary = ""),
      onClick = {},
    )
  }
}
