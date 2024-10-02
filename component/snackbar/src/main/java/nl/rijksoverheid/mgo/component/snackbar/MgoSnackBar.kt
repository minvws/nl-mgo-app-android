package nl.rijksoverheid.mgo.component.snackbar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.bodySmall
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

@Composable
internal fun MgoSnackBar(
    visuals: MgoSnackBarVisuals,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.padding(16.dp),
    ) {
        Row(
            modifier =
                Modifier
                    .background(visuals.type.getBackgroundColor())
                    .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                painter = painterResource(visuals.type.getIcon()),
                contentDescription = null,
                tint = visuals.type.getContentColor(),
            )
            Text(
                modifier =
                    Modifier
                        .weight(1f)
                        .padding(start = 16.dp),
                text = stringResource(visuals.title),
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                color = visuals.type.getContentColor(),
            )
            if (visuals.action != null) {
                Text(
                    modifier =
                        Modifier
                            .clickable(visuals.actionCallback != null) { visuals.actionCallback?.invoke() }
                            .padding(start = 16.dp),
                    text = stringResource(visuals.action),
                    style = MaterialTheme.typography.bodySmall,
                    textDecoration = TextDecoration.Underline,
                    color = visuals.type.getContentColor(),
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
internal fun MgoSnackBarSuccess() {
    MgoTheme {
        MgoSnackBar(visuals = MgoSnackBarVisuals(type = MgoSnackBarType.SUCCESS, title = CopyR.string.app_name))
    }
}

@PreviewLightDark
@Composable
internal fun MgoSnackBarSuccessWithAction() {
    MgoTheme {
        MgoSnackBar(
            visuals =
                MgoSnackBarVisuals(
                    type = MgoSnackBarType.SUCCESS,
                    title = CopyR.string.app_name,
                    action = CopyR.string.app_name,
                ),
        )
    }
}

@PreviewLightDark
@Composable
internal fun MgoSnackBarSuccessOverflow() {
    MgoTheme {
        MgoSnackBar(
            visuals =
                MgoSnackBarVisuals(
                    type = MgoSnackBarType.SUCCESS,
                    title = CopyR.string.dialog_remove_organization_subheading,
                    action = CopyR.string.app_name,
                ),
        )
    }
}

@PreviewLightDark
@Composable
internal fun MgoSnackBarWarning() {
    MgoTheme {
        MgoSnackBar(visuals = MgoSnackBarVisuals(type = MgoSnackBarType.WARNING, title = CopyR.string.app_name))
    }
}

@PreviewLightDark
@Composable
internal fun MgoSnackBarWarningWithAction() {
    MgoTheme {
        MgoSnackBar(
            visuals =
                MgoSnackBarVisuals(
                    type = MgoSnackBarType.WARNING,
                    title = CopyR.string.app_name,
                    action = CopyR.string.app_name,
                ),
        )
    }
}

@PreviewLightDark
@Composable
internal fun MgoSnackBarError() {
    MgoTheme {
        MgoSnackBar(visuals = MgoSnackBarVisuals(type = MgoSnackBarType.ERROR, title = CopyR.string.app_name))
    }
}

@PreviewLightDark
@Composable
internal fun MgoSnackBarInfo() {
    MgoTheme {
        MgoSnackBar(visuals = MgoSnackBarVisuals(type = MgoSnackBarType.INFO, title = CopyR.string.app_name))
    }
}
