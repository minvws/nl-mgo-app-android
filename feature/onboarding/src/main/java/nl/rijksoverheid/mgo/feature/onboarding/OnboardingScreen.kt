package nl.rijksoverheid.mgo.feature.onboarding

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun OnboardingScreen(modifier: Modifier = Modifier) {
    Text(
        modifier = modifier,
        text = "Hello World",
        style = MaterialTheme.typography.h1,
    )
}
