package nl.rijksoverheid.mgo.feature.dashboard.uiSchema.rows

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
  onClickPft: (pft: Pft) -> Unit,
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

      // Get if we have a Patient Friendly Term
      val pft = getPft(value.snomedCode)

      // Show the text. If we have a Patient Friendly Term, we do some adjustments to the UI so it looks clickable.
      Row(modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 4.dp, bottom = 16.dp), verticalAlignment = Alignment.CenterVertically) {
        SelectionContainer(modifier = Modifier.weight(1f)) {
          // The annotated string to display.
          val text = getText(text = value.value, pft = pft)

          // Increase the text line height so that the icon fits in multiline text.
          val textLineHeight = 28.sp

          // The icon to display at the end of the text if we have a Patient Friendly Term.
          val inlineContent = getInlineContent(lineHeight = textLineHeight)

          val textColor = if (pft == null) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.CategoriesRijkslint()
          val textStyle = MaterialTheme.typography.bodyMedium.copy(color = textColor, lineHeight = textLineHeight)

          var textLayoutResult by remember { mutableStateOf<TextLayoutResult?>(null) }
          Text(
            text = text,
            inlineContent = inlineContent,
            style = textStyle,
            onTextLayout = { textLayoutResult = it },
            modifier =
              if (pft ==
                null
              ) {
                Modifier
              } else {
                Modifier.dottedLine(text = value.value, color = textColor, layoutResult = textLayoutResult).clickable { onClickPft(pft) }
              },
          )
        }
      }
    }
  }
}

/**
 * Get a modifier that draws a dotted line under text.
 */
private fun Modifier.dottedLine(
  text: String,
  color: Color,
  layoutResult: TextLayoutResult?,
) = this.drawBehind {
  layoutResult?.let { layout ->
    val strokeWidth = 1.dp.toPx()
    val pathEffect = PathEffect.dashPathEffect(floatArrayOf(1f, 1f), 0f)
    val verticalOffset = 5.dp.toPx()

    // De hele tekst gebruiken
    val start = 0
    val end = text.length.coerceAtMost(layout.layoutInput.text.length)

    for (i in start until end) {
      val box = layout.getBoundingBox(i)
      val baseline = layout.getLineBaseline(layout.getLineForOffset(i)) + verticalOffset

      drawLine(
        color = color,
        start = Offset(box.left, baseline),
        end = Offset(box.right, baseline),
        strokeWidth = strokeWidth,
        pathEffect = pathEffect,
      )
    }
  }
}

/**
 * Get if a PFT (Patient Friendly Term) is available for this snomed code.
 */
@Composable
private fun getPft(snomedCode: String?): Pft? {
  if (LocalInspectionMode.current) {
    // If we are looking at the compose preview, do not use view model
    return if (snomedCode == null) {
      null
    } else {
      Pft(name = null, synonym = null, description = "")
    }
  } else {
    // Use view model to determine if we have a Pft
    val viewModel =
      hiltViewModel<UISchemaRowStaticViewModel, UISchemaRowStaticViewModel.Factory>(
        creationCallback = { factory -> factory.create(snomedCode = snomedCode) },
        key = snomedCode,
      )
    val pft by viewModel.pft.collectAsStateWithLifecycle()
    return pft
  }
}

/**
 * Get the text, with room for an icon at the end if we have a Patient Friendly Term.
 */
@Composable
private fun getText(
  text: String,
  pft: Pft?,
): AnnotatedString =
  buildAnnotatedString {
    append(text)
    if (pft != null) {
      appendInlineContent("icon", "[icon]")
    }
  }

/**
 * Get the icon to display at the end of the text.
 */
@Composable
private fun getInlineContent(lineHeight: TextUnit): Map<String, InlineTextContent> {
  val iconSizeDp = 24.dp

  // The amount of spacing between the text and the icon
  val iconPaddingDp = 4.dp

  val density = LocalDensity.current
  val placeholderWidth = with(density) { (iconSizeDp + iconPaddingDp).toSp() }

  val inlineContent =
    mapOf(
      "icon" to
        InlineTextContent(
          Placeholder(
            width = placeholderWidth,
            height = lineHeight,
            placeholderVerticalAlign = PlaceholderVerticalAlign.TextCenter,
          ),
        ) {
          Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Icon(
              modifier = Modifier.size(24.dp),
              painter = painterResource(R.drawable.ic_help),
              contentDescription = null,
              tint = MaterialTheme.colorScheme.CategoriesRijkslint(),
            )
          }
        },
    )

  return inlineContent
}

@PreviewLightDark
@Composable
internal fun UiSchemaRowStaticSingleValuePreview() {
  MgoTheme {
    UiSchemaRowStatic(
      row = UISchemaRow.Static(heading = "Heading", value = listOf(UISchemaRowStaticValue("Value"))),
      onClickPft = {},
    )
  }
}

@PreviewLightDark
@Composable
internal fun UiSchemaRowStaticSingleValueMultiLinePreview() {
  MgoTheme {
    UiSchemaRowStatic(
      row =
        UISchemaRow.Static(
          heading = "Heading",
          value = listOf(UISchemaRowStaticValue("Lorem Ipsum is simply dummy text of the printing and typesetting industry", snomedCode = "123")),
        ),
      onClickPft = {},
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
      onClickPft = {},
    )
  }
}
