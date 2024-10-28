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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import nl.rijksoverheid.mgo.component.theme.actionPrimaryDefaultBackground
import nl.rijksoverheid.mgo.component.theme.actionPrimaryDefaultText
import nl.rijksoverheid.mgo.component.theme.actionPrimaryNegativeBackground
import nl.rijksoverheid.mgo.component.theme.actionPrimaryNegativeText
import nl.rijksoverheid.mgo.component.theme.actionSecondaryDefaultBackground
import nl.rijksoverheid.mgo.component.theme.actionSecondaryDefaultText
import nl.rijksoverheid.mgo.component.theme.actionSecondaryNegativeBackground
import nl.rijksoverheid.mgo.component.theme.actionSecondaryNegativeText
import nl.rijksoverheid.mgo.component.theme.actionTertiaryDefaultText
import nl.rijksoverheid.mgo.component.theme.actionTertiaryNegativeText
import nl.rijksoverheid.mgo.component.theme.backgroundPrimary
import nl.rijksoverheid.mgo.component.theme.backgroundSecondary
import nl.rijksoverheid.mgo.component.theme.backgroundTertiary
import nl.rijksoverheid.mgo.component.theme.contentPrimary
import nl.rijksoverheid.mgo.component.theme.contentSecondary
import nl.rijksoverheid.mgo.component.theme.contentTertiary
import nl.rijksoverheid.mgo.component.theme.iconsPrimary
import nl.rijksoverheid.mgo.component.theme.iconsSecondary
import nl.rijksoverheid.mgo.component.theme.linesInput
import nl.rijksoverheid.mgo.component.theme.linesPrimary
import nl.rijksoverheid.mgo.component.theme.linesSecondary
import nl.rijksoverheid.mgo.component.theme.linesTertiary
import nl.rijksoverheid.mgo.component.theme.notificationError
import nl.rijksoverheid.mgo.component.theme.notificationInformation
import nl.rijksoverheid.mgo.component.theme.notificationSuccess
import nl.rijksoverheid.mgo.component.theme.notificationWarning
import nl.rijksoverheid.mgo.component.theme.strokesPrimary
import nl.rijksoverheid.mgo.component.theme.strokesTertiary
import nl.rijksoverheid.mgo.component.theme.supportApotheek
import nl.rijksoverheid.mgo.component.theme.supportFysiotherapeut
import nl.rijksoverheid.mgo.component.theme.supportGgz
import nl.rijksoverheid.mgo.component.theme.supportHuisarts
import nl.rijksoverheid.mgo.component.theme.supportKliniek
import nl.rijksoverheid.mgo.component.theme.supportOverige
import nl.rijksoverheid.mgo.component.theme.supportRijkslint
import nl.rijksoverheid.mgo.component.theme.supportTandarts
import nl.rijksoverheid.mgo.component.theme.supportThuiszorg
import nl.rijksoverheid.mgo.component.theme.supportVerpleeghuis
import nl.rijksoverheid.mgo.component.theme.supportZiekenhuis

@Composable
private fun ColorSampleScreen() {
    LazyVerticalGrid(
        modifier = Modifier.background(MaterialTheme.colorScheme.background).padding(16.dp),
        columns = GridCells.Adaptive(minSize = 100.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        item {
            ColorSample(color = MaterialTheme.colorScheme.backgroundPrimary(), text = "Background Primary")
        }
        item {
            ColorSample(color = MaterialTheme.colorScheme.backgroundSecondary(), text = "Background Secondary")
        }
        item {
            ColorSample(color = MaterialTheme.colorScheme.backgroundTertiary(), text = "Background Tertiary")
        }
        item {
            ColorSample(color = MaterialTheme.colorScheme.contentPrimary(), text = "Content Primary")
        }
        item {
            ColorSample(color = MaterialTheme.colorScheme.contentSecondary(), text = "Content Secondary")
        }
        item {
            ColorSample(color = MaterialTheme.colorScheme.contentTertiary(), text = "Content Tertiary")
        }
        item {
            ColorSample(color = MaterialTheme.colorScheme.iconsPrimary(), text = "Icons Primary")
        }
        item {
            ColorSample(color = MaterialTheme.colorScheme.iconsSecondary(), text = "Icons Secondary")
        }
        item {
            ColorSample(color = MaterialTheme.colorScheme.strokesPrimary(), text = "Strokes Primary")
        }
        item {
            ColorSample(color = MaterialTheme.colorScheme.strokesPrimary(), text = "Strokes Secondary")
        }
        item {
            ColorSample(color = MaterialTheme.colorScheme.strokesTertiary(), text = "Strokes Tertiary")
        }
        item {
            ColorSample(color = MaterialTheme.colorScheme.linesPrimary(), text = "Lines Primary")
        }
        item {
            ColorSample(color = MaterialTheme.colorScheme.linesSecondary(), text = "Lines Secondary")
        }
        item {
            ColorSample(color = MaterialTheme.colorScheme.linesTertiary(), text = "Lines Tertiary")
        }
        item {
            ColorSample(color = MaterialTheme.colorScheme.linesInput(), text = "Lines Input")
        }
        item {
            ColorSample(color = MaterialTheme.colorScheme.actionPrimaryDefaultBackground(), text = "Action Primary Default Background")
        }
        item {
            ColorSample(color = MaterialTheme.colorScheme.actionPrimaryDefaultText(), text = "Action Primary Default Text")
        }
        item {
            ColorSample(color = MaterialTheme.colorScheme.actionPrimaryNegativeBackground(), text = "Action Primary Negative Background")
        }
        item {
            ColorSample(color = MaterialTheme.colorScheme.actionPrimaryNegativeText(), text = "Action Primary Negative Text")
        }
        item {
            ColorSample(color = MaterialTheme.colorScheme.actionSecondaryDefaultBackground(), text = "Action Secondary Default Background")
        }
        item {
            ColorSample(color = MaterialTheme.colorScheme.actionSecondaryDefaultText(), text = "Action Secondary Default Text")
        }
        item {
            ColorSample(
                color = MaterialTheme.colorScheme.actionSecondaryNegativeBackground(),
                text = "Action Secondary Negative Background",
            )
        }
        item {
            ColorSample(color = MaterialTheme.colorScheme.actionSecondaryNegativeText(), text = "Action Secondary Negative Text")
        }
        item {
            ColorSample(color = MaterialTheme.colorScheme.actionTertiaryDefaultText(), text = "Action Tertiary Default Text")
        }
        item {
            ColorSample(color = MaterialTheme.colorScheme.actionTertiaryNegativeText(), text = "Action Tertiary Negative Text")
        }
        item {
            ColorSample(color = MaterialTheme.colorScheme.notificationInformation(), text = "Notification Information")
        }
        item {
            ColorSample(color = MaterialTheme.colorScheme.notificationSuccess(), text = "Notification Success")
        }
        item {
            ColorSample(color = MaterialTheme.colorScheme.notificationWarning(), text = "Notification Warning")
        }
        item {
            ColorSample(color = MaterialTheme.colorScheme.notificationError(), text = "Notification Error")
        }
        item {
            ColorSample(color = MaterialTheme.colorScheme.supportApotheek(), text = "Support Apotheek")
        }
        item {
            ColorSample(color = MaterialTheme.colorScheme.supportZiekenhuis(), text = "Support Ziekenhuis")
        }
        item {
            ColorSample(color = MaterialTheme.colorScheme.supportHuisarts(), text = "Support Huisarts")
        }
        item {
            ColorSample(color = MaterialTheme.colorScheme.supportTandarts(), text = "Support Tandarts")
        }
        item {
            ColorSample(color = MaterialTheme.colorScheme.supportGgz(), text = "Support GGZ")
        }
        item {
            ColorSample(color = MaterialTheme.colorScheme.supportFysiotherapeut(), text = "Support Fysiotherapeut")
        }
        item {
            ColorSample(color = MaterialTheme.colorScheme.supportVerpleeghuis(), text = "Support Verpleeghuis")
        }
        item {
            ColorSample(color = MaterialTheme.colorScheme.supportThuiszorg(), text = "Support Thuiszorg")
        }
        item {
            ColorSample(color = MaterialTheme.colorScheme.supportKliniek(), text = "Support Kliniek")
        }
        item {
            ColorSample(color = MaterialTheme.colorScheme.supportOverige(), text = "Support Overige")
        }
        item {
            ColorSample(color = MaterialTheme.colorScheme.supportRijkslint(), text = "Support Rijkslint")
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
                style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp),
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
