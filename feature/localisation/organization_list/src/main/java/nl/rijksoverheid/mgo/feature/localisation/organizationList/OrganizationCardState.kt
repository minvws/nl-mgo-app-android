package nl.rijksoverheid.mgo.feature.localisation.organizationList

import nl.rijksoverheid.mgo.component.organization.MgoOrganization
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

internal fun MgoOrganization.getCardState(): OrganizationSearchCardState =
  when {
    dataServices.any { !it.isSupported } -> OrganizationSearchCardState.NOT_SUPPORTED
    dataServices.isEmpty() -> OrganizationSearchCardState.NOT_SUPPORTED
    added -> OrganizationSearchCardState.ADDED
    else -> OrganizationSearchCardState.ADD
  }
