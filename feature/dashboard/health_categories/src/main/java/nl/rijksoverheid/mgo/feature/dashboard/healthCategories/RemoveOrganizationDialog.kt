package nl.rijksoverheid.mgo.feature.dashboard.healthCategories

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import nl.rijksoverheid.mgo.component.mgo.MgoAlertDialog
import nl.rijksoverheid.mgo.component.theme.ActionsGhostText
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

@Composable
fun RemoveOrganizationDialog(
  organizationName: String,
  onClickPositiveButton: () -> Unit,
  onClickNegativeButton: () -> Unit,
  onDismissRequest: () -> Unit,
) {
  MgoAlertDialog(
    heading = stringResource(CopyR.string.dialog_remove_organization_heading, organizationName),
    subHeading = stringResource(CopyR.string.dialog_remove_organization_subheading),
    positiveButtonText = stringResource(CopyR.string.organizations_remove_organization),
    positiveButtonTextColor = MaterialTheme.colorScheme.ActionsGhostText(),
    negativeButtonText = stringResource(CopyR.string.common_cancel),
    negativeButtonTextColor = MaterialTheme.colorScheme.ActionsGhostText(),
    onClickPositiveButton = onClickPositiveButton,
    onClickNegativeButton = onClickNegativeButton,
    onDismissRequest = onDismissRequest,
  )
}
