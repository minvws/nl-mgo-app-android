package nl.rijksoverheid.mgo.data.healthcare

import androidx.annotation.StringRes
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

enum class HealthCareCategory {
    MEDICATIONS,
    ALLERGIES,
    MEASUREMENTS,
    VACCINATIONS,
    COMPLAINTS,
    TREATMENTS,
    REPORTS,
    DOCUMENTS,
}

@StringRes
fun HealthCareCategory.getTitle(): Int {
    return when (this) {
        HealthCareCategory.MEDICATIONS -> CopyR.string.health_category_medication
        HealthCareCategory.ALLERGIES -> CopyR.string.health_category_allergies
        HealthCareCategory.MEASUREMENTS -> CopyR.string.health_category_measurements
        HealthCareCategory.VACCINATIONS -> CopyR.string.health_category_vaccinations
        HealthCareCategory.COMPLAINTS -> CopyR.string.health_category_complaints
        HealthCareCategory.TREATMENTS -> CopyR.string.health_category_treatments
        HealthCareCategory.REPORTS -> CopyR.string.health_category_reports
        HealthCareCategory.DOCUMENTS -> CopyR.string.health_category_documents
    }
}
