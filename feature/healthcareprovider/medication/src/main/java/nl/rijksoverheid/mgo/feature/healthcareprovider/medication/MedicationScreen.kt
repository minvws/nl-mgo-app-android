package nl.rijksoverheid.mgo.feature.healthcareprovider.medication

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun MedicationScreen() {
    val viewModel: MedicationViewModel = hiltViewModel()
    Box(
        modifier =
            Modifier
                .fillMaxSize()
                .background(Color.Red),
    )
}
