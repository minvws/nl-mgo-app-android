package nl.rijksoverheid.mgo.data.healthcare

enum class HealthCareCategory(val id: String) {
    MEDICATIONS("medication"),
    MEASUREMENTS("measurements"),
    LAB_RESULTS("lab_results"),
    ALLERGIES("allergies"),
    TREATMENTS("treatments"),
    APPOINTMENTS("appointments"),
    VACCINATIONS("vaccinations"),
    DOCUMENTS("documents"),
    COMPLAINTS("complaints"),
    PATIENT("patient"),
    ALERTS("alerts"),
    PAYMENT("payment"),
    PLANS("plans"),
    DEVICES("devices"),
    MENTAL("mental"),
    LIFESTYLE("lifestyle"),
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
        HealthCareCategory.DOCUMENTS -> {
            listOf(DOCUMENT_REFERENCE)
        }
        else -> listOf()
    }
}
