package nl.rijksoverheid.mgo.data.healthcare

import org.junit.Assert.assertEquals
import org.junit.Test

class HealthCareCategoryTest {
    @Test
    fun `Given all categories, When calling getRequests, Return correct requests objects()`() {
        // Medications
        val medicationsRequests = HealthCareCategory.MEDICATIONS.getRequests()
        assertEquals(
            listOf(BGZ_MEDICATION_USE, BGZ_MEDICATION_AGREEMENT, BGZ_ADMINISTRATION_AGREEMENT, GP_MEDICATION_AGREEMENT),
            medicationsRequests,
        )

        // Allergies
        val allergiesRequests = HealthCareCategory.ALLERGIES.getRequests()
        assertEquals(
            listOf(BGZ_ALLERGY_INTOLERANCE, GP_ALLERGY_INTOLERANCE),
            allergiesRequests,
        )

        // Concerns
        val complaintsRequests = HealthCareCategory.COMPLAINTS.getRequests()
        assertEquals(
            listOf(BGZ_CONCERN),
            complaintsRequests,
        )

        // Alerts
        val alertsRequests = HealthCareCategory.ALERTS.getRequests()
        assertEquals(
            listOf(BGZ_ALERT),
            alertsRequests,
        )

        // Devices
        val devicesRequests = HealthCareCategory.DEVICES.getRequests()
        assertEquals(
            listOf(BGZ_MEDICAL_DEVICE),
            devicesRequests,
        )

        // Lifestyle
        val lifeStyleRequests = HealthCareCategory.LIFESTYLE.getRequests()
        assertEquals(
            listOf(BGZ_LIVING_SITUATION, BGZ_DRUGS_USE, BGZ_ALCOHOL_USE, BGZ_TABACCO_USE, BGZ_NUTRITION_USE),
            lifeStyleRequests,
        )

        // Mental
        val mentalRequests = HealthCareCategory.MENTAL.getRequests()
        assertEquals(
            listOf(BGZ_FUNCTIONAL_OR_MENTAL_STATUS),
            mentalRequests,
        )

        // Other
        val otherRequests = HealthCareCategory.PAYMENT.getRequests()
        assertEquals(
            listOf<HealthCareRequest>(),
            otherRequests,
        )
    }
}
