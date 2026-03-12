package nl.rijksoverheid.mgo.feature.onboarding.introduction

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import nl.rijksoverheid.mgo.component.mgo.MgoAutoScrollColumn
import nl.rijksoverheid.mgo.component.mgo.MgoBottomButton
import nl.rijksoverheid.mgo.component.mgo.MgoBottomButtons
import nl.rijksoverheid.mgo.component.mgo.MgoLargeTopAppBar
import nl.rijksoverheid.mgo.component.mgo.getMgoAppBarScrollBehaviour
import nl.rijksoverheid.mgo.component.theme.DefaultPreviews
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

object IntroductionScreenTestTag {
  const val SCREEN = "IntroductionScreen"
}

/**
 * Composable that shows a screen that introduces the app.
 *
 * @param onNavigateToProposition Called when requested to navigate to the proposition screen.
 */
@Composable
fun IntroductionScreen(onNavigateToProposition: () -> Unit) {
  val scrollState = rememberScrollState()
  val scrollBehavior = getMgoAppBarScrollBehaviour(scrollState.canScrollForward, scrollState.canScrollBackward)
  Scaffold(
    modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection).testTag(IntroductionScreenTestTag.SCREEN),
    topBar = {
      MgoLargeTopAppBar(
        title = stringResource(CopyR.string.introduction_heading),
        scrollBehavior = scrollBehavior,
      )
    },
    contentWindowInsets = WindowInsets.statusBars,
    content = { contentPadding ->
      Column(modifier = Modifier.padding(contentPadding)) {
        MgoAutoScrollColumn(
          modifier =
            Modifier
              .weight(1f),
          scrollState = scrollState,
        ) {
          Text(
            modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp),
            text = stringResource(id = CopyR.string.introduction_subheading),
            style = MaterialTheme.typography.bodyMedium,
          )

          Spacer(modifier = Modifier.weight(1f))

          Image(
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
            contentScale = ContentScale.Crop,
            painter = painterResource(id = R.drawable.illustration_introduction_new),
            contentDescription = null,
            alignment = Alignment.BottomCenter,
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
