package nl.rijksoverheid.mgo.data.localisation.models

import com.squareup.moshi.JsonClass
import dev.zacsweers.moshix.sealed.annotations.TypeLabel

@JsonClass(generateAdapter = true, generator = "sealed:type")
sealed class MgoOrganizationDataService(open val resourceEndpoint: String) {
    @TypeLabel("bgz")
    @JsonClass(generateAdapter = true)
    data class Bgz(override val resourceEndpoint: String) : MgoOrganizationDataService(resourceEndpoint)

    @TypeLabel("gp")
    @JsonClass(generateAdapter = true)
    data class Gp(override val resourceEndpoint: String) : MgoOrganizationDataService(resourceEndpoint)
}
