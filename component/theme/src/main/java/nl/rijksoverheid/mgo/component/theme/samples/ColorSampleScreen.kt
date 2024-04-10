package nl.rijksoverheid.mgo.component.theme.samples

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.actionBorder
import nl.rijksoverheid.mgo.component.theme.actionPrimary
import nl.rijksoverheid.mgo.component.theme.actionSecondary
import nl.rijksoverheid.mgo.component.theme.actionTertiary
import nl.rijksoverheid.mgo.component.theme.apotheek
import nl.rijksoverheid.mgo.component.theme.backgroundPrimary
import nl.rijksoverheid.mgo.component.theme.backgroundSecondary
import nl.rijksoverheid.mgo.component.theme.backgroundTertiary
import nl.rijksoverheid.mgo.component.theme.bodyText
import nl.rijksoverheid.mgo.component.theme.contentPrimary
import nl.rijksoverheid.mgo.component.theme.contentSecondary
import nl.rijksoverheid.mgo.component.theme.contentTertiary
import nl.rijksoverheid.mgo.component.theme.fysiotherapeut
import nl.rijksoverheid.mgo.component.theme.ggz
import nl.rijksoverheid.mgo.component.theme.huisarts
import nl.rijksoverheid.mgo.component.theme.iconsPrimary
import nl.rijksoverheid.mgo.component.theme.kliniek
import nl.rijksoverheid.mgo.component.theme.linesPrimary
import nl.rijksoverheid.mgo.component.theme.linesSecondary
import nl.rijksoverheid.mgo.component.theme.notificationError
import nl.rijksoverheid.mgo.component.theme.notificationSuccess
import nl.rijksoverheid.mgo.component.theme.notificationWarning
import nl.rijksoverheid.mgo.component.theme.overige
import nl.rijksoverheid.mgo.component.theme.rijksLint
import nl.rijksoverheid.mgo.component.theme.tandarts
import nl.rijksoverheid.mgo.component.theme.verpleeghuis
import nl.rijksoverheid.mgo.component.theme.ziekenhuis

@Composable
private fun ColorSampleScreen() {
    LazyVerticalGrid(
        modifier = Modifier.background(MaterialTheme.colors.background).padding(16.dp),
        columns = GridCells.Adaptive(minSize = 100.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        item {
            ColorSample(color = MaterialTheme.colors.backgroundPrimary(), text = "Background Primary")
        }
        item {
            ColorSample(color = MaterialTheme.colors.backgroundSecondary(), text = "Background Secondary")
        }
        item {
            ColorSample(color = MaterialTheme.colors.backgroundTertiary(), text = "Background Tertiary")
        }
        item {
            ColorSample(color = MaterialTheme.colors.contentPrimary(), text = "Content Primary")
        }
        item {
            ColorSample(color = MaterialTheme.colors.contentSecondary(), text = "Content Secondary")
        }
        item {
            ColorSample(color = MaterialTheme.colors.contentTertiary(), text = "Content Tertiary")
        }
        item {
            ColorSample(color = MaterialTheme.colors.iconsPrimary(), text = "Icons Primary")
        }
        item {
            ColorSample(color = MaterialTheme.colors.iconsPrimary(), text = "Icons Secondary")
        }
        item {
            ColorSample(color = MaterialTheme.colors.linesPrimary(), text = "Lines Primary")
        }
        item {
            ColorSample(color = MaterialTheme.colors.linesSecondary(), text = "Lines Secondary")
        }
        item {
            ColorSample(color = MaterialTheme.colors.actionBorder(), text = "Action Border")
        }
        item {
            ColorSample(color = MaterialTheme.colors.actionPrimary(), text = "Action Primary")
        }
        item {
            ColorSample(color = MaterialTheme.colors.actionSecondary(), text = "Action Secondary")
        }
        item {
            ColorSample(color = MaterialTheme.colors.actionTertiary(), text = "Action Tertiary")
        }
        item {
            ColorSample(color = MaterialTheme.colors.rijksLint(), text = "Rijkslint")
        }
        item {
            ColorSample(color = MaterialTheme.colors.notificationSuccess(), text = "NotificationSuccess")
        }
        item {
            ColorSample(color = MaterialTheme.colors.notificationWarning(), text = "NotificationWarning")
        }
        item {
            ColorSample(color = MaterialTheme.colors.notificationError(), text = "NotificationError")
        }
        item {
            ColorSample(color = MaterialTheme.colors.apotheek(), text = "Apotheek")
        }
        item {
            ColorSample(color = MaterialTheme.colors.ziekenhuis(), text = "Ziekenhuis")
        }
        item {
            ColorSample(color = MaterialTheme.colors.huisarts(), text = "Huisarts")
        }
        item {
            ColorSample(color = MaterialTheme.colors.tandarts(), text = "Tandarts")
        }
        item {
            ColorSample(color = MaterialTheme.colors.ggz(), text = "GGZ")
        }
        item {
            ColorSample(color = MaterialTheme.colors.fysiotherapeut(), text = "Fysiotherapeut")
        }
        item {
            ColorSample(color = MaterialTheme.colors.verpleeghuis(), text = "Verpleeghuis")
        }
        item {
            ColorSample(color = MaterialTheme.colors.kliniek(), text = "Kliniek")
        }
        item {
            ColorSample(color = MaterialTheme.colors.overige(), text = "Overige")
        }
    }
}

@Composable
private fun ColorSample(
    color: Color,
    text: String,
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .background(color)
                    .border(0.1.dp, Color(0xFFE6E6E6)),
        )
        Box(modifier = Modifier.fillMaxWidth().height(36.dp).border(0.1.dp, Color(0xFFE6E6E6)), contentAlignment = Alignment.Center) {
            Text(
                modifier = Modifier.padding(4.dp),
                text = text,
                style = MaterialTheme.typography.bodyText.copy(fontSize = 10.sp),
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@PreviewLightDark
@Composable
internal fun ColorSampleScreenPreview() {
    MgoTheme {
        ColorSampleScreen()
    }
}
