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

fun HealthCareCategory.getRequests(): List<HealthCareRequest> {
    return when (this) {
        HealthCareCategory.MEDICATIONS -> {
            listOf(BGZ_MEDICATION_USE, BGZ_MEDICATION_AGREEMENT, BGZ_ADMINISTRATION_AGREEMENT, GP_MEDICATION_AGREEMENT)
        }
        HealthCareCategory.ALLERGIES -> {
            listOf(BGZ_ALLERGY_INTOLERANCE, GP_ALLERGY_INTOLERANCE)
        }
        HealthCareCategory.COMPLAINTS -> {
            listOf(BGZ_CONCERN)
        }
        HealthCareCategory.ALERTS -> {
            listOf(BGZ_ALERT)
        }
        HealthCareCategory.DEVICES -> {
            listOf(BGZ_MEDICAL_DEVICE)
        }
        HealthCareCategory.LIFESTYLE -> {
            listOf(BGZ_LIVING_SITUATION, BGZ_DRUGS_USE, BGZ_ALCOHOL_USE, BGZ_TABACCO_USE, BGZ_NUTRITION_USE)
        }
        HealthCareCategory.MENTAL -> {
            listOf(BGZ_FUNCTIONAL_OR_MENTAL_STATUS)
        }
        else -> listOf()
    }
}
