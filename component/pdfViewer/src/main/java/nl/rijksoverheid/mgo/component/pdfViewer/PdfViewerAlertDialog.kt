package nl.rijksoverheid.mgo.component.pdfViewer

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import nl.rijksoverheid.mgo.component.mgo.MgoAlertDialog
import nl.rijksoverheid.mgo.component.theme.ActionsGhostText
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

@Composable
fun PdfViewerAlertDialog(
  onClickPositiveButton: () -> Unit,
  onClickNegativeButton: () -> Unit,
  onDismissRequest: () -> Unit,
) {
  MgoAlertDialog(
    heading = stringResource(CopyR.string.export_pdf_dialog_heading),
    subHeading = stringResource(CopyR.string.export_pdf_dialog_subheading),
    positiveButtonText = stringResource(CopyR.string.export_pdf_dialog_create_document),
    positiveButtonTextColor = MaterialTheme.colorScheme.ActionsGhostText(),
    negativeButtonText = stringResource(CopyR.string.common_cancel),
    negativeButtonTextColor = MaterialTheme.colorScheme.ActionsGhostText(),
    onClickPositiveButton = onClickPositiveButton,
    onClickNegativeButton = onClickNegativeButton,
    onDismissRequest = onDismissRequest,
  )
}
