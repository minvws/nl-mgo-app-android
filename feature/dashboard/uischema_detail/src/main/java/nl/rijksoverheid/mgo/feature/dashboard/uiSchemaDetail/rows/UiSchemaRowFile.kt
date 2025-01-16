package nl.rijksoverheid.mgo.feature.dashboard.uiSchemaDetail.rows

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import nl.rijksoverheid.mgo.component.theme.actionTertiaryDefaultText
import nl.rijksoverheid.mgo.component.theme.backgroundTertiary
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
            UiSchemaRowFile(row = row, loading = false, modifier = modifier.clickable { onClick(row) })
        }

        is UISchemaRow.File.NotDownloaded.Loading -> {
            UiSchemaRowFile(row = row, loading = true, modifier = modifier.clickable { onClick(row) })
        }

        is UISchemaRow.File.NotDownloaded.Error -> {
        }

        is UISchemaRow.File.Downloaded -> {
        }
    }
}

@Composable
private fun UiSchemaRowFile(
    row: UISchemaRow,
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
            color = MaterialTheme.colorScheme.actionTertiaryDefaultText(),
            text = row.value,
            style = MaterialTheme.typography.bodySmall,
        )

        if (loading) {
            CircularProgressIndicator(
                modifier = Modifier.size(24.dp),
                strokeWidth = 3.dp,
                trackColor = MaterialTheme.colorScheme.actionTertiaryDefaultText(),
                color = MaterialTheme.colorScheme.backgroundTertiary(),
            )
        } else {
            Icon(
                painter = painterResource(R.drawable.ic_attachment),
                tint = MaterialTheme.colorScheme.actionTertiaryDefaultText(),
                contentDescription = null,
            )
        }
    }
}
