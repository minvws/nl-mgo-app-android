package nl.rijksoverheid.mgo.feature.healthcareprovider.medication

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import nl.rijksoverheid.mgo.component.theme.DefaultPreviews
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.bodySmall
import nl.rijksoverheid.mgo.component.theme.bodySmallMini
import nl.rijksoverheid.mgo.component.theme.contentTertiary
import nl.rijksoverheid.mgo.component.theme.headingExtraSmall
import nl.rijksoverheid.mgo.component.theme.headingLarge
import nl.rijksoverheid.mgo.component.theme.headingSmall
import nl.rijksoverheid.mgo.data.medication.models.MgoMedication
import nl.rijksoverheid.mgo.data.medication.models.TEST_MGO_MEDICATION
import nl.rijksoverheid.mgo.component.theme.R as ThemeR
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

@Composable
fun MedicationScreen(onNavigateBack: () -> Unit) {
    val viewModel: MedicationScreenViewModel = hiltViewModel()
    val viewState by viewModel.viewState.collectAsState()
    MedicationScreenContent(
        viewState = viewState,
        onNavigateBack = onNavigateBack,
    )
}

@Composable
private fun MedicationScreenContent(
    viewState: MedicationScreenViewState,
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
                            contentDescription = stringResource(id = CopyR.string.general_previous),
                        )
                    }
                },
            )
        },
        content = { innerPadding ->
            Column(
                modifier =
                    Modifier
                        .padding(horizontal = 16.dp)
                        .padding(innerPadding)
                        .then(
                            if (viewState !is MedicationScreenViewState.Loading) {
                                Modifier.verticalScroll(rememberScrollState())
                            } else {
                                Modifier
                            },
                        ),
            ) {
                Text(
                    text = stringResource(id = CopyR.string.medication_title),
                    style = MaterialTheme.typography.headingLarge,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    modifier = Modifier.padding(top = 16.dp),
                    text = stringResource(id = CopyR.string.medication_subtitle),
                    style = MaterialTheme.typography.bodySmall,
                )

                when (viewState) {
                    MedicationScreenViewState.Loading ->
                        MedicationScreenLoadingContent(
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .weight(1f)
                                    .padding(vertical = 16.dp),
                        )
                    is MedicationScreenViewState.Success -> MedicationScreenSuccessContent(medications = viewState.medications)
                    is MedicationScreenViewState.Error ->
                        MedicationScreenErrorContent(
                            modifier = Modifier.padding(vertical = 16.dp),
                            isProductionBuild = viewState.isProductionBuild,
                            error = viewState.error,
                        )
                }
            }
        },
    )
}

@Composable
private fun MedicationScreenLoadingContent(modifier: Modifier = Modifier) {
    Card(modifier = modifier) {
        Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator(
                modifier =
                    Modifier
                        .size(48.dp),
                strokeWidth = 6.dp,
            )
            Text(
                modifier = Modifier.padding(top = 20.dp),
                text = stringResource(id = CopyR.string.general_loading),
                style = MaterialTheme.typography.bodySmall,
            )
        }
    }
}

@Composable
private fun MedicationScreenSuccessContent(medications: List<MgoMedication>) {
    medications.forEach { medication ->
        CollapsableCard(
            medication = medication,
            modifier = Modifier.padding(top = 24.dp),
        )
    }
}

@Composable
private fun MedicationScreenErrorContent(
    isProductionBuild: Boolean,
    error: Throwable,
    modifier: Modifier = Modifier,
) {
    Card(modifier = modifier) {
        Column(modifier = Modifier.padding(all = 16.dp)) {
            Image(
                modifier =
                    Modifier
                        .fillMaxWidth(),
                painter = painterResource(id = ThemeR.drawable.illustration_alert),
                contentDescription = null,
            )

            Text(
                modifier = Modifier.padding(top = 24.dp),
                text = stringResource(id = CopyR.string.healthcareprovider_card_error_title),
                style = MaterialTheme.typography.headingSmall,
            )

            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = stringResource(id = CopyR.string.healthcareprovider_card_error_subtitle),
                style = MaterialTheme.typography.bodySmall,
            )
            if (!isProductionBuild) {
                Text(
                    modifier = Modifier.padding(top = 8.dp),
                    text = error.toString(),
                    style = MaterialTheme.typography.bodySmallMini,
                    color = MaterialTheme.colors.error,
                )
            }
        }
    }
}

@Composable
private fun CollapsableCard(
    medication: MgoMedication,
    modifier: Modifier = Modifier,
) {
    Card(modifier = modifier) {
        Column(modifier = Modifier.padding(all = 16.dp)) {
            Text(text = medication.title, style = MaterialTheme.typography.headingExtraSmall)
            Text(
                modifier = Modifier.padding(top = 16.dp),
                text = stringResource(id = CopyR.string.medication_card_instructions_header).uppercase(),
                color = MaterialTheme.colors.contentTertiary(),
                style = MaterialTheme.typography.bodySmallMini,
            )
            Text(
                modifier = Modifier.padding(top = 4.dp),
                text = medication.instructions,
                style = MaterialTheme.typography.bodySmall,
            )
            Text(
                modifier = Modifier.padding(top = 16.dp),
                text = stringResource(id = CopyR.string.medication_card_start_date_header).uppercase(),
                color = MaterialTheme.colors.contentTertiary(),
                style = MaterialTheme.typography.bodySmallMini,
            )
            Text(
                modifier = Modifier.padding(top = 4.dp),
                text = medication.startDate,
                style = MaterialTheme.typography.bodySmall,
            )
            Text(
                modifier = Modifier.padding(top = 16.dp),
                text = stringResource(id = CopyR.string.medication_card_prescribed_by_header).uppercase(),
                color = MaterialTheme.colors.contentTertiary(),
                style = MaterialTheme.typography.bodySmallMini,
            )
            Text(
                modifier = Modifier.padding(top = 4.dp),
                text = medication.prescribedBy,
                style = MaterialTheme.typography.bodySmall,
            )
            Text(
                modifier = Modifier.padding(top = 16.dp),
                text = stringResource(id = CopyR.string.medication_card_status_header).uppercase(),
                color = MaterialTheme.colors.contentTertiary(),
                style = MaterialTheme.typography.bodySmallMini,
            )
            Text(
                modifier = Modifier.padding(top = 4.dp),
                text = medication.status,
                style = MaterialTheme.typography.bodySmall,
            )
        }
    }
}

@DefaultPreviews
@Composable
internal fun MedicationScreenLoadingPreview() {
    MgoTheme {
        MedicationScreenContent(
            viewState = MedicationScreenViewState.Loading,
            onNavigateBack = {},
        )
    }
}

@DefaultPreviews
@Composable
internal fun MedicationScreenMedicationsPreview() {
    MgoTheme {
        MedicationScreenContent(
            viewState = MedicationScreenViewState.Success(listOf(TEST_MGO_MEDICATION, TEST_MGO_MEDICATION)),
            onNavigateBack = {},
        )
    }
}

@DefaultPreviews
@Composable
internal fun MedicationScreenErrorPreview() {
    MgoTheme {
        MedicationScreenContent(
            viewState = MedicationScreenViewState.Error(isProductionBuild = true, error = IllegalStateException("Something went wrong")),
            onNavigateBack = {},
        )
    }
}
