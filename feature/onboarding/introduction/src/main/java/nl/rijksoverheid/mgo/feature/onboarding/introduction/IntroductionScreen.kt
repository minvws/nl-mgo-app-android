package nl.rijksoverheid.mgo.feature.onboarding.introduction

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import nl.rijksoverheid.mgo.component.mgo.MgoScaffold
import nl.rijksoverheid.mgo.component.mgo.MgoScaffoldScrollStateProvider
import nl.rijksoverheid.mgo.component.theme.DefaultPreviews
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

/**
 * Composable that shows a screen that introduces the app.
 *
 * @param onNavigateToProposition Called when requested to navigate to the proposition screen.
 */
@Composable
fun IntroductionScreen(onNavigateToProposition: () -> Unit) {
    MgoScaffold(
        scrollStateProvider =
            MgoScaffoldScrollStateProvider.Column(
                rememberScrollState(),
            ),
        primaryButtonText = stringResource(id = CopyR.string.common_next),
        onPrimaryButtonClick = onNavigateToProposition,
    ) {
        Image(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(top = TopAppBarDefaults.MediumAppBarCollapsedHeight)
                    .align(Alignment.CenterHorizontally),
            painter = painterResource(id = R.drawable.illustration_introduction),
            contentDescription = null,
        )

        Text(
            modifier = Modifier.padding(top = 32.dp),
            text = stringResource(id = CopyR.string.introduction_heading),
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
        )

        Text(
            modifier = Modifier.padding(top = 16.dp),
            text = stringResource(id = CopyR.string.introduction_subheading),
            style = MaterialTheme.typography.bodyMedium,
        )
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@DefaultPreviews
@Composable
internal fun IntroductionScreenPreview() {
    MgoTheme {
        IntroductionScreen(
            onNavigateToProposition = {},
        )
    }
}
