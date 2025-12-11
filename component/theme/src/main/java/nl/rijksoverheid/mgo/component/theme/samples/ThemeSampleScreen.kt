package nl.rijksoverheid.mgo.component.theme.samples

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import nl.rijksoverheid.mgo.component.theme.ActionsGhostText
import nl.rijksoverheid.mgo.component.theme.ActionsSolidBackground
import nl.rijksoverheid.mgo.component.theme.ActionsSolidText
import nl.rijksoverheid.mgo.component.theme.ActionsTonalBackground
import nl.rijksoverheid.mgo.component.theme.ActionsTonalText
import nl.rijksoverheid.mgo.component.theme.BackgroundsPrimary
import nl.rijksoverheid.mgo.component.theme.BackgroundsSecondary
import nl.rijksoverheid.mgo.component.theme.BackgroundsTertiary
import nl.rijksoverheid.mgo.component.theme.CategoriesAdministration
import nl.rijksoverheid.mgo.component.theme.CategoriesAllergies
import nl.rijksoverheid.mgo.component.theme.CategoriesContacts
import nl.rijksoverheid.mgo.component.theme.CategoriesDevice
import nl.rijksoverheid.mgo.component.theme.CategoriesDocuments
import nl.rijksoverheid.mgo.component.theme.CategoriesLaboratory
import nl.rijksoverheid.mgo.component.theme.CategoriesLifestyle
import nl.rijksoverheid.mgo.component.theme.CategoriesMedication
import nl.rijksoverheid.mgo.component.theme.CategoriesMental
import nl.rijksoverheid.mgo.component.theme.CategoriesPlan
import nl.rijksoverheid.mgo.component.theme.CategoriesProblems
import nl.rijksoverheid.mgo.component.theme.CategoriesProcedures
import nl.rijksoverheid.mgo.component.theme.CategoriesProviders
import nl.rijksoverheid.mgo.component.theme.CategoriesRijkslint
import nl.rijksoverheid.mgo.component.theme.CategoriesVaccinations
import nl.rijksoverheid.mgo.component.theme.CategoriesVitals
import nl.rijksoverheid.mgo.component.theme.CategoriesWarning
import nl.rijksoverheid.mgo.component.theme.LabelsInvert
import nl.rijksoverheid.mgo.component.theme.LabelsPrimary
import nl.rijksoverheid.mgo.component.theme.LabelsSecondary
import nl.rijksoverheid.mgo.component.theme.LabelsVibrant
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.SeparatorsInvert
import nl.rijksoverheid.mgo.component.theme.SeparatorsSecondary
import nl.rijksoverheid.mgo.component.theme.SeperatorsPrimary
import nl.rijksoverheid.mgo.component.theme.StatesCritical
import nl.rijksoverheid.mgo.component.theme.StatesInformative
import nl.rijksoverheid.mgo.component.theme.StatesPositive
import nl.rijksoverheid.mgo.component.theme.StatesWarning
import nl.rijksoverheid.mgo.component.theme.SymbolsPrimary
import nl.rijksoverheid.mgo.component.theme.SymbolsSecondary
import nl.rijksoverheid.mgo.component.theme.SymbolsTertiary

@Composable
private fun Backgrounds() {
  Column {
    Text(text = "Backgrounds", style = MaterialTheme.typography.headlineLarge)
    FlowRow(modifier = Modifier.padding(vertical = 16.dp)) {
      ThemeSample(theme = MaterialTheme.colorScheme.BackgroundsPrimary(), text = "Backgrounds Primary")
      ThemeSample(theme = MaterialTheme.colorScheme.BackgroundsSecondary(), text = "Backgrounds Secondary")
      ThemeSample(theme = MaterialTheme.colorScheme.BackgroundsTertiary(), text = "Backgrounds Tertiary")
    }
  }
}

@Composable
private fun Labels() {
  Column {
    Text(text = "Labels", style = MaterialTheme.typography.headlineLarge)
    FlowRow(modifier = Modifier.padding(vertical = 16.dp)) {
      ThemeSample(theme = MaterialTheme.colorScheme.LabelsPrimary(), text = "Labels Primary")
      ThemeSample(theme = MaterialTheme.colorScheme.LabelsSecondary(), text = "Labels Secondary")
      ThemeSample(theme = MaterialTheme.colorScheme.LabelsInvert(), text = "Labels Invert")
      ThemeSample(theme = MaterialTheme.colorScheme.LabelsVibrant(), text = "Labels Vibrant")
    }
  }
}

@Composable
private fun Separators() {
  Column {
    Text(text = "Labels", style = MaterialTheme.typography.headlineLarge)
    FlowRow(modifier = Modifier.padding(vertical = 16.dp)) {
      ThemeSample(theme = MaterialTheme.colorScheme.SeperatorsPrimary(), text = "Separators Primary")
      ThemeSample(theme = MaterialTheme.colorScheme.SeparatorsSecondary(), text = "Separators Secondary")
      ThemeSample(theme = MaterialTheme.colorScheme.SeparatorsInvert(), text = "Separators Invert")
    }
  }
}

@Composable
private fun Symbols() {
  Column {
    Text(text = "Backgrounds", style = MaterialTheme.typography.headlineLarge)
    FlowRow(modifier = Modifier.padding(vertical = 16.dp)) {
      ThemeSample(theme = MaterialTheme.colorScheme.SymbolsPrimary(), text = "Symbols Primary")
      ThemeSample(theme = MaterialTheme.colorScheme.SymbolsSecondary(), text = "Symbols Secondary")
      ThemeSample(theme = MaterialTheme.colorScheme.SymbolsTertiary(), text = "Symbols Tertiary")
    }
  }
}

@Composable
private fun Categories() {
  Column {
    Text(text = "Categories", style = MaterialTheme.typography.headlineLarge)
    FlowRow(modifier = Modifier.padding(vertical = 16.dp)) {
      ThemeSample(theme = MaterialTheme.colorScheme.CategoriesRijkslint(), text = "Categories Rijkslint")
      ThemeSample(theme = MaterialTheme.colorScheme.CategoriesMedication(), text = "Categories Medication")
      ThemeSample(theme = MaterialTheme.colorScheme.CategoriesContacts(), text = "Categories Contacts")
      ThemeSample(theme = MaterialTheme.colorScheme.CategoriesLaboratory(), text = "Categories Laboratory")
      ThemeSample(theme = MaterialTheme.colorScheme.CategoriesMental(), text = "Categories Mental")
      ThemeSample(theme = MaterialTheme.colorScheme.CategoriesDevice(), text = "Categories Device")
      ThemeSample(theme = MaterialTheme.colorScheme.CategoriesVitals(), text = "Categories Vitals")
      ThemeSample(theme = MaterialTheme.colorScheme.CategoriesDocuments(), text = "Categories Documents")
      ThemeSample(theme = MaterialTheme.colorScheme.CategoriesVaccinations(), text = "Categories Vaccinations")
      ThemeSample(theme = MaterialTheme.colorScheme.CategoriesAllergies(), text = "Categories Allergies")
      ThemeSample(theme = MaterialTheme.colorScheme.CategoriesProblems(), text = "Categories Problems")
      ThemeSample(theme = MaterialTheme.colorScheme.CategoriesAdministration(), text = "Categories Administration")
      ThemeSample(theme = MaterialTheme.colorScheme.CategoriesWarning(), text = "Categories Warning")
      ThemeSample(theme = MaterialTheme.colorScheme.CategoriesProviders(), text = "Categories Providers")
      ThemeSample(theme = MaterialTheme.colorScheme.CategoriesProcedures(), text = "Categories Procedures")
      ThemeSample(theme = MaterialTheme.colorScheme.CategoriesLifestyle(), text = "Categories Lifestyle")
      ThemeSample(theme = MaterialTheme.colorScheme.CategoriesPlan(), text = "Categories Plan")
    }
  }
}

@Composable
private fun Actions() {
  Column {
    Text(text = "Actions", style = MaterialTheme.typography.headlineLarge)
    FlowRow(modifier = Modifier.padding(vertical = 16.dp)) {
      ThemeSample(theme = MaterialTheme.colorScheme.ActionsSolidBackground(), text = "Actions Solid Background")
      ThemeSample(theme = MaterialTheme.colorScheme.ActionsSolidText(), text = "Actions Solid Text")
      ThemeSample(theme = MaterialTheme.colorScheme.ActionsTonalBackground(), text = "Actions Tonal Background")
      ThemeSample(theme = MaterialTheme.colorScheme.ActionsTonalText(), text = "Actions Tonal Text")
      ThemeSample(theme = MaterialTheme.colorScheme.ActionsGhostText(), text = "Actions Ghost Text")
    }
  }
}

@Composable
private fun States() {
  Column {
    Text(text = "States", style = MaterialTheme.typography.headlineLarge)
    FlowRow(modifier = Modifier.padding(vertical = 16.dp)) {
      ThemeSample(theme = MaterialTheme.colorScheme.StatesInformative(), text = "States Informative")
      ThemeSample(theme = MaterialTheme.colorScheme.StatesPositive(), text = "States Positive")
      ThemeSample(theme = MaterialTheme.colorScheme.StatesWarning(), text = "States Warning")
      ThemeSample(theme = MaterialTheme.colorScheme.StatesCritical(), text = "States Critical")
    }
  }
}

@Composable
private fun ThemeSample(
  theme: Color,
  text: String,
) {
  Box(
    modifier =
      Modifier
        .fillMaxWidth()
        .background(theme)
        .border(0.1.dp, Color(0xFFE6E6E6))
        .padding(2.dp),
  ) {
    Text(
      modifier = Modifier.padding(8.dp),
      text = text,
      style = MaterialTheme.typography.bodyMedium.copy(fontSize = 10.sp),
      color = theme.getTextColor(),
      fontWeight = FontWeight.Bold,
      textAlign = TextAlign.Center,
    )
  }
}

@Composable
private fun Color.getTextColor(): Color =
  if (isSystemInDarkTheme()) {
    if (luminance() <= 0.5f) {
      MaterialTheme.colorScheme.LabelsPrimary()
    } else {
      MaterialTheme.colorScheme
        .LabelsPrimary(!isSystemInDarkTheme())
    }
  } else {
    if (luminance() >= 0.5f) {
      MaterialTheme.colorScheme.LabelsPrimary()
    } else {
      MaterialTheme.colorScheme
        .LabelsPrimary(!isSystemInDarkTheme())
    }
  }

@PreviewLightDark
@Composable
internal fun BackgroundsPreview() {
  MgoTheme {
    Box(modifier = Modifier.padding(16.dp)) {
      Backgrounds()
    }
  }
}

@PreviewLightDark
@Composable
internal fun LabelsPreview() {
  MgoTheme {
    Box(modifier = Modifier.padding(16.dp)) {
      Labels()
    }
  }
}

@PreviewLightDark
@Composable
internal fun SymbolsPreview() {
  MgoTheme {
    Box(modifier = Modifier.padding(16.dp)) {
      Symbols()
    }
  }
}

@PreviewLightDark
@Composable
internal fun CategoriesPreview() {
  MgoTheme {
    Box(modifier = Modifier.padding(16.dp)) {
      Categories()
    }
  }
}

@PreviewLightDark
@Composable
internal fun ActionsPreview() {
  MgoTheme {
    Box(modifier = Modifier.padding(16.dp)) {
      Actions()
    }
  }
}

@PreviewLightDark
@Composable
internal fun StatesPreview() {
  MgoTheme {
    Box(modifier = Modifier.padding(16.dp)) {
      States()
    }
  }
}
