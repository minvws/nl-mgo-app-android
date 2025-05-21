package nl.rijksoverheid.mgo.component.theme.samples

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import nl.rijksoverheid.mgo.component.theme.DefaultPreviews
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.headlineExtraSmall

@Composable
private fun TypographySampleScreen() {
  Column(modifier = Modifier.padding(all = 24.dp)) {
    Text(text = "Headline Large", style = MaterialTheme.typography.headlineLarge)
    Spacer(modifier = Modifier.padding(top = 24.dp))
    Text(text = "Headline Medium", style = MaterialTheme.typography.headlineMedium)
    Spacer(modifier = Modifier.padding(top = 24.dp))
    Text(text = "Heading Small", style = MaterialTheme.typography.headlineSmall)
    Spacer(modifier = Modifier.padding(top = 24.dp))
    Text(text = "Heading Extra Small", style = MaterialTheme.typography.headlineExtraSmall)
    Spacer(modifier = Modifier.padding(top = 24.dp))
    Text(text = "Body Large", style = MaterialTheme.typography.bodyLarge)
    Spacer(modifier = Modifier.padding(top = 24.dp))
    Text(text = "Body Medium", style = MaterialTheme.typography.bodyMedium)
    Spacer(modifier = Modifier.padding(top = 24.dp))
    Text(text = "Body Small", style = MaterialTheme.typography.bodyMedium)
  }
}

@DefaultPreviews
@Composable
internal fun TypographySampleScreenPreview() {
  MgoTheme {
    TypographySampleScreen()
  }
}
