package nl.rijksoverheid.mgo.feature.dashboard.healthCategory

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import nl.rijksoverheid.mgo.component.mgo.MgoCard
import nl.rijksoverheid.mgo.component.theme.LabelsSecondary
import nl.rijksoverheid.mgo.component.theme.MgoTheme

@Composable
internal fun HealthCategoryCard(
  title: String,
  subtitle: String,
  detail: String?,
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
) {
  MgoCard(modifier = modifier.testTag(HealthCategoryScreenTestTag.CARD), onClick = onClick) {
    Row(modifier = Modifier.padding(16.dp)) {
      Column(modifier = Modifier.weight(1f)) {
        Text(
          text = title,
          style = MaterialTheme.typography.bodyMedium,
          fontWeight = FontWeight.Bold,
          maxLines = 2,
          overflow = TextOverflow.Ellipsis,
        )
        Text(
          modifier = Modifier.padding(top = 8.dp),
          text = subtitle,
          style = MaterialTheme.typography.bodyMedium,
          color = MaterialTheme.colorScheme.LabelsSecondary(),
          maxLines = 2,
          overflow = TextOverflow.Ellipsis,
        )
      }
      if (detail != null) {
        Text(
          modifier = Modifier.weight(1f).padding(start = 16.dp),
          text = detail,
          style = MaterialTheme.typography.bodySmall,
          color = MaterialTheme.colorScheme.LabelsSecondary(),
          maxLines = 1,
          overflow = TextOverflow.Ellipsis,
          textAlign = TextAlign.End,
        )
      }
    }
  }
}

@Composable
@PreviewLightDark
internal fun HealthCategoryCardPreview() {
  MgoTheme {
    val listItem = TEST_LIST_ITEM_1
    HealthCategoryCard(
      modifier = Modifier.fillMaxWidth(),
      title = listItem.title,
      subtitle = listItem.subtitle,
      detail = null,
      onClick = {},
    )
  }
}

@Composable
@PreviewLightDark
internal fun HealthCategoryCardWithDetailPreview() {
  MgoTheme {
    val listItem = TEST_LIST_ITEM_1
    HealthCategoryCard(
      modifier = Modifier.fillMaxWidth(),
      title = listItem.title,
      subtitle = listItem.subtitle,
      detail = listItem.detail,
      onClick = {},
    )
  }
}

@Composable
@PreviewLightDark
internal fun HealthCategoryCardWithDetailOverflowPreview() {
  MgoTheme {
    val overflowText = "HelloWorldHelloWorldHelloWorldHelloWorldHelloWorldHelloWorldHelloWorldHelloWorldHelloWorldHelloWorldHelloWorldHelloWorldHelloWorld"
    HealthCategoryCard(
      title = overflowText,
      subtitle = overflowText,
      detail = overflowText,
      onClick = {},
    )
  }
}
