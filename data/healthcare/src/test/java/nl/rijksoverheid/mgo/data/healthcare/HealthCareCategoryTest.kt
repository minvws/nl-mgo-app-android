package nl.rijksoverheid.mgo.data.healthcare

import org.junit.Assert.assertEquals
import org.junit.Test
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

class HealthCareCategoryTest {
    @Test
    fun `Given all categories, When calling getTitle, Return correct title resource`() {
        assertEquals(CopyR.string.health_category_medication, HealthCareCategory.MEDICATIONS.getTitle())
        assertEquals(CopyR.string.health_category_allergies, HealthCareCategory.ALLERGIES.getTitle())
        assertEquals(CopyR.string.health_category_measurements, HealthCareCategory.MEASUREMENTS.getTitle())
        assertEquals(CopyR.string.health_category_vaccinations, HealthCareCategory.VACCINATIONS.getTitle())
        assertEquals(CopyR.string.health_category_complaints, HealthCareCategory.COMPLAINTS.getTitle())
        assertEquals(CopyR.string.health_category_treatments, HealthCareCategory.TREATMENTS.getTitle())
        assertEquals(CopyR.string.health_category_reports, HealthCareCategory.REPORTS.getTitle())
        assertEquals(CopyR.string.health_category_documents, HealthCareCategory.DOCUMENTS.getTitle())
    }
}
