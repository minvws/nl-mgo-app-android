package nl.rijksoverheid.mgo.feature.dashboard.uiSchemaDetail.rows

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import nl.rijksoverheid.mgo.feature.dashboard.uiSchemaDetail.models.UISchemaRow

@Composable
internal fun UiSchemaRowReference(
    row: UISchemaRow.Reference,
    onClick: (reference: UISchemaRow.Reference) -> Unit,
    modifier: Modifier = Modifier,
) {
    Text(
        modifier =
            modifier
                .padding(16.dp)
                .clickable { onClick(row) },
        text = row.value,
        style = MaterialTheme.typography.bodySmall,
    )
}
