package nl.rijksoverheid.mgo.data.api.dva

import com.squareup.moshi.rawType
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

internal class FhirConverterFactory : Converter.Factory() {
    private val fhirMapper = FhirMapper()

    override fun responseBodyConverter(
        listType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit,
    ): Converter<ResponseBody, *> {
        return Converter { responseBody ->
            // Convert our request to domain resources.
            val domainResources = fhirMapper.toDomainResources(fhirResponseJson = responseBody.string())

            // Filter out any domain resources that we do not expect.
            val parameterizedListType = listType as ParameterizedType
            val type = parameterizedListType.actualTypeArguments.first().rawType
            domainResources
                .filter { it.javaClass == type }
        }
    }
}
