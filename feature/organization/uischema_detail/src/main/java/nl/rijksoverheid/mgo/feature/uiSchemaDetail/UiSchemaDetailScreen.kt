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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import nl.rijksoverheid.mgo.component.theme.DefaultPreviews
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.bodySmall
import nl.rijksoverheid.mgo.component.theme.bodySmallMini
import nl.rijksoverheid.mgo.component.theme.contentTertiary
import nl.rijksoverheid.mgo.component.theme.strokesPrimary
import nl.rijksoverheid.mgo.data.uiSchema.ChildDisplay
import nl.rijksoverheid.mgo.data.uiSchema.ChildElement
import nl.rijksoverheid.mgo.data.uiSchema.DisplayElement
import nl.rijksoverheid.mgo.data.uiSchema.TEST_UI_SCHEMA_MEDICATION
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
        Text(
            text = group.label.getStringFromResourceWithFallback(),
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Bold,
        )
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
            text = childElement.label.getStringFromResourceWithFallback(),
            style = MaterialTheme.typography.bodySmallMini,
            color = MaterialTheme.colors.contentTertiary(),
        )
        Text(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 4.dp, bottom = 16.dp),
            text = childElement.display.getStringOrUnknown(),
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

/**
 * Expects the string to be a resources key, and tries to grab the string resource.
 * If it doesn't exist, it will try a fallback key.
 * If that doesn't exist, returns the fallback key as string.
 */
@Composable
private fun String.getStringFromResourceWithFallback(): String {
    val context = LocalContext.current
    val resId: Int = context.resources.getIdentifier(this, "string", context.packageName)
    if (resId == 0) {
        val fallbackLabel = "fhir." + this.substringAfter(".")
        val fallbackResId = context.resources.getIdentifier(fallbackLabel, "string", context.packageName)
        if (fallbackResId == 0) {
            return fallbackLabel
        }
        return stringResource(id = fallbackResId)
    }
    return stringResource(id = resId)
}

@Composable
private fun ChildDisplay?.getStringOrUnknown(): String {
    if (this == null) return stringResource(id = R.string.fhir_unknown)
    return when (this) {
        is ChildDisplay.StringValue -> this.value
        is ChildDisplay.UnionArrayValue -> this.value.joinToString(", ") { it.getString() }
    }
}

private fun DisplayElement.getString(): String {
    return when (this) {
        is DisplayElement.StringValue -> this.value
        is DisplayElement.StringArrayValue -> this.value.joinToString(", ")
    }
}

@DefaultPreviews
@Composable
internal fun UiSchemaDetailScreenPreview() {
    MgoTheme {
        UiSchemaDetailScreen(
            toolbarTitle = stringResource(id = R.string.medication_details_heading),
            uiSchema = TEST_UI_SCHEMA_MEDICATION,
            onNavigateBack = {},
        )
    }
}
