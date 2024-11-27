package nl.rijksoverheid.mgo.feature.localisation.organizationSearch

import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganizationDataServiceType

enum class OrganizationSearchCardState {
    ADD,
    ADDED,
    NOT_SUPPORTED,
}

fun MgoOrganization.getCardState(): OrganizationSearchCardState {
    return when {
        added -> OrganizationSearchCardState.ADDED
        dataServices.isEmpty() -> OrganizationSearchCardState.NOT_SUPPORTED
        !containsBgz() && !containsGp() -> OrganizationSearchCardState.NOT_SUPPORTED
        else -> OrganizationSearchCardState.ADD
    }
}

private fun MgoOrganization.containsBgz(): Boolean {
    return dataServices.map { it.type }.contains(MgoOrganizationDataServiceType.BGZ)
}

private fun MgoOrganization.containsGp(): Boolean {
    return dataServices.map { it.type }.contains(MgoOrganizationDataServiceType.GP)
}
