package nl.rijksoverheid.mgo.feature.pincode.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun PinCodeLoginScreen(onPinEntered: () -> Unit) {
    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .background(Color.Red),
    ) {
        Text("Pin code login placeholder")
        Button(onPinEntered) {
            Text("Skip")
        }
    }
}
