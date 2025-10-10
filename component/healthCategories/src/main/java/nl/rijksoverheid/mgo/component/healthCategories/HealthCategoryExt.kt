package nl.rijksoverheid.mgo.component.healthCategories

import android.annotation.SuppressLint
import android.content.Context
import androidx.annotation.DrawableRes
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import nl.rijksoverheid.mgo.component.theme.categoriesAllergies
import nl.rijksoverheid.mgo.component.theme.categoriesContacts
import nl.rijksoverheid.mgo.component.theme.categoriesDevice
import nl.rijksoverheid.mgo.component.theme.categoriesDocuments
import nl.rijksoverheid.mgo.component.theme.categoriesLaboratory
import nl.rijksoverheid.mgo.component.theme.categoriesLifestyle
import nl.rijksoverheid.mgo.component.theme.categoriesMedication
import nl.rijksoverheid.mgo.component.theme.categoriesMental
import nl.rijksoverheid.mgo.component.theme.categoriesPayer
import nl.rijksoverheid.mgo.component.theme.categoriesPersonal
import nl.rijksoverheid.mgo.component.theme.categoriesPlan
import nl.rijksoverheid.mgo.component.theme.categoriesProblems
import nl.rijksoverheid.mgo.component.theme.categoriesProcedures
import nl.rijksoverheid.mgo.component.theme.categoriesVaccinations
import nl.rijksoverheid.mgo.component.theme.categoriesVital
import nl.rijksoverheid.mgo.component.theme.categoriesWarnings
import nl.rijksoverheid.mgo.data.healthCategories.models.HealthCategoryGroup
import nl.rijksoverheid.mgo.data.healthCategories.models.HealthCategoryStringResource

@SuppressLint("DiscouragedApi")
fun Context.getString(stringResource: HealthCategoryStringResource): String {
  // We dynamically get the string based on a key that exists in a JSON file.
  // If we do it like that, R8 will shrink all the strings in the strings.xml file that exist in that JSON file, since they appear unused.
  // To solve this, the prefix needs to exist in the code so that the shrinking knows these strings do not need to be removed.
  val stringResourceName =
    when {
      stringResource.startsWith("mhc") -> "mhc_" + stringResource.drop(4)
      stringResource.startsWith("hc") -> "hc_" + stringResource.drop(3)
      stringResource.startsWith("zib") -> "zib_" + stringResource.drop(4)
      else -> stringResource
    }
  val stringResourceWithPrefix = resources.getIdentifier(stringResourceName, "string", packageName)
  return try {
    getString(stringResourceWithPrefix)
  } catch (e: Exception) {
    ""
  }
}

@DrawableRes
fun HealthCategoryGroup.HealthCategory.getIcon() =
  when (this.id) {
    "problems" -> R.drawable.ic_health_cross
    "allergies" -> R.drawable.ic_allergy
    "alerts" -> R.drawable.ic_emergency_home
    "vaccinations" -> R.drawable.ic_syringe
    "lifestyle" -> R.drawable.ic_nutrition
    "mental_wellbeing" -> R.drawable.ic_psychology
    "measurements" -> R.drawable.ic_vital_signs
    "lab_results" -> R.drawable.ic_labs
    "treatments" -> R.drawable.ic_medical_services
    "medication" -> R.drawable.ic_pill
    "appointments" -> R.drawable.ic_calendar_today
    "documents" -> R.drawable.ic_folder
    "plans" -> R.drawable.ic_patient_list
    "medical_devices" -> R.drawable.ic_health_and_safety
    "patient" -> R.drawable.ic_person
    "payment" -> R.drawable.ic_account_balance
    else -> R.drawable.ic_health_cross
  }

@Composable
fun HealthCategoryGroup.HealthCategory.getIconColor() =
  when (this.id) {
    "problems" -> MaterialTheme.colorScheme.categoriesProblems()
    "allergies" -> MaterialTheme.colorScheme.categoriesAllergies()
    "alerts" -> MaterialTheme.colorScheme.categoriesWarnings()
    "vaccinations" -> MaterialTheme.colorScheme.categoriesVaccinations()
    "lifestyle" -> MaterialTheme.colorScheme.categoriesLifestyle()
    "mental_wellbeing" -> MaterialTheme.colorScheme.categoriesMental()
    "measurements" -> MaterialTheme.colorScheme.categoriesVital()
    "lab_results" -> MaterialTheme.colorScheme.categoriesLaboratory()
    "treatments" -> MaterialTheme.colorScheme.categoriesProcedures()
    "medication" -> MaterialTheme.colorScheme.categoriesMedication()
    "appointments" -> MaterialTheme.colorScheme.categoriesContacts()
    "documents" -> MaterialTheme.colorScheme.categoriesDocuments()
    "plans" -> MaterialTheme.colorScheme.categoriesPlan()
    "medical_devices" -> MaterialTheme.colorScheme.categoriesDevice()
    "patient" -> MaterialTheme.colorScheme.categoriesPersonal()
    "payment" -> MaterialTheme.colorScheme.categoriesPayer()
    else -> MaterialTheme.colorScheme.categoriesProblems()
  }
