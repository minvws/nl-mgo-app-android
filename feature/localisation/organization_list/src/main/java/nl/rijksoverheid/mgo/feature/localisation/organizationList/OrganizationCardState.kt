package nl.rijksoverheid.mgo.feature.localisation.organizationList

import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganizationDataServiceType
import nl.rijksoverheid.mgo.feature.localisation.organizationList.manual.OrganizationListManualCard

/**
 * Represents the state of the [OrganizationListManualCard].
 */
internal enum class OrganizationSearchCardState {
  /**
   * Represents the state that the organization be added.
   */
  ADD,

  /**
   * Represents the state that the organization is added.
   */
  ADDED,

  /**
   * Represents the state that the organization is not supported.
   */
  NOT_SUPPORTED,
}

internal fun MgoOrganization.getCardState(): OrganizationSearchCardState {
  return when {
    !containsBgz() && !containsGp() && !containsDocuments() && !containsVaccination() -> OrganizationSearchCardState.NOT_SUPPORTED
    dataServices.isEmpty() -> OrganizationSearchCardState.NOT_SUPPORTED
    added -> OrganizationSearchCardState.ADDED
    else -> OrganizationSearchCardState.ADD
  }
}

private fun MgoOrganization.containsBgz(): Boolean {
  return dataServices.map { it.type }.contains(MgoOrganizationDataServiceType.BGZ)
}

private fun MgoOrganization.containsGp(): Boolean {
  return dataServices.map { it.type }.contains(MgoOrganizationDataServiceType.GP)
}

private fun MgoOrganization.containsDocuments(): Boolean {
  return dataServices.map { it.type }.contains(MgoOrganizationDataServiceType.DOCUMENTS)
}

private fun MgoOrganization.containsVaccination(): Boolean {
  return dataServices.map { it.type }.contains(MgoOrganizationDataServiceType.VACCINATION)
}
