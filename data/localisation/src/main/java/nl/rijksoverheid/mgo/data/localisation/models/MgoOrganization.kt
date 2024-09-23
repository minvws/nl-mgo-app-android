package nl.rijksoverheid.mgo.data.localisation.models

import com.squareup.moshi.JsonClass
import nl.rijksoverheid.mgo.data.api.load.DATA_SERVICE_BGZ
import nl.rijksoverheid.mgo.data.api.load.DATA_SERVICE_GP
import nl.rijksoverheid.mgo.data.api.load.SearchResponse

@JsonClass(generateAdapter = true)
data class MgoOrganization(
    val id: String,
    val name: String,
    val address: String?,
    val category: String?,
    val added: Boolean,
    val dataServices: List<MgoOrganizationDataService>,
)

val TEST_MGO_ORGANIZATION =
    MgoOrganization(
        id = "1",
        name = "Tandarts Tandje Erbij",
        address = "Boorplatform 5\r\n1234AB Roermond",
        category = "Tandarts",
        added = false,
        dataServices = listOf(),
    )

internal fun SearchResponse.Organization.toMgoOrganization(added: Boolean): MgoOrganization {
    return MgoOrganization(
        id = id,
        name = displayName ?: "",
        address = addresses.firstOrNull()?.address,
        category = types.firstOrNull()?.displayName,
        added = added,
        dataServices =
            dataServices.mapNotNull { dataService ->
                when (dataService.id) {
                    DATA_SERVICE_BGZ -> MgoOrganizationDataService.Bgz(dataService.roles.first().resourceEndpoint)
                    DATA_SERVICE_GP -> MgoOrganizationDataService.Gp(dataService.roles.first().resourceEndpoint)
                    else -> null
                }
            },
    )
}
