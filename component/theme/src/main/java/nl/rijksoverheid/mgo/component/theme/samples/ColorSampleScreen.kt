@file:OptIn(ExperimentalLayoutApi::class)

package nl.rijksoverheid.mgo.component.theme.samples

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.backgroundPrimary
import nl.rijksoverheid.mgo.component.theme.backgroundSecondary
import nl.rijksoverheid.mgo.component.theme.backgroundTertiary
import nl.rijksoverheid.mgo.component.theme.borderPrimary
import nl.rijksoverheid.mgo.component.theme.borderSecondary
import nl.rijksoverheid.mgo.component.theme.contentInvert
import nl.rijksoverheid.mgo.component.theme.contentPrimary
import nl.rijksoverheid.mgo.component.theme.contentSecondary
import nl.rijksoverheid.mgo.component.theme.interactivePrimaryCriticalBackground
import nl.rijksoverheid.mgo.component.theme.interactivePrimaryCriticalText
import nl.rijksoverheid.mgo.component.theme.interactivePrimaryDefaultBackground
import nl.rijksoverheid.mgo.component.theme.interactivePrimaryDefaultText
import nl.rijksoverheid.mgo.component.theme.interactiveSecondaryCriticalBackground
import nl.rijksoverheid.mgo.component.theme.interactiveSecondaryCriticalText
import nl.rijksoverheid.mgo.component.theme.interactiveSecondaryDefaultBackground
import nl.rijksoverheid.mgo.component.theme.interactiveSecondaryDefaultText
import nl.rijksoverheid.mgo.component.theme.interactiveTertiaryCriticalText
import nl.rijksoverheid.mgo.component.theme.interactiveTertiaryDefaultText
import nl.rijksoverheid.mgo.component.theme.sentimentCritical
import nl.rijksoverheid.mgo.component.theme.sentimentInformative
import nl.rijksoverheid.mgo.component.theme.sentimentPositive
import nl.rijksoverheid.mgo.component.theme.sentimentWarning
import nl.rijksoverheid.mgo.component.theme.supportAllergies
import nl.rijksoverheid.mgo.component.theme.supportContacts
import nl.rijksoverheid.mgo.component.theme.supportDevice
import nl.rijksoverheid.mgo.component.theme.supportDocuments
import nl.rijksoverheid.mgo.component.theme.supportFunctional
import nl.rijksoverheid.mgo.component.theme.supportLaboratory
import nl.rijksoverheid.mgo.component.theme.supportLifestyle
import nl.rijksoverheid.mgo.component.theme.supportMedication
import nl.rijksoverheid.mgo.component.theme.supportPayer
import nl.rijksoverheid.mgo.component.theme.supportPersonal
import nl.rijksoverheid.mgo.component.theme.supportProblems
import nl.rijksoverheid.mgo.component.theme.supportProcedures
import nl.rijksoverheid.mgo.component.theme.supportRijkslint
import nl.rijksoverheid.mgo.component.theme.supportTreatment
import nl.rijksoverheid.mgo.component.theme.supportVaccinations
import nl.rijksoverheid.mgo.component.theme.supportVitals
import nl.rijksoverheid.mgo.component.theme.supportWarning
import nl.rijksoverheid.mgo.component.theme.symbolsPrimary
import nl.rijksoverheid.mgo.component.theme.symbolsSecondary
import nl.rijksoverheid.mgo.component.theme.symbolsTertiary

@Composable
private fun BackgroundColors() {
  Column {
    Text(text = "Background", style = MaterialTheme.typography.headlineLarge)
    FlowRow(modifier = Modifier.padding(vertical = 16.dp)) {
      ColorSample(color = MaterialTheme.colorScheme.backgroundPrimary(), text = "Primary")
      ColorSample(color = MaterialTheme.colorScheme.backgroundSecondary(), text = "Secondary")
      ColorSample(color = MaterialTheme.colorScheme.backgroundTertiary(), text = "Tertiary")
    }
  }
}

@Composable
private fun ContentColors() {
  Column {
    Text(text = "Content", style = MaterialTheme.typography.headlineLarge)
    FlowRow(modifier = Modifier.padding(top = 16.dp)) {
      ColorSample(color = MaterialTheme.colorScheme.contentPrimary(), text = "Primary")
      ColorSample(color = MaterialTheme.colorScheme.contentSecondary(), text = "Secondary")
      ColorSample(color = MaterialTheme.colorScheme.contentInvert(), text = "Invert")
    }
  }
}

@Composable
private fun BorderColors() {
  Column {
    Text(text = "Border", style = MaterialTheme.typography.headlineLarge)
    FlowRow(modifier = Modifier.padding(top = 16.dp)) {
      ColorSample(color = MaterialTheme.colorScheme.borderPrimary(), text = "Primary")
      ColorSample(color = MaterialTheme.colorScheme.borderSecondary(), text = "Secondary")
    }
  }
}

@Composable
private fun SymbolColors() {
  Column {
    Text(text = "Symbol", style = MaterialTheme.typography.headlineLarge)
    FlowRow(modifier = Modifier.padding(top = 16.dp)) {
      ColorSample(color = MaterialTheme.colorScheme.symbolsPrimary(), text = "Primary")
      ColorSample(color = MaterialTheme.colorScheme.symbolsSecondary(), text = "Secondary")
      ColorSample(color = MaterialTheme.colorScheme.symbolsTertiary(), text = "Tertiary")
    }
  }
}

@Composable
private fun SentimentColors() {
  Column {
    Text(text = "Sentiment", style = MaterialTheme.typography.headlineLarge)
    FlowRow(modifier = Modifier.padding(top = 16.dp)) {
      ColorSample(
        color = MaterialTheme.colorScheme.sentimentInformative(),
        text = "Informative",
      )
      ColorSample(color = MaterialTheme.colorScheme.sentimentPositive(), text = "Positive")
      ColorSample(color = MaterialTheme.colorScheme.sentimentWarning(), text = "Warning")
      ColorSample(color = MaterialTheme.colorScheme.sentimentCritical(), text = "Critical")
    }
  }
}

@Composable
private fun InteractiveColors() {
  Column {
    Text(text = "Interactive", style = MaterialTheme.typography.headlineLarge)

    Text(
      text = "Primary",
      style = MaterialTheme.typography.headlineMedium,
      modifier = Modifier.padding(top = 4.dp),
    )

    Text(
      text = "Default",
      style = MaterialTheme.typography.headlineSmall,
      modifier = Modifier.padding(top = 4.dp),
    )
    FlowRow(modifier = Modifier.padding(top = 4.dp)) {
      ColorSample(
        color = MaterialTheme.colorScheme.interactivePrimaryDefaultBackground(),
        text = "Background",
      )
      ColorSample(
        color = MaterialTheme.colorScheme.interactivePrimaryDefaultText(),
        text = "Text",
      )
    }

    Text(
      text = "Critical",
      style = MaterialTheme.typography.headlineSmall,
      modifier = Modifier.padding(top = 4.dp),
    )
    FlowRow(modifier = Modifier.padding(top = 4.dp)) {
      ColorSample(
        color = MaterialTheme.colorScheme.interactivePrimaryCriticalBackground(),
        text = "Background",
      )
      ColorSample(
        color = MaterialTheme.colorScheme.interactivePrimaryCriticalText(),
        text = "Text",
      )
    }

    Text(
      text = "Secondary",
      style = MaterialTheme.typography.headlineMedium,
      modifier = Modifier.padding(top = 4.dp),
    )

    Text(
      text = "Default",
      style = MaterialTheme.typography.headlineSmall,
      modifier = Modifier.padding(top = 4.dp),
    )
    FlowRow(modifier = Modifier.padding(top = 4.dp)) {
      ColorSample(
        color = MaterialTheme.colorScheme.interactiveSecondaryDefaultBackground(),
        text = "Background",
      )
      ColorSample(
        color = MaterialTheme.colorScheme.interactiveSecondaryDefaultText(),
        text = "Text",
      )
    }

    Text(
      text = "Critical",
      style = MaterialTheme.typography.headlineSmall,
      modifier = Modifier.padding(top = 4.dp),
    )
    FlowRow(modifier = Modifier.padding(top = 4.dp)) {
      ColorSample(
        color = MaterialTheme.colorScheme.interactiveSecondaryCriticalBackground(),
        text = "Background",
      )
      ColorSample(
        color = MaterialTheme.colorScheme.interactiveSecondaryCriticalText(),
        text = "Text",
      )
    }

    Text(
      text = "Tertiary",
      style = MaterialTheme.typography.headlineMedium,
      modifier = Modifier.padding(top = 16.dp),
    )

    Text(
      text = "Default",
      style = MaterialTheme.typography.headlineSmall,
      modifier = Modifier.padding(top = 16.dp),
    )
    FlowRow(modifier = Modifier.padding(top = 16.dp)) {
      ColorSample(
        color = MaterialTheme.colorScheme.interactiveTertiaryDefaultText(),
        text = "Text",
      )
    }

    Text(
      text = "Critical",
      style = MaterialTheme.typography.headlineSmall,
      modifier = Modifier.padding(top = 16.dp),
    )
    FlowRow(modifier = Modifier.padding(top = 16.dp)) {
      ColorSample(
        color = MaterialTheme.colorScheme.interactiveTertiaryCriticalText(),
        text = "Text",
      )
    }
  }
}

@Composable
private fun SupportColors() {
  Column {
    Text(text = "Support", style = MaterialTheme.typography.headlineLarge)
    FlowRow(modifier = Modifier.padding(top = 16.dp)) {
      ColorSample(color = MaterialTheme.colorScheme.supportMedication(), text = "Medication")
      ColorSample(color = MaterialTheme.colorScheme.supportTreatment(), text = "Positive")
      ColorSample(color = MaterialTheme.colorScheme.supportContacts(), text = "Contacts")
      ColorSample(color = MaterialTheme.colorScheme.supportLaboratory(), text = "Laboratory")
      ColorSample(color = MaterialTheme.colorScheme.supportFunctional(), text = "Functional")
      ColorSample(color = MaterialTheme.colorScheme.supportDevice(), text = "Device")
      ColorSample(color = MaterialTheme.colorScheme.supportVitals(), text = "Vitals")
      ColorSample(color = MaterialTheme.colorScheme.supportDocuments(), text = "Documents")
      ColorSample(color = MaterialTheme.colorScheme.supportAllergies(), text = "Allergies")
      ColorSample(color = MaterialTheme.colorScheme.supportProblems(), text = "Problems")
      ColorSample(color = MaterialTheme.colorScheme.supportPersonal(), text = "Personal")
      ColorSample(color = MaterialTheme.colorScheme.supportRijkslint(), text = "Rijkslint")
      ColorSample(color = MaterialTheme.colorScheme.supportWarning(), text = "Warning")
      ColorSample(color = MaterialTheme.colorScheme.supportPayer(), text = "Payer")
      ColorSample(
        color = MaterialTheme.colorScheme.supportVaccinations(),
        text = "Vaccinations",
      )
      ColorSample(color = MaterialTheme.colorScheme.supportProcedures(), text = "Procedures")
      ColorSample(color = MaterialTheme.colorScheme.supportLifestyle(), text = "Lifestyle")
    }
  }
}

@Composable
private fun ColorSample(
  color: Color,
  text: String,
) {
  Box(
    modifier =
      Modifier
        .fillMaxWidth()
        .background(color)
        .border(0.1.dp, Color(0xFFE6E6E6))
        .padding(2.dp),
  ) {
    Text(
      modifier = Modifier.padding(8.dp),
      text = text,
      style = MaterialTheme.typography.bodyMedium.copy(fontSize = 10.sp),
      color = color.getTextColor(),
      fontWeight = FontWeight.Bold,
      textAlign = TextAlign.Center,
    )
  }
}

@Composable
private fun Color.getTextColor(): Color {
  return if (isSystemInDarkTheme()) {
    if (luminance() <= 0.5f) {
      MaterialTheme.colorScheme.contentPrimary()
    } else {
      MaterialTheme.colorScheme
        .contentPrimary(!isSystemInDarkTheme())
    }
  } else {
    if (luminance() >= 0.5f) {
      MaterialTheme.colorScheme.contentPrimary()
    } else {
      MaterialTheme.colorScheme
        .contentPrimary(!isSystemInDarkTheme())
    }
  }
}

@PreviewLightDark
@Composable
internal fun BackgroundColorsPreview() {
  MgoTheme {
    Box(modifier = Modifier.padding(16.dp)) {
      BackgroundColors()
    }
  }
}

@PreviewLightDark
@Composable
internal fun ContentColorsPreview() {
  MgoTheme {
    Box(modifier = Modifier.padding(16.dp)) {
      ContentColors()
    }
  }
}

@PreviewLightDark
@Composable
internal fun BorderColorsPreview() {
  MgoTheme {
    Box(modifier = Modifier.padding(16.dp)) {
      BorderColors()
    }
  }
}

@PreviewLightDark
@Composable
internal fun SymbolColorsPreview() {
  MgoTheme {
    Box(modifier = Modifier.padding(16.dp)) {
      SymbolColors()
    }
  }
}

@PreviewLightDark
@Composable
internal fun SentimentColorsPreview() {
  MgoTheme {
    Box(modifier = Modifier.padding(16.dp)) {
      SentimentColors()
    }
  }
}

@PreviewLightDark
@Composable
internal fun InteractiveColorsPreview() {
  MgoTheme {
    Box(modifier = Modifier.padding(16.dp)) {
      InteractiveColors()
    }
  }
}

@PreviewLightDark
@Composable
internal fun SupportColorsPreview() {
  MgoTheme {
    Box(modifier = Modifier.padding(16.dp)) {
      SupportColors()
    }
  }
}
