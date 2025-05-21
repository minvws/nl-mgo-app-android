package nl.rijksoverheid.mgo.component.mgo.banner

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import nl.rijksoverheid.mgo.component.mgo.MgoCard
import nl.rijksoverheid.mgo.component.mgo.R
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.interactiveTertiaryDefaultText
import nl.rijksoverheid.mgo.component.theme.symbolsSecondary
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

/**
 * Composable that shows a banner.
 * @param type The type of banner you want to display.
 * @param heading The heading of the banner.
 * @param subHeading The subheading of the banner.
 * @param onDismiss Called when the close button is clicked.
 * @param modifier the [Modifier] to be applied.
 * @param buttonText Show a button with this text if not null.
 * @param onButtonClick Called when button is clicked.
 */
@Composable
fun MgoBanner(
  type: MgoBannerType,
  heading: String,
  subHeading: String,
  onDismiss: () -> Unit,
  modifier: Modifier = Modifier,
  buttonText: String? = null,
  onButtonClick: (() -> Unit)? = null,
) {
  MgoCard(modifier = modifier) {
    Row(modifier = Modifier.padding(12.dp)) {
      Icon(
        painterResource(id = type.getIcon()),
        tint = type.getIconColor(),
        contentDescription = null,
      )
      Column(
        modifier =
          Modifier
            .weight(1f)
            .padding(horizontal = 8.dp),
      ) {
        Text(
          text = heading,
          style = MaterialTheme.typography.bodyMedium,
          fontWeight = FontWeight.Bold,
        )
        Text(
          modifier = Modifier.padding(top = 4.dp),
          text = subHeading,
          style = MaterialTheme.typography.bodyMedium,
        )
        if (buttonText != null) {
          Text(
            modifier =
              Modifier.padding(top = 4.dp)
                .clickable { onButtonClick?.invoke() },
            text = buttonText,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.interactiveTertiaryDefaultText(),
          )
        }
      }
      Icon(
        modifier = Modifier.clickable { onDismiss() },
        painter = painterResource(id = R.drawable.ic_banner_close),
        tint = MaterialTheme.colorScheme.symbolsSecondary(),
        contentDescription = stringResource(id = CopyR.string.common_close),
      )
    }
  }
}

@PreviewLightDark
@Composable
internal fun InfoBannerPreview() {
  MgoTheme {
    MgoBanner(
      modifier = Modifier.fillMaxWidth(),
      heading = "This is a heading",
      subHeading = "This is a subheading",
      type = MgoBannerType.INFO,
      onDismiss = {},
    )
  }
}

@PreviewLightDark
@Composable
internal fun InfoBannerWithButtonPreview() {
  MgoTheme {
    MgoBanner(
      modifier = Modifier.fillMaxWidth(),
      heading = "This is a heading",
      subHeading = "This is a subheading",
      buttonText = "Button",
      type = MgoBannerType.INFO,
      onDismiss = {},
    )
  }
}

@PreviewLightDark
@Composable
internal fun SuccessBannerPreview() {
  MgoTheme {
    MgoBanner(
      modifier = Modifier.fillMaxWidth(),
      heading = "This is a heading",
      subHeading = "This is a subheading",
      type = MgoBannerType.SUCCESS,
      onDismiss = {},
    )
  }
}

@PreviewLightDark
@Composable
internal fun WarningBannerPreview() {
  MgoTheme {
    MgoBanner(
      modifier = Modifier.fillMaxWidth(),
      heading = "This is a heading",
      subHeading = "This is a subheading",
      type = MgoBannerType.WARNING,
      onDismiss = {},
    )
  }
}

@PreviewLightDark
@Composable
internal fun ErrorBannerPreview() {
  MgoTheme {
    MgoBanner(
      modifier = Modifier.fillMaxWidth(),
      heading = "This is a heading",
      subHeading = "This is a subheading",
      type = MgoBannerType.ERROR,
      onDismiss = {},
    )
  }
}
