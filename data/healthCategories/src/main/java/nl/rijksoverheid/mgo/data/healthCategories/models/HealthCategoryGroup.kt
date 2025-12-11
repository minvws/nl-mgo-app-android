package nl.rijksoverheid.mgo.data.healthCategories.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

typealias HealthCategoryStringResource = String
typealias HealthCategoryProfile = String
typealias HealthCategoryId = String
typealias HealthCategoryIcon = String

@Serializable
data class HealthCategoryGroup(
  val id: String,
  val heading: HealthCategoryStringResource,
  val categories: List<HealthCategory>,
) {
  @Serializable
  @Parcelize
  data class HealthCategory(
    val id: HealthCategoryId,
    val icon: HealthCategoryIcon,
    val heading: HealthCategoryStringResource,
    val subheading: HealthCategoryStringResource,
    val subcategories: List<Subcategory>,
  ) : Parcelable {
    @Serializable
    @Parcelize
    data class Subcategory(
      val heading: HealthCategoryStringResource,
      val profiles: List<String>,
    ) : Parcelable
  }
}

val TEST_HEALTH_CATEGORY_PROBLEMS =
  HealthCategoryGroup.HealthCategory(
    id = "problems",
    icon = "health_cross",
    heading = "hc_problems.heading",
    subheading = "hc_problems.subheading",
    subcategories =
      listOf(
        HealthCategoryGroup.HealthCategory.Subcategory(
          heading = "zib_problem.heading",
          profiles = listOf("http://nictiz.nl/fhir/StructureDefinition/zib-Problem"),
        ),
      ),
  )

val TEST_HEALTH_CATEGORY_ALLERGIES =
  HealthCategoryGroup.HealthCategory(
    id = "allergies",
    icon = "allergy",
    heading = "hc_allergies.heading",
    subheading = "hc_allergies.subheading",
    subcategories =
      listOf(
        HealthCategoryGroup.HealthCategory.Subcategory(
          heading = "zib_allergy_intolerance.heading",
          profiles = listOf("http://nictiz.nl/fhir/StructureDefinition/zib-AllergyIntolerance"),
        ),
      ),
  )

val TEST_HEALTH_CATEGORY_LIFESTYLE =
  HealthCategoryGroup.HealthCategory(
    id = "lifestyle",
    icon = "nutrition",
    heading = "hc_lifestyle.heading",
    subheading = "hc_lifestyle.subheading",
    subcategories =
      listOf(
        HealthCategoryGroup.HealthCategory.Subcategory(
          heading = "zib_living_situation.heading",
          profiles = listOf("http://nictiz.nl/fhir/StructureDefinition/zib-LivingSituation"),
        ),
        HealthCategoryGroup.HealthCategory.Subcategory(
          heading = "zib_drug_use.heading",
          profiles = listOf("http://nictiz.nl/fhir/StructureDefinition/zib-DrugUse"),
        ),
        HealthCategoryGroup.HealthCategory.Subcategory(
          heading = "zib_alcohol_use.heading",
          profiles = listOf("http://nictiz.nl/fhir/StructureDefinition/zib-AlcoholUse"),
        ),
        HealthCategoryGroup.HealthCategory.Subcategory(
          heading = "zib_tobacco_use.heading",
          profiles = listOf("http://nictiz.nl/fhir/StructureDefinition/zib-TobaccoUse"),
        ),
        HealthCategoryGroup.HealthCategory.Subcategory(
          heading = "zib_nutrition_advice.heading",
          profiles = listOf("http://nictiz.nl/fhir/StructureDefinition/zib-NutritionAdvice"),
        ),
      ),
  )

val TEST_HEALTH_CATEGORY_MEDICATION =
  HealthCategoryGroup.HealthCategory(
    id = "medication",
    icon = "pill",
    heading = "hc_medication.heading",
    subheading = "hc_medication.subheading",
    subcategories =
      listOf(
        HealthCategoryGroup.HealthCategory.Subcategory(
          heading = "zib_medication_use.heading",
          profiles = listOf("http://nictiz.nl/fhir/StructureDefinition/zib-MedicationUse"),
        ),
        HealthCategoryGroup.HealthCategory.Subcategory(
          heading = "zib_medication_agreement.heading",
          profiles = listOf("http://nictiz.nl/fhir/StructureDefinition/zib-MedicationAgreement"),
        ),
        HealthCategoryGroup.HealthCategory.Subcategory(
          heading = "zib_administration_agreement.heading",
          profiles = listOf("http://nictiz.nl/fhir/StructureDefinition/zib-AdministrationAgreement"),
        ),
      ),
  )

val TEST_HEALTH_CATEGORY_GROUP_HEALTH =
  HealthCategoryGroup(
    id = "health",
    heading = "mhc_health.heading",
    categories =
      listOf(
        TEST_HEALTH_CATEGORY_PROBLEMS,
        TEST_HEALTH_CATEGORY_ALLERGIES,
      ),
  )
