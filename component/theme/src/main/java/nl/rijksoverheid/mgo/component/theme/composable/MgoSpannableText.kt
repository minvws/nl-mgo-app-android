package nl.rijksoverheid.mgo.component.theme.composable

import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.PreviewLightDark
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.actionTertiaryDefault
import nl.rijksoverheid.mgo.component.theme.bodySmall

@Composable
fun MgoSpannableText(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.bodySmall,
    onUrlClick: (url: String) -> Unit = {},
    linkColor: Color = MaterialTheme.colors.actionTertiaryDefault(),
) {
    val annotatedString =
        buildAnnotatedString {
            var currentIndex = 0
            val regex = Regex("""(\*\*.*?\*\*|\[.*?]\(.*?\))""")
            val matches = regex.findAll(text)

            for (match in matches) {
                val matchRange = match.range
                append(text.substring(currentIndex, matchRange.first))
                val matchText = match.value
                when {
                    matchText.startsWith("**") && matchText.endsWith("**") -> {
                        val boldText = matchText.substring(2, matchText.length - 2)
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append(boldText)
                        }
                    }

                    matchText.startsWith("[") && matchText.contains("](") && matchText.endsWith(")") -> {
                        val textStart = matchText.indexOf("[") + 1
                        val textEnd = matchText.indexOf("]")
                        val linkStart = matchText.indexOf("(") + 1
                        val linkEnd = matchText.indexOf(")")

                        val linkText = matchText.substring(textStart, textEnd)
                        val linkUrl = matchText.substring(linkStart, linkEnd)

                        val start = length
                        append(linkText)
                        addStyle(
                            style = SpanStyle(color = linkColor, textDecoration = TextDecoration.Underline),
                            start = start,
                            end = length,
                        )
                        addStringAnnotation(
                            tag = "URL",
                            annotation = linkUrl,
                            start = start,
                            end = length,
                        )
                    }
                }
                currentIndex = matchRange.last + 1
            }
            append(text.substring(currentIndex))
        }
    ClickableText(
        modifier = modifier,
        text = annotatedString,
        style = style.copy(color = LocalContentColor.current.copy(alpha = LocalContentAlpha.current)),
        onClick = { offset ->
            annotatedString.getStringAnnotations("URL", offset, offset).firstOrNull()?.let { annotation ->
                onUrlClick(annotation.item)
            }
        },
    )
}

@PreviewLightDark
@Composable
internal fun MgoSpannableTextPreview() {
    MgoTheme {
        MgoSpannableText(text = "Hello **World**. This is a [link](https://www.google.nl).")
    }
}
