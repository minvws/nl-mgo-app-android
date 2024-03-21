package nl.rijksoverheid.mgo.feature.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import nl.rijksoverheid.mgo.component.theme.DefaultPreviews
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.framework.navigation.LocalNavigationManager
import nl.rijksoverheid.mgo.framework.navigation.NavigationScreen
import kotlinx.coroutines.flow.collectLatest
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

@Composable
fun SplashScreen(viewModel: SplashScreenViewModel = hiltViewModel()) {
    val navigationManager = LocalNavigationManager.current
    LaunchedEffect(Unit) {
        viewModel.navigation.collectLatest {
            navigationManager.navigate(NavigationScreen.Onboarding.Start)
        }
    }
    SplashScreenContent()
}

@Composable
private fun SplashScreenContent() {
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Image(painter = painterResource(id = R.drawable.logo_vws), contentDescription = null)
        Text(
            modifier = Modifier.padding(top = 64.dp, start = 16.dp, end = 16.dp),
            text = stringResource(id = CopyR.string.splash_title),
            style = MaterialTheme.typography.h4,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = Modifier.weight(1f))
        Column(modifier = Modifier.padding(bottom = 64.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator(
                modifier = Modifier.width(24.dp),
                color = MaterialTheme.colors.primary,
                strokeWidth = 4.dp,
            )
            Text(modifier = Modifier.padding(top = 12.dp), text = stringResource(id = CopyR.string.splash_loading))
        }
    }
}

@DefaultPreviews
@Composable
internal fun SplashScreenPreview() {
    MgoTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background,
        ) {
            SplashScreenContent()
        }
    }
}
