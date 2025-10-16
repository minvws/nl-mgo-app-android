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
    "health_cross" -> MaterialTheme.colorScheme.categoriesProblems()
    "allergy" -> MaterialTheme.colorScheme.categoriesAllergies()
    "emergency_home" -> MaterialTheme.colorScheme.categoriesWarnings()
    "syringe" -> MaterialTheme.colorScheme.categoriesVaccinations()
    "nutrition" -> MaterialTheme.colorScheme.categoriesLifestyle()
    "psychology" -> MaterialTheme.colorScheme.categoriesMental()
    "vital_signs" -> MaterialTheme.colorScheme.categoriesVital()
    "labs" -> MaterialTheme.colorScheme.categoriesLaboratory()
    "medical_services" -> MaterialTheme.colorScheme.categoriesProcedures()
    "pill" -> MaterialTheme.colorScheme.categoriesMedication()
    "calendar_today" -> MaterialTheme.colorScheme.categoriesContacts()
    "folder" -> MaterialTheme.colorScheme.categoriesDocuments()
    "patient_list" -> MaterialTheme.colorScheme.categoriesPlan()
    "health_and_safety" -> MaterialTheme.colorScheme.categoriesDevice()
    "person" -> MaterialTheme.colorScheme.categoriesPersonal()
    "account_balance" -> MaterialTheme.colorScheme.categoriesPayer()
    else -> MaterialTheme.colorScheme.categoriesProblems()
  }
