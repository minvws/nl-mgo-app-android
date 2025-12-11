package nl.rijksoverheid.mgo.feature.dashboard.uiSchema

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import nl.rijksoverheid.mgo.component.mgo.SetCorrectStatusBarIconColor
import nl.rijksoverheid.mgo.component.theme.LabelsPrimary
import nl.rijksoverheid.mgo.component.theme.LabelsSecondary
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.headlineExtraSmall
import nl.rijksoverheid.mgo.data.pft.Pft
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

@Composable
fun PftBottomSheet(
  pft: Pft,
  onDismissRequest: () -> Unit,
) {
  val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)

  ModalBottomSheet(
    modifier = Modifier.testTag(UiSchemaBottomSheetTestTag.SHEET),
    contentWindowInsets = { WindowInsets(0) },
    onDismissRequest = onDismissRequest,
    sheetState = sheetState,
    dragHandle = { BottomSheetDefaults.DragHandle() },
  ) {
    SetCorrectStatusBarIconColor()
    PftBottomSheetContent(pft)
  }
}

@Composable
private fun PftBottomSheetContent(pft: Pft) {
  Scaffold { contentPadding ->
    Column(modifier = Modifier.consumeWindowInsets(contentPadding).padding(horizontal = 16.dp)) {
      Text(
        text = pft.name ?: "",
        style = MaterialTheme.typography.headlineMedium,
        color = MaterialTheme.colorScheme.LabelsPrimary(),
      )
      pft.synonym?.let { synonym ->
        Text(
          modifier = Modifier.padding(top = 4.dp),
          text = stringResource(CopyR.string.patientfriendlyterms_synonym, synonym),
          style = MaterialTheme.typography.headlineExtraSmall,
          color = MaterialTheme.colorScheme.LabelsSecondary(),
        )
      }
      Text(
        modifier = Modifier.padding(top = 12.dp),
        text = pft.description,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.LabelsPrimary(),
      )
    }
  }
}

@PreviewLightDark
@Composable
internal fun PftBottomSheetPreview() {
  MgoTheme {
    val pft =
      Pft(
        name = "Atriumfibrilleren",
        synonym = "boezemfibrilleren, atriumfibrillatie",
        description = "Dit betekent dat het bovenste deel van je hart onregelmatig en vaak ook te snel klopt. Hierdoor wordt je bloed niet goed rondgepompt.",
      )

    PftBottomSheetContent(
      pft = pft,
    )
  }
}
