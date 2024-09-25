package nl.rijksoverheid.mgo.component.banner

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.actionTertiaryDefault
import nl.rijksoverheid.mgo.component.theme.bodySmall
import nl.rijksoverheid.mgo.component.theme.iconsSecondary
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

@Composable
fun HealthDataErrorBanner(
    type: MgoBannerType,
    heading: String,
    subHeading: String,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    buttonText: String? = null,
    buttonCallback: (() -> Unit)? = null,
) {
    Card(modifier = modifier) {
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
                Text(text = heading, style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold)
                Text(
                    modifier = Modifier.padding(top = 4.dp),
                    text = subHeading,
                    style = MaterialTheme.typography.bodySmall,
                )
                if (buttonText != null) {
                    Text(
                        modifier = Modifier.padding(top = 4.dp).clickable { buttonCallback?.invoke() },
                        text = buttonText,
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colors.actionTertiaryDefault(),
                    )
                }
            }
            Icon(
                modifier = Modifier.clickable { onDismiss() },
                painter = painterResource(id = R.drawable.ic_banner_close),
                tint = MaterialTheme.colors.iconsSecondary(),
                contentDescription = stringResource(id = CopyR.string.common_close),
            )
        }
    }
}

@PreviewLightDark
@Composable
internal fun InfoBannerPreview() {
    MgoTheme {
        HealthDataErrorBanner(
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
        HealthDataErrorBanner(
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
        HealthDataErrorBanner(
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
        HealthDataErrorBanner(
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
        HealthDataErrorBanner(
            modifier = Modifier.fillMaxWidth(),
            heading = "This is a heading",
            subHeading = "This is a subheading",
            type = MgoBannerType.ERROR,
            onDismiss = {},
        )
    }
}
