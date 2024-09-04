package nl.rijksoverheid.mgo.feature.organization.medicationUse

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import nl.rijksoverheid.mgo.component.theme.DefaultPreviews
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.bodySmall
import nl.rijksoverheid.mgo.component.theme.contentSecondary
import nl.rijksoverheid.mgo.component.theme.headingLarge
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization
import nl.rijksoverheid.mgo.data.uiSchema.UISchema
import nl.rijksoverheid.mgo.data.uiSchema.models.TEST_UI_SCHEMA_MEDICATION
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

@Composable
fun MedicationUseScreen(
    provider: MgoOrganization,
    onClickUiSchema: (toolbarTitle: String, uiSchema: UISchema) -> Unit,
    onNavigateBack: () -> Unit,
) {
    val viewModel =
        hiltViewModel<MedicationUseScreenViewModel, MedicationUseScreenViewModel.Factory>(
            creationCallback = { factory -> factory.create(provider) },
        )
    val viewState by viewModel.viewState.collectAsState()

    val medicationDetailsToolbarTitle = stringResource(id = CopyR.string.medication_details_heading)
    MedicationUseScreenContent(
        viewState = viewState,
        onClickUiSchema = { uiSchema ->
            onClickUiSchema(medicationDetailsToolbarTitle, uiSchema)
        },
        onNavigateBack = onNavigateBack,
    )
}

@Composable
private fun MedicationUseScreenContent(
    viewState: MedicationUseScreenViewState,
    onClickUiSchema: (uiSchema: UISchema) -> Unit,
    onNavigateBack: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "") },
                backgroundColor = Color.Transparent,
                elevation = 0.dp,
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(id = CopyR.string.common_previous),
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
                item {
                    Text(
                        text = stringResource(id = CopyR.string.medication_use_heading),
                        style = MaterialTheme.typography.headingLarge,
                        fontWeight = FontWeight.Bold,
                    )
                }

                items(viewState.uiSchemaList.size) { position ->
                    val uiSchema = viewState.uiSchemaList[position]
                    MedicationUseCard(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .clickable { onClickUiSchema(uiSchema) }
                                .padding(top = 16.dp),
                        title = uiSchema.label ?: "",
                        subtitle = "Ondertitel",
                    )
                }
            }
        },
    )
}

@Composable
private fun MedicationUseCard(
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier,
) {
    Card(modifier = modifier) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = title, style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold)
            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colors.contentSecondary(),
            )
        }
    }
}

@DefaultPreviews
@Composable
internal fun MedicationUseScreenPreview() {
    MgoTheme {
        MedicationUseScreenContent(
            viewState = MedicationUseScreenViewState.initialState.copy(uiSchemaList = listOf(TEST_UI_SCHEMA_MEDICATION)),
            onClickUiSchema = {},
            onNavigateBack = {},
        )
    }
}
