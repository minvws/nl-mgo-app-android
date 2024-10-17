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
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import nl.rijksoverheid.mgo.component.pincode.R
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.bodyDefault
import nl.rijksoverheid.mgo.component.theme.contentPrimary

@Composable
internal fun KeyboardItemNumber(
    number: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
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
        Text(
            text = number.toString(),
            style = MaterialTheme.typography.bodyDefault,
        )
    }
}

@Composable
internal fun KeyboardItemIcon(
    @DrawableRes icon: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
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
        Icon(
            painter = painterResource(icon),
            contentDescription = null,
        )
    }
}

@PreviewLightDark
@Composable
internal fun KeyboardItemNumberPreview() {
    MgoTheme {
        KeyboardItemNumber(
            modifier =
                Modifier
                    .size(100.dp)
                    .padding(16.dp),
            number = 1,
            onClick = {},
        )
    }
}

@PreviewLightDark
@Composable
internal fun KeyboardItemIconPreview() {
    MgoTheme {
        KeyboardItemIcon(
            modifier =
                Modifier
                    .size(100.dp)
                    .padding(16.dp),
            onClick = {},
            icon = R.drawable.ic_keyboard_backspace,
        )
    }
}
