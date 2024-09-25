package nl.rijksoverheid.mgo.data.healthcare

import androidx.annotation.StringRes
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

enum class HealthCareCategory {
    MEDICATIONS,
    MEASUREMENTS,
    LAB_RESULTS,
    ALLERGIES,
    TREATMENTS,
    APPOINTMENTS,
    VACCINATIONS,
    DOCUMENTS,
    COMPLAINTS,
    PATIENT,
    ALERTS,
    PAYMENT,
    PLANS,
    DEVICES,
    MENTAL,
    LIFESTYLE,
}

@StringRes
fun HealthCareCategory.getTitle(): Int {
    return when (this) {
        HealthCareCategory.MEDICATIONS -> CopyR.string.health_category_medication
        HealthCareCategory.MEASUREMENTS -> CopyR.string.health_category_measurements
        HealthCareCategory.LAB_RESULTS -> CopyR.string.health_category_lab_results
        HealthCareCategory.ALLERGIES -> CopyR.string.health_category_allergies
        HealthCareCategory.TREATMENTS -> CopyR.string.health_category_treatments
        HealthCareCategory.APPOINTMENTS -> CopyR.string.health_category_appointments
        HealthCareCategory.VACCINATIONS -> CopyR.string.health_category_vaccinations
        HealthCareCategory.DOCUMENTS -> CopyR.string.health_category_documents
        HealthCareCategory.COMPLAINTS -> CopyR.string.health_category_complaints
        HealthCareCategory.PATIENT -> CopyR.string.health_category_patient
        HealthCareCategory.ALERTS -> CopyR.string.health_category_alerts
        HealthCareCategory.PAYMENT -> CopyR.string.health_category_payment
        HealthCareCategory.PLANS -> CopyR.string.health_category_plans
        HealthCareCategory.DEVICES -> CopyR.string.health_category_devices
        HealthCareCategory.MENTAL -> CopyR.string.health_category_mental
        HealthCareCategory.LIFESTYLE -> CopyR.string.health_category_lifestyle
    }
}
