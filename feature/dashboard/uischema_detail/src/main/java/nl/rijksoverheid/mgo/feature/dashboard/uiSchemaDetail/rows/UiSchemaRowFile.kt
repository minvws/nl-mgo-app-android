package nl.rijksoverheid.mgo.feature.dashboard.uiSchemaDetail.rows

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import nl.rijksoverheid.mgo.component.theme.actionTertiaryDefaultText
import nl.rijksoverheid.mgo.feature.dashboard.uiSchemaDetail.R
import nl.rijksoverheid.mgo.feature.dashboard.uiSchemaDetail.models.UISchemaRow

@Composable
internal fun UiSchemaRowFile(
    row: UISchemaRow.File,
    onClick: (row: UISchemaRow.File.NotDownloaded) -> Unit,
    modifier: Modifier = Modifier,
) {
    when (row) {
        is UISchemaRow.File.NotDownloaded.Idle -> {
            UiSchemaRowFileIdle(row = row, modifier = modifier)
        }
        is UISchemaRow.File.NotDownloaded.Loading -> {
        }
        is UISchemaRow.File.NotDownloaded.Error -> {
        }
        is UISchemaRow.File.Downloaded -> {
        }
    }
}

@Composable
private fun UiSchemaRowFileIdle(
    row: UISchemaRow.File.NotDownloaded.Idle,
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
            color = MaterialTheme.colorScheme.actionTertiaryDefaultText(),
            text = row.value,
            style = MaterialTheme.typography.bodySmall,
        )
        Icon(
            painter = painterResource(R.drawable.ic_attachment),
            tint = MaterialTheme.colorScheme.actionTertiaryDefaultText(),
            contentDescription = null,
        )
    }
}
