package nl.rijksoverheid.mgo.feature.localisation.manual

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import nl.rijksoverheid.mgo.component.mgo.MgoCard
import nl.rijksoverheid.mgo.component.theme.LabelsPrimary
import nl.rijksoverheid.mgo.component.theme.LabelsSecondary
import nl.rijksoverheid.mgo.component.theme.MgoTheme

@Composable
internal fun ManualLocalisationCard(
  heading: String,
  subheading: String,
  modifier: Modifier = Modifier,
  trailing: String? = null,
  disabled: Boolean = false,
  onClick: (() -> Unit)? = null,
) {
  MgoCard(modifier = modifier) {
    Row(modifier = Modifier.clickable(enabled = onClick != null) { onClick?.invoke() }.padding(16.dp)) {
      Column(modifier = Modifier.weight(1f)) {
        val headingColor = if (disabled) MaterialTheme.colorScheme.LabelsSecondary() else MaterialTheme.colorScheme.LabelsPrimary()
        Text(text = heading, style = MaterialTheme.typography.bodyMedium, color = headingColor, fontWeight = FontWeight.Bold)
        Text(
          modifier = Modifier.padding(top = 4.dp),
          text = subheading,
          style = MaterialTheme.typography.bodyMedium,
          color = MaterialTheme.colorScheme.LabelsSecondary(),
        )
      }
      trailing?.let {
        Text(
          modifier = Modifier.padding(start = 16.dp),
          text = trailing,
          style = MaterialTheme.typography.bodySmall,
          color = MaterialTheme.colorScheme.LabelsSecondary(),
        )
      }
    }
  }
}

@Composable
@PreviewLightDark
internal fun ManualLocalisationCardPreview() {
  MgoTheme {
    ManualLocalisationCard(
      heading = "Heading",
      subheading = "Subheading",
      trailing = "Trailing",
      disabled = false,
    )
  }
}

@Composable
@PreviewLightDark
internal fun ManualLocalisationCardDisabledPreview() {
  MgoTheme {
    ManualLocalisationCard(
      heading = "Heading",
      subheading = "Subheading",
      trailing = "Trailing",
      disabled = true,
    )
  }
}
