package nl.rijksoverheid.mgo.component.pincode.keyboard

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import nl.rijksoverheid.mgo.component.pincode.R
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.bodyDefault
import nl.rijksoverheid.mgo.component.theme.contentPrimary
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

@Composable
internal fun KeyboardItem(
    type: KeyboardItemType,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    when (type) {
        is KeyboardItemType.Icon -> {
            Button(
                modifier = modifier,
                colors =
                    ButtonDefaults.buttonColors(
                        backgroundColor = Color.Transparent,
                        contentColor = MaterialTheme.colors.contentPrimary(),
                    ),
                elevation = ButtonDefaults.elevation(0.dp, 0.dp, 0.dp, 0.dp),
                shape = RoundedCornerShape(25.dp),
                onClick = onClick,
            ) {
                KeyboardItemIcon(icon = type.icon, talkBackString = stringResource(type.talkBack))
            }
        }

        is KeyboardItemType.Number -> {
            Button(
                modifier = modifier,
                colors =
                    ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colors.surface,
                        contentColor =
                            MaterialTheme
                                .colors.onSurface,
                    ),
                shape = RoundedCornerShape(25.dp),
                onClick = onClick,
            ) {
                KeyboardItemNumber(number = type.number)
            }
        }
    }
}

@Composable
private fun KeyboardItemNumber(
    number: Int,
    modifier: Modifier = Modifier,
) {
    Text(
        modifier = modifier,
        text = number.toString(),
        style = MaterialTheme.typography.bodyDefault,
    )
}

@Composable
private fun KeyboardItemIcon(
    @DrawableRes icon: Int,
    talkBackString: String,
    modifier: Modifier = Modifier,
) {
    Icon(
        modifier = modifier.semantics { contentDescription = talkBackString },
        painter = painterResource(icon),
        contentDescription = null,
    )
}

@PreviewLightDark
@Composable
internal fun KeyboardItemNumberPreview() {
    MgoTheme {
        KeyboardItem(
            modifier =
                Modifier
                    .size(100.dp)
                    .padding(16.dp),
            type = KeyboardItemType.Number(1),
            onClick = {},
        )
    }
}

@PreviewLightDark
@Composable
internal fun KeyboardItemIconPreview() {
    MgoTheme {
        KeyboardItem(
            modifier =
                Modifier
                    .size(100.dp)
                    .padding(16.dp),
            type = KeyboardItemType.Icon(icon = R.drawable.ic_keyboard_backspace, talkBack = CopyR.string.common_ok),
            onClick = {},
        )
    }
}
