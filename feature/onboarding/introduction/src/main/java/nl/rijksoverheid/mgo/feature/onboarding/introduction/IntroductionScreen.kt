package nl.rijksoverheid.mgo.feature.onboarding.introduction

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import nl.rijksoverheid.mgo.component.mgo.MgoBottomButton
import nl.rijksoverheid.mgo.component.mgo.MgoBottomButtons
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
  val scrollState = rememberScrollState()
  Scaffold(
    contentWindowInsets = WindowInsets.statusBars,
    content = { contentPadding ->
      Column(modifier = Modifier.padding(contentPadding)) {
        Column(
          modifier =
            Modifier
              .weight(1f)
              .verticalScroll(scrollState)
              .padding(16.dp),
        ) {
          Image(
            modifier =
              Modifier
                .fillMaxWidth()
                .padding(top = TopAppBarDefaults.LargeAppBarCollapsedHeight),
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
        }

        MgoBottomButtons(
          primaryButton =
            MgoBottomButton(
              text = stringResource(id = CopyR.string.common_next),
              onClick = onNavigateToProposition,
            ),
          isElevated = scrollState.canScrollForward,
        )
      }
    },
  )
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
