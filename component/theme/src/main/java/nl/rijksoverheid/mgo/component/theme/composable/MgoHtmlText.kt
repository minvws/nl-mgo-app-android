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

/**
 * Composable that displays HTML Text.
 * Currently supports: <b> and <a href>.
 * @param html The text to display. Make sure that the text provided has the HTML tags inside it. This means wrapping the string in CDATA
 * if coming from strings.xml.
 */
@Composable
fun MgoHtmlText(
    html: String,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.bodySmall,
    onLinkClicked: (url: String) -> Unit = {},
    linkColor: Color = MaterialTheme.colors.actionTertiaryDefault(),
) {
    val annotatedString =
        buildAnnotatedString {
            var currentIndex = 0
            val regex = Regex("<(b|a href=['\"]([^'\"]+)['\"])>(.+?)</(b|a)>")
            regex.findAll(html).forEach { matchResult ->
                val tag = matchResult.groups[1]?.value
                val url = matchResult.groups[2]?.value
                val content = matchResult.groups[3]?.value ?: ""
                val start = matchResult.range.first
                val end = matchResult.range.last + 1

                if (currentIndex < start) {
                    append(html.substring(currentIndex, start))
                }

                if (tag?.startsWith("a href") == true && url != null) {
                    pushStringAnnotation(tag = "URL", annotation = url)
                    withStyle(style = SpanStyle(color = linkColor, textDecoration = TextDecoration.Underline)) {
                        append(content)
                    }
                    pop()
                } else if (tag == "b") {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append(content)
                    }
                }

                currentIndex = end
            }
            if (currentIndex < html.length) {
                append(html.substring(currentIndex, html.length))
            }
        }
    ClickableText(
        modifier = modifier,
        text = annotatedString,
        style = style.copy(color = LocalContentColor.current.copy(alpha = LocalContentAlpha.current)),
        onClick = { offset ->
            annotatedString.getStringAnnotations(tag = "URL", start = offset, end = offset)
                .firstOrNull()?.let { annotation ->
                    onLinkClicked(annotation.item)
                }
        },
    )
}

@PreviewLightDark
@Composable
internal fun MgoHtmlTextPreview() {
    MgoTheme {
        MgoHtmlText(html = "Hello <b>World</b>. This is a <a href='https://www.google.nl'>link</a>.")
    }
}
