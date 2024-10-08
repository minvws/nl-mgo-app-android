package nl.rijksoverheid.mgo.feature.pincode.confirm

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun PinCodeConfirmScreen(onPinConfirmed: () -> Unit) {
    val viewModel: PinCodeConfirmScreenViewModel = hiltViewModel()
    Box(
        modifier =
            Modifier
                .fillMaxSize()
                .background(Color.Blue),
    ) {
        Button(onPinConfirmed) {
            viewModel.setHasSeenPinCode()
            Text("Click")
        }
    }
}
