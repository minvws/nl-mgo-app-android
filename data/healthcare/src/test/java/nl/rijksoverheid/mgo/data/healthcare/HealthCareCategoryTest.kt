package nl.rijksoverheid.mgo.data.healthcare

import org.junit.Assert.assertEquals
import org.junit.Test
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

class HealthCareCategoryTest {
    @Test
    fun `Given all categories, When calling getTitle, Return correct title resource`() {
        assertEquals(CopyR.string.health_category_medication, HealthCareCategory.MEDICATIONS.getTitle())
        assertEquals(CopyR.string.health_category_measurements, HealthCareCategory.MEASUREMENTS.getTitle())
        assertEquals(CopyR.string.health_category_lab_results, HealthCareCategory.LAB_RESULTS.getTitle())
        assertEquals(CopyR.string.health_category_allergies, HealthCareCategory.ALLERGIES.getTitle())
        assertEquals(CopyR.string.health_category_treatments, HealthCareCategory.TREATMENTS.getTitle())
        assertEquals(CopyR.string.health_category_appointments, HealthCareCategory.APPOINTMENTS.getTitle())
        assertEquals(CopyR.string.health_category_vaccinations, HealthCareCategory.VACCINATIONS.getTitle())
        assertEquals(CopyR.string.health_category_documents, HealthCareCategory.DOCUMENTS.getTitle())
        assertEquals(CopyR.string.health_category_complaints, HealthCareCategory.COMPLAINTS.getTitle())
        assertEquals(CopyR.string.health_category_patient, HealthCareCategory.PATIENT.getTitle())
        assertEquals(CopyR.string.health_category_alerts, HealthCareCategory.ALERTS.getTitle())
        assertEquals(CopyR.string.health_category_payment, HealthCareCategory.PAYMENT.getTitle())
        assertEquals(CopyR.string.health_category_plans, HealthCareCategory.PLANS.getTitle())
        assertEquals(CopyR.string.health_category_devices, HealthCareCategory.DEVICES.getTitle())
        assertEquals(CopyR.string.health_category_mental, HealthCareCategory.MENTAL.getTitle())
        assertEquals(CopyR.string.health_category_lifestyle, HealthCareCategory.LIFESTYLE.getTitle())
    }

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
            mentalRequests
        )

        // Other
        val otherRequests = HealthCareCategory.PAYMENT.getRequests()
        assertEquals(
            listOf<HealthCareRequest>(),
            otherRequests
        )
    }
}
