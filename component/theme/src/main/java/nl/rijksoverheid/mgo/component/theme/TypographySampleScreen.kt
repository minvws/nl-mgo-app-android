package nl.rijksoverheid.mgo.component.theme

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
private fun TypographySampleScreen() {
    Column(modifier = Modifier.padding(all = 24.dp)) {
        Text(text = "Heading Extra Large", style = MaterialTheme.typography.headingExtraLarge)
        Spacer(modifier = Modifier.padding(top = 24.dp))
        Text(text = "Heading Large", style = MaterialTheme.typography.headingLarge)
        Spacer(modifier = Modifier.padding(top = 24.dp))
        Text(text = "Heading Regular", style = MaterialTheme.typography.headingRegular)
        Spacer(modifier = Modifier.padding(top = 24.dp))
        Text(text = "Heading Small", style = MaterialTheme.typography.headingSmall)
        Spacer(modifier = Modifier.padding(top = 24.dp))
        Text(text = "Heading Extra Small", style = MaterialTheme.typography.headingExtraSmall)
        Spacer(modifier = Modifier.padding(top = 24.dp))
        Text(text = "Body text", style = MaterialTheme.typography.bodyText)
    }
}

@DefaultPreviews
@Composable
internal fun TypographySampleScreenPreview() {
    MgoTheme {
        TypographySampleScreen()
    }
}
