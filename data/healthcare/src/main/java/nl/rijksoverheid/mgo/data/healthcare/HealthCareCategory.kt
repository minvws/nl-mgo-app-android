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
            listOf(BGZ.MEDICATION_USE.request)
        }

        else -> listOf()
    }
}
