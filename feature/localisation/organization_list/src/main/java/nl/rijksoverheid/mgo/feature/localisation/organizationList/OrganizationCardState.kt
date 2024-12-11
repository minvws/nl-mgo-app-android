package nl.rijksoverheid.mgo.feature.localisation.organizationList

import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganizationDataServiceType

enum class OrganizationSearchCardState {
    ADD,
    ADDED,
    NOT_SUPPORTED,
}

fun MgoOrganization.getCardState(): OrganizationSearchCardState {
    return when {
        !containsBgz() && !containsGp() -> OrganizationSearchCardState.NOT_SUPPORTED
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
