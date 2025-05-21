package nl.rijksoverheid.mgo.feature.dashboard.uiSchema.rows

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.interactiveTertiaryDefaultText
import nl.rijksoverheid.mgo.feature.dashboard.uiSchema.R
import nl.rijksoverheid.mgo.feature.dashboard.uiSchema.models.UISchemaRow
import nl.rijksoverheid.mgo.framework.util.launchBrowser

/**
 * Composable that shows a list item that represents a file that can be downloaded via a url.
 *
 * @param row The [UISchemaRow.Link].
 * @param modifier The [Modifier] to be applied.
 */
@Composable
internal fun UiSchemaRowLink(
  row: UISchemaRow.Link,
  modifier: Modifier = Modifier,
) {
  val context = LocalContext.current

  Row(
    modifier =
      modifier
        .fillMaxWidth()
        .padding(16.dp)
        .clickable { context.launchBrowser(row.url) },
    verticalAlignment = Alignment.CenterVertically,
  ) {
    Text(
      modifier =
        Modifier
          .weight(1f)
          .padding(end = 8.dp),
      color = MaterialTheme.colorScheme.interactiveTertiaryDefaultText(),
      text = row.value,
      style = MaterialTheme.typography.bodyMedium,
    )

    Icon(
      painter = painterResource(R.drawable.ic_attachment),
      tint = MaterialTheme.colorScheme.interactiveTertiaryDefaultText(),
      contentDescription = null,
    )
  }
}

@PreviewLightDark
@Composable
internal fun UiSchemaRowLinkPreview() {
  MgoTheme {
    UiSchemaRowLink(
      row =
        UISchemaRow.Link(
          heading = "Heading",
          value = "Value",
          url = "https://www.google.com",
        ),
    )
  }
}
