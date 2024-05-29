package nl.rijksoverheid.mgo.data.api.dva

import ca.uhn.fhir.context.FhirContext
import com.squareup.moshi.rawType
import okhttp3.ResponseBody
import org.hl7.fhir.dstu3.model.Bundle
import org.hl7.fhir.dstu3.model.DomainResource
import org.hl7.fhir.dstu3.model.MedicationStatement
import org.hl7.fhir.dstu3.model.ResourceType
import retrofit2.Converter
import retrofit2.Retrofit
import timber.log.Timber
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * A converter factory for retrofit to parse FHIR models [https://github.com/hapifhir/hapi-fhir].
 * It currently only supports:
 *     - Bundle resource type as response.
 *     - The retrofit api function call must return a List<DomainResource>.
 * It will map the entries found in the bundle to the correct domain resource.
 */
internal class FhirConverterFactory : Converter.Factory() {
    private val context = FhirContext.forDstu3()
    private val parser = context.newJsonParser()

    override fun responseBodyConverter(
        listType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit,
    ): Converter<ResponseBody, *> {
        return Converter { responseBody ->
            // We only support lists.
            if (listType !is ParameterizedType) {
                error("FhirConverterFactory only supports lists as a return type.")
            }

            // We only support DomainResource in that list.
            val type = listType.actualTypeArguments.first().rawType
            val isDomainResource = DomainResource::class.java.isAssignableFrom(type)
            if (!isDomainResource) {
                error("FhirConverterFactory only supports DomainResource.")
            }

            // Parse the bundle
            val bundle = parser.parseResource(Bundle::class.java, responseBody.string())

            // Get the domain resources from the bundle entries
            val domainResources =
                bundle.entry.map { entryComponent ->
                    entryComponent.toDomainResource()
                }

            // Filter out any domain resources that we don't expect
            domainResources
                .filterNotNull()
                .filter { it.javaClass == type }
        }
    }

    private fun Bundle.BundleEntryComponent.toDomainResource(): DomainResource? {
        return when (resource.resourceType) {
            ResourceType.MedicationStatement -> {
                resource as MedicationStatement
            }

            else -> {
                Timber.e(
                    "Cannot map resource ${resource.resourceType}, not supported. " +
                        "To support add mapping in FhirConverterFactory",
                )
                null
            }
        }
    }
}
