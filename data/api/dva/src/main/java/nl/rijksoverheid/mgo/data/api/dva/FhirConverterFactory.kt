package nl.rijksoverheid.mgo.data.api.dva

import ca.uhn.fhir.context.FhirContext
import com.squareup.moshi.rawType
import okhttp3.ResponseBody
import org.hl7.fhir.dstu3.model.Bundle
import org.hl7.fhir.dstu3.model.DomainResource
import retrofit2.Converter
import retrofit2.Retrofit
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
            val parameterizedListType = listType as ParameterizedType
            val type = parameterizedListType.actualTypeArguments.first().rawType

            // Parse the bundle
            val bundle = parser.parseResource(Bundle::class.java, responseBody.string())

            // Get the domain resources from the bundle entries
            val domainResources =
                bundle.entry.map { entryComponent ->
                    entryComponent.resource as DomainResource
                }

            // Filter out any domain resources that we don't expect
            domainResources
                .filter { it.javaClass == type }
        }
    }
}
