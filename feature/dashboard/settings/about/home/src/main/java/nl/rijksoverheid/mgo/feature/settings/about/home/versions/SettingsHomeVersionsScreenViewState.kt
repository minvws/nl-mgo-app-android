package nl.rijksoverheid.mgo.feature.settings.about.home.versions

data class SettingsHomeVersionsScreenViewState(
  val hcimPackageVersion: String?,
  val hcimPackageDate: String?,
  val hcimPackageGitRef: String?,
  val healthCategoriesConfigVersion: String?,
  val healthCategoriesConfigDate: String?,
  val healthCategoriesConfigGitRef: String?,
  val patientFriendlyTermsETag: String?,
)
