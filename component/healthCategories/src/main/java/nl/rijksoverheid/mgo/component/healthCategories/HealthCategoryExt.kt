package nl.rijksoverheid.mgo.component.healthCategories

import android.annotation.SuppressLint
import android.content.Context
import androidx.annotation.DrawableRes
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
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
import nl.rijksoverheid.mgo.component.theme.CategoriesVaccinations
import nl.rijksoverheid.mgo.component.theme.CategoriesVitals
import nl.rijksoverheid.mgo.component.theme.CategoriesWarning
import nl.rijksoverheid.mgo.data.healthCategories.models.HealthCategoryIcon
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
fun HealthCategoryIcon.getDrawable() =
  when (this) {
    "health_cross" -> R.drawable.ic_health_cross
    "allergy" -> R.drawable.ic_allergy
    "emergency_home" -> R.drawable.ic_emergency_home
    "syringe" -> R.drawable.ic_syringe
    "nutrition" -> R.drawable.ic_nutrition
    "psychology" -> R.drawable.ic_psychology
    "vital_signs" -> R.drawable.ic_vital_signs
    "labs" -> R.drawable.ic_labs
    "medical_services" -> R.drawable.ic_medical_services
    "pill" -> R.drawable.ic_pill
    "calendar_today" -> R.drawable.ic_calendar_today
    "folder" -> R.drawable.ic_folder
    "patient_list" -> R.drawable.ic_patient_list
    "health_and_safety" -> R.drawable.ic_health_and_safety
    "person" -> R.drawable.ic_person
    "account_balance" -> R.drawable.ic_account_balance
    else -> R.drawable.ic_health_cross
  }

@Composable
fun HealthCategoryIcon.getColor() =
  when (this) {
    "health_cross" -> MaterialTheme.colorScheme.CategoriesProblems()
    "allergy" -> MaterialTheme.colorScheme.CategoriesAllergies()
    "emergency_home" -> MaterialTheme.colorScheme.CategoriesWarning()
    "syringe" -> MaterialTheme.colorScheme.CategoriesVaccinations()
    "nutrition" -> MaterialTheme.colorScheme.CategoriesLifestyle()
    "psychology" -> MaterialTheme.colorScheme.CategoriesMental()
    "vital_signs" -> MaterialTheme.colorScheme.CategoriesVitals()
    "labs" -> MaterialTheme.colorScheme.CategoriesLaboratory()
    "medical_services" -> MaterialTheme.colorScheme.CategoriesProcedures()
    "pill" -> MaterialTheme.colorScheme.CategoriesMedication()
    "calendar_today" -> MaterialTheme.colorScheme.CategoriesContacts()
    "folder" -> MaterialTheme.colorScheme.CategoriesDocuments()
    "patient_list" -> MaterialTheme.colorScheme.CategoriesPlan()
    "health_and_safety" -> MaterialTheme.colorScheme.CategoriesDevice()
    "person" -> MaterialTheme.colorScheme.CategoriesAdministration()
    "account_balance" -> MaterialTheme.colorScheme.CategoriesProviders()
    else -> MaterialTheme.colorScheme.CategoriesProblems()
  }
