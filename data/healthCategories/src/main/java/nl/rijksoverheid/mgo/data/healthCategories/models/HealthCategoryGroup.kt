package nl.rijksoverheid.mgo.data.healthCategories.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

typealias HealthCategoryStringResource = String
typealias HealthCategoryProfile = String
typealias HealthCategoryId = String

/**
 * Represents a top-level group of medical categories as defined in a shared JSON file.
 *
 * This data model is a direct 1:1 mapping of the JSON structure exchanged between platforms
 * (e.g., Android, iOS, Web). It is not intended for UI usage.
 *
 * @property id Unique identifier for the category group.
 * @property heading Display title of the category group.
 * @property categories List of [HealthCategory] items belonging to this group.
 */
@Serializable
data class HealthCategoryGroup(
  val id: String,
  val heading: HealthCategoryStringResource,
  val categories: List<HealthCategory>,
) {
  /**
   * Represents a single health category within a group of medical categories.
   *
   * @property id Unique identifier for the category.
   * @property heading Heading of the category.
   * @property subheading Subheading of the category.
   * @property subcategories List of [Subcategory] objects associated with this category.
   */
  @Serializable
  @Parcelize
  data class HealthCategory(
    val id: HealthCategoryId,
    val heading: HealthCategoryStringResource,
    val subheading: HealthCategoryStringResource,
    val subcategories: List<Subcategory>,
  ) : Parcelable {
    /**
     * Represents a leaf-level subcategory within a [HealthCategory].
     *
     * @param heading The heading of ths subcategory.
     * @param profiles The profiles of the category. Read more about profiles and how they are used in the README.md of this module.
     */
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
