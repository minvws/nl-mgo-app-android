package nl.rijksoverheid.mgo.feature.dashboard.uiSchemaDetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import nl.rijksoverheid.mgo.component.theme.DefaultPreviews
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.bodySmallMini
import nl.rijksoverheid.mgo.component.theme.composable.MgoCard
import nl.rijksoverheid.mgo.component.theme.composable.MgoScaffold
import nl.rijksoverheid.mgo.component.theme.contentTertiary
import nl.rijksoverheid.mgo.component.theme.strokesPrimary
import nl.rijksoverheid.mgo.data.uiSchema.ChildDisplay
import nl.rijksoverheid.mgo.data.uiSchema.DisplayElement
import nl.rijksoverheid.mgo.data.uiSchema.TEST_UI_SCHEMA_MEDICATION
import nl.rijksoverheid.mgo.data.uiSchema.UISchema
import nl.rijksoverheid.mgo.data.uiSchema.UISchemaGroup
import nl.rijksoverheid.mgo.data.uiSchema.Value
import nl.rijksoverheid.mgo.framework.copy.R

@Composable
fun UiSchemaDetailScreen(
    toolbarTitle: String,
    uiSchema: UISchema,
    onNavigateBack: () -> Unit,
) {
    MgoScaffold(
        appBarTitle = toolbarTitle,
        onNavigateBack = onNavigateBack,
        content = {
            LazyColumn(contentPadding = PaddingValues(top = 8.dp)) {
                items(uiSchema.children.size) { position ->
                    val uiSchemaGroup = uiSchema.children[position]
                    UiSchemaSection(group = uiSchemaGroup, modifier = Modifier.padding(bottom = 24.dp))
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
        MgoCard(modifier = Modifier.padding(top = 8.dp)) {
            Column {
                group.children.forEachIndexed { index, childElement ->
                    UiSchemaLabelWithValue(value = childElement, hasDivider = index != group.children.lastIndex)
                }
            }
        }
    }
}

@Composable
private fun UiSchemaLabelWithValue(
    value: Value,
    hasDivider: Boolean,
) {
    Column {
        Text(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp),
            text = value.label.getStringFromResourceWithFallback(),
            style = MaterialTheme.typography.bodySmallMini,
            color = MaterialTheme.colorScheme.contentTertiary(),
        )
        Text(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 4.dp, bottom = 16.dp),
            text = value.display.getStringOrUnknown(),
            style = MaterialTheme.typography.bodySmall,
        )
        if (hasDivider) {
            Divider(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(0.33.dp)
                        .padding(start = 16.dp),
                color = MaterialTheme.colorScheme.strokesPrimary(),
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
