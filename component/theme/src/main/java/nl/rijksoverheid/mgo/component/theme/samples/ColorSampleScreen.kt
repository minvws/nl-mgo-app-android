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
import nl.rijksoverheid.mgo.component.theme.actionPrimaryBackground
import nl.rijksoverheid.mgo.component.theme.actionPrimaryText
import nl.rijksoverheid.mgo.component.theme.actionSecondaryBackground
import nl.rijksoverheid.mgo.component.theme.actionSecondaryText
import nl.rijksoverheid.mgo.component.theme.actionTertiaryDefault
import nl.rijksoverheid.mgo.component.theme.backgroundPrimary
import nl.rijksoverheid.mgo.component.theme.backgroundSecondary
import nl.rijksoverheid.mgo.component.theme.backgroundTertiary
import nl.rijksoverheid.mgo.component.theme.bodySmall
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
            ColorSample(color = MaterialTheme.colors.iconsSecondary(), text = "Icons Secondary")
        }
        item {
            ColorSample(color = MaterialTheme.colors.linesPrimary(), text = "Lines Primary")
        }
        item {
            ColorSample(color = MaterialTheme.colors.linesSecondary(), text = "Lines Secondary")
        }
        item {
            ColorSample(color = MaterialTheme.colors.linesTertiary(), text = "Lines Tertiary")
        }
        item {
            ColorSample(color = MaterialTheme.colors.linesInput(), text = "Lines Input")
        }
        item {
            ColorSample(color = MaterialTheme.colors.actionPrimaryBackground(), text = "Action Primary Background")
        }
        item {
            ColorSample(color = MaterialTheme.colors.actionPrimaryText(), text = "Action Primary Text")
        }
        item {
            ColorSample(color = MaterialTheme.colors.actionSecondaryBackground(), text = "Action Secondary Background")
        }
        item {
            ColorSample(color = MaterialTheme.colors.actionSecondaryText(), text = "Action Secondary Text")
        }
        item {
            ColorSample(color = MaterialTheme.colors.actionTertiaryDefault(), text = "Action Tertiary Default")
        }
        item {
            ColorSample(color = MaterialTheme.colors.notificationInformation(), text = "Notification Information")
        }
        item {
            ColorSample(color = MaterialTheme.colors.notificationSuccess(), text = "Notification Success")
        }
        item {
            ColorSample(color = MaterialTheme.colors.notificationWarning(), text = "Notification Warning")
        }
        item {
            ColorSample(color = MaterialTheme.colors.notificationError(), text = "Notification Error")
        }
        item {
            ColorSample(color = MaterialTheme.colors.supportApotheek(), text = "Support Apotheek")
        }
        item {
            ColorSample(color = MaterialTheme.colors.supportZiekenhuis(), text = "Support Ziekenhuis")
        }
        item {
            ColorSample(color = MaterialTheme.colors.supportHuisarts(), text = "Support Huisarts")
        }
        item {
            ColorSample(color = MaterialTheme.colors.supportTandarts(), text = "Support Tandarts")
        }
        item {
            ColorSample(color = MaterialTheme.colors.supportGgz(), text = "Support GGZ")
        }
        item {
            ColorSample(color = MaterialTheme.colors.supportFysiotherapeut(), text = "Support Fysiotherapeut")
        }
        item {
            ColorSample(color = MaterialTheme.colors.supportVerpleeghuis(), text = "Support Verpleeghuis")
        }
        item {
            ColorSample(color = MaterialTheme.colors.supportThuiszorg(), text = "Support Thuiszorg")
        }
        item {
            ColorSample(color = MaterialTheme.colors.supportKliniek(), text = "Support Kliniek")
        }
        item {
            ColorSample(color = MaterialTheme.colors.supportOverige(), text = "Support Overige")
        }
        item {
            ColorSample(color = MaterialTheme.colors.supportRijkslint(), text = "Support Rijkslint")
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
