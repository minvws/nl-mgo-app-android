package nl.rijksoverheid.mgo.feature.pincode.create

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import nl.rijksoverheid.mgo.component.theme.bodySmall
import nl.rijksoverheid.mgo.component.theme.headingLarge
import nl.rijksoverheid.mgo.framework.copy.R

@Composable
fun PinCodeCreateScreen(
    onPinEntered: () -> Unit,
    onNavigateBack: () -> Unit,
) {
    PinCodeCreateScreenContent(onNavigateBack)
}

@Composable
private fun PinCodeCreateScreenContent(onNavigateBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "") },
                backgroundColor = Color.Transparent,
                elevation = 0.dp,
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = stringResource(id = R.string.common_previous),
                        )
                    }
                },
            )
        },
        content = { innerPadding ->
            Column(modifier = Modifier.padding(16.dp).padding(innerPadding)) {
                Text(
                    text = stringResource(id = R.string.pincode_create_heading),
                    style = MaterialTheme.typography.headingLarge,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    modifier = Modifier.padding(top = 16.dp),
                    text = stringResource(id = R.string.pincode_create_subheading),
                    style = MaterialTheme.typography.bodySmall,
                )
            }
        },
    )
}
