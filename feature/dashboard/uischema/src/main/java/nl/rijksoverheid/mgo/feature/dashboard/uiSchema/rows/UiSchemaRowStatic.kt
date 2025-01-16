package nl.rijksoverheid.mgo.feature.dashboard.uiSchema.rows

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import nl.rijksoverheid.mgo.component.theme.bodySmallMini
import nl.rijksoverheid.mgo.component.theme.contentTertiary
import nl.rijksoverheid.mgo.feature.dashboard.uiSchema.models.UISchemaRow

@Composable
internal fun UiSchemaRowStatic(
    row: UISchemaRow.Static,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        if (row.heading != null) {
            Text(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp),
                text = row.heading,
                style = MaterialTheme.typography.bodySmallMini,
                color = MaterialTheme.colorScheme.contentTertiary(),
            )
        }
        Text(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 4.dp, bottom = 16.dp),
            text = row.value,
            style = MaterialTheme.typography.bodySmall,
        )
    }
}
