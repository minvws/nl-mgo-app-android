package nl.rijksoverheid.mgo.component.pincode

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.bodyDefault

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun KeyboardItem(
    type: KeyboardItemType,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(modifier = modifier, shape = RoundedCornerShape(25.dp), onClick = onClick) {
        Box(contentAlignment = Alignment.Center) {
            when (type) {
                is KeyboardItemType.Icon -> {}
                is KeyboardItemType.Number -> KeyboardItemNumber(number = type.number)
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

@PreviewLightDark
@Composable
internal fun KeyboardItemPreview() {
    MgoTheme {
        KeyboardItem(
            modifier = Modifier.size(100.dp).padding(16.dp),
            type = KeyboardItemType.Number(1),
            onClick = {},
        )
    }
}
