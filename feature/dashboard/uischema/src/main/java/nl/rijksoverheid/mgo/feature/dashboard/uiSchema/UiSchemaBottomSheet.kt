package nl.rijksoverheid.mgo.feature.dashboard.uiSchema

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import nl.rijksoverheid.mgo.data.fhirParser.mgoResource.MgoResource
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization

@Composable
fun UiSchemaBottomSheet(
  organization: MgoOrganization,
  mgoResource: MgoResource,
  isSummary: Boolean,
  onDismissRequest: () -> Unit,
) {
  val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

  ModalBottomSheet(
    contentWindowInsets = { WindowInsets(0) },
    onDismissRequest = onDismissRequest,
    sheetState = sheetState,
    dragHandle = { BottomSheetDefaults.DragHandle() },
  ) {
    UiSchemaScreen(
      organization = organization,
      mgoResource = mgoResource,
      isSummary = isSummary,
      onNavigateToDetail = { _, _ -> },
      onNavigateBack = {},
    )
  }
}
