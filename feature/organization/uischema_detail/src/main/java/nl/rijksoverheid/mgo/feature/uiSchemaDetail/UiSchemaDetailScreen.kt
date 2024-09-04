package nl.rijksoverheid.mgo.feature.uiSchemaDetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import nl.rijksoverheid.mgo.component.theme.bodySmall
import nl.rijksoverheid.mgo.component.theme.bodySmallMini
import nl.rijksoverheid.mgo.component.theme.contentTertiary
import nl.rijksoverheid.mgo.component.theme.strokesPrimary
import nl.rijksoverheid.mgo.data.uiSchema.ChildElement
import nl.rijksoverheid.mgo.data.uiSchema.UISchema
import nl.rijksoverheid.mgo.data.uiSchema.UISchemaGroup
import nl.rijksoverheid.mgo.framework.copy.R

@Composable
fun UiSchemaDetailScreen(
    toolbarTitle: String,
    uiSchema: UISchema,
    onNavigateBack: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = toolbarTitle, style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold) },
                backgroundColor = Color.Transparent,
                elevation = 0.dp,
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.common_previous),
                        )
                    }
                },
            )
        },
        content = { innerPadding ->
            LazyColumn(
                modifier = Modifier.padding(innerPadding),
                contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 16.dp),
            ) {
                items(uiSchema.children.size) { position ->
                    val uiSchemaGroup = uiSchema.children[position]
                    UiSchemaSection(group = uiSchemaGroup, modifier = Modifier.padding(top = 24.dp))
                }
            }
        },
    )
}

@Composable
private fun UiSchemaSection(
    group: UISchemaGroup,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Text(text = group.label, style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold)
        Card(modifier = Modifier.padding(top = 8.dp)) {
            Column {
                group.children.forEachIndexed { index, childElement ->
                    UiSchemaLabelWithValue(childElement = childElement, hasDivider = index != group.children.lastIndex)
                }
            }
        }
    }
}

@Composable
private fun UiSchemaLabelWithValue(
    childElement: ChildElement,
    hasDivider: Boolean,
) {
    Column {
        Text(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp),
            text = childElement.label,
            style = MaterialTheme.typography.bodySmallMini,
            color = MaterialTheme.colors.contentTertiary(),
        )
        Text(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 4.dp, bottom = 16.dp),
            text = childElement.display.toString(),
            style = MaterialTheme.typography.bodySmall,
        )
        if (hasDivider) {
            Divider(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(0.33.dp)
                        .padding(start = 16.dp),
                color = MaterialTheme.colors.strokesPrimary(),
            )
        }
    }
}
