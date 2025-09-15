package nl.rijksoverheid.mgo.component.healthCareCategory

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import getStringResourceByName
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
import nl.rijksoverheid.mgo.component.theme.supportTreatment
import nl.rijksoverheid.mgo.component.theme.supportVaccinations
import nl.rijksoverheid.mgo.component.theme.supportVitals
import nl.rijksoverheid.mgo.component.theme.supportWarning
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.category.HealthCareCategoryId
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

@Composable
@StringRes
fun HealthCareCategoryId.getTitle(): Int {
  val stringResource = LocalContext.current.getStringResourceByName("hc_$id.heading")
  if (stringResource == 0) {
    return CopyR.string.common_unknown
  }
  return stringResource
}

@DrawableRes
fun HealthCareCategoryId.getIcon(): Int =
  when (this) {
    HealthCareCategoryId.MEDICATIONS -> R.drawable.ic_medication
    HealthCareCategoryId.MEASUREMENTS -> R.drawable.ic_measurements
    HealthCareCategoryId.LAB_RESULTS -> R.drawable.ic_labresults
    HealthCareCategoryId.ALLERGIES -> R.drawable.ic_allergies
    HealthCareCategoryId.TREATMENTS -> R.drawable.ic_treatments
    HealthCareCategoryId.APPOINTMENTS -> R.drawable.ic_appointments
    HealthCareCategoryId.VACCINATIONS -> R.drawable.ic_vaccinations
    HealthCareCategoryId.DOCUMENTS -> R.drawable.ic_documents
    HealthCareCategoryId.COMPLAINTS -> R.drawable.ic_complaints
    HealthCareCategoryId.PATIENT -> R.drawable.ic_patient
    HealthCareCategoryId.ALERTS -> R.drawable.ic_alerts
    HealthCareCategoryId.PAYMENT -> R.drawable.ic_payment
    HealthCareCategoryId.PLANS -> R.drawable.ic_plans
    HealthCareCategoryId.DEVICES -> R.drawable.ic_devices
    HealthCareCategoryId.MENTAL -> R.drawable.ic_mental
    HealthCareCategoryId.LIFESTYLE -> R.drawable.ic_lifestyle
  }

@Composable
fun HealthCareCategoryId.getIconColor(): Color =
  when (this) {
    HealthCareCategoryId.MEDICATIONS -> MaterialTheme.colorScheme.supportMedication()
    HealthCareCategoryId.MEASUREMENTS -> MaterialTheme.colorScheme.supportVitals()
    HealthCareCategoryId.LAB_RESULTS -> MaterialTheme.colorScheme.supportLaboratory()
    HealthCareCategoryId.ALLERGIES -> MaterialTheme.colorScheme.supportAllergies()
    HealthCareCategoryId.TREATMENTS -> MaterialTheme.colorScheme.supportTreatment()
    HealthCareCategoryId.APPOINTMENTS -> MaterialTheme.colorScheme.supportContacts()
    HealthCareCategoryId.VACCINATIONS -> MaterialTheme.colorScheme.supportVaccinations()
    HealthCareCategoryId.DOCUMENTS -> MaterialTheme.colorScheme.supportDocuments()
    HealthCareCategoryId.COMPLAINTS -> MaterialTheme.colorScheme.supportProblems()
    HealthCareCategoryId.PATIENT -> MaterialTheme.colorScheme.supportPersonal()
    HealthCareCategoryId.ALERTS -> MaterialTheme.colorScheme.supportWarning()
    HealthCareCategoryId.PAYMENT -> MaterialTheme.colorScheme.supportPayer()
    HealthCareCategoryId.PLANS -> MaterialTheme.colorScheme.supportProcedures()
    HealthCareCategoryId.DEVICES -> MaterialTheme.colorScheme.supportDevice()
    HealthCareCategoryId.MENTAL -> MaterialTheme.colorScheme.supportFunctional()
    HealthCareCategoryId.LIFESTYLE -> MaterialTheme.colorScheme.supportLifestyle()
  }
