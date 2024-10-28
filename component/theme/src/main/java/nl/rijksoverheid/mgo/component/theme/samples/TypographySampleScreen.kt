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
import nl.rijksoverheid.mgo.component.theme.bodyDefault
import nl.rijksoverheid.mgo.component.theme.bodySmallMini
import nl.rijksoverheid.mgo.component.theme.headingExtraLarge
import nl.rijksoverheid.mgo.component.theme.headingExtraSmall
import nl.rijksoverheid.mgo.component.theme.headingLarge
import nl.rijksoverheid.mgo.component.theme.headingRegular
import nl.rijksoverheid.mgo.component.theme.headingSmall

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
        Text(text = "Body Default", style = MaterialTheme.typography.bodyDefault)
        Spacer(modifier = Modifier.padding(top = 24.dp))
        Text(text = "Body Small", style = MaterialTheme.typography.bodySmall)
        Spacer(modifier = Modifier.padding(top = 24.dp))
        Text(text = "Body Small Mini", style = MaterialTheme.typography.bodySmallMini)
    }
}

@DefaultPreviews
@Composable
internal fun TypographySampleScreenPreview() {
    MgoTheme {
        TypographySampleScreen()
    }
}
