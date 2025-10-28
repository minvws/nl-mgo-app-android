package nl.rijksoverheid.mgo.feature.dashboard.uiSchema.rows

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import nl.rijksoverheid.mgo.component.theme.CategoriesRijkslint
import nl.rijksoverheid.mgo.component.theme.LabelsSecondary
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.uiSchema.UISchemaRow
import nl.rijksoverheid.mgo.component.uiSchema.UISchemaRowStaticValue
import nl.rijksoverheid.mgo.data.pft.Pft
import nl.rijksoverheid.mgo.feature.dashboard.uiSchema.R

@Composable
internal fun UiSchemaRowStatic(
  row: UISchemaRow.Static,
  modifier: Modifier = Modifier,
) {
  Column(modifier = modifier.fillMaxWidth()) {
    val heading = row.heading
    if (heading != null) {
      Text(
        modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp),
        text = heading,
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.LabelsSecondary(),
      )
    }
    row.value.forEach { value ->

      // Get if we have a Patient Friendly Term available for this text
      val pft =
        if (LocalInspectionMode.current) {
          // If we are looking at the compose preview, do not use view model
          if (value.snomedCode == null) {
            null
          } else {
            Pft(name = null, synonym = null, description = "")
          }
        } else {
          val viewModel =
            hiltViewModel<UISchemaRowStaticViewModel, UISchemaRowStaticViewModel.Factory>(
              creationCallback = { factory -> factory.create(snomedCode = value.snomedCode) },
              key = value.snomedCode,
            )
          val pft by viewModel.pft.collectAsStateWithLifecycle()
          pft
        }

      // Show the text. If we have a Patient Friendly Term, we do some adjustments to the UI so it looks clickable.
      Row(modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 4.dp, bottom = 16.dp), verticalAlignment = Alignment.CenterVertically) {
        SelectionContainer(modifier = Modifier.weight(1f)) {
          Text(
            text = value.value,
            color = if (pft == null) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.CategoriesRijkslint(),
            style =
              if (pft ==
                null
              ) {
                MaterialTheme.typography.bodyMedium
              } else {
                MaterialTheme.typography.bodyMedium.copy(textDecoration = TextDecoration.Underline)
              },
          )
        }
        if (pft != null) {
          Icon(
            modifier = Modifier.padding(start = 4.dp),
            painter = painterResource(R.drawable.ic_help),
            tint = MaterialTheme.colorScheme.CategoriesRijkslint(),
            contentDescription = null,
          )
        }
      }
    }
  }
}

@PreviewLightDark
@Composable
internal fun UiSchemaRowStaticSingleValuePreview() {
  MgoTheme {
    UiSchemaRowStatic(
      row = UISchemaRow.Static(heading = "Heading", value = listOf(UISchemaRowStaticValue("Value"))),
    )
  }
}

@PreviewLightDark
@Composable
internal fun UiSchemaRowStaticMultipleValuePreview() {
  MgoTheme {
    UiSchemaRowStatic(
      row =
        UISchemaRow.Static(
          heading = "Heading",
          value =
            listOf(UISchemaRowStaticValue("Value 1"), UISchemaRowStaticValue("Value 2", snomedCode = "123")),
        ),
    )
  }
}
