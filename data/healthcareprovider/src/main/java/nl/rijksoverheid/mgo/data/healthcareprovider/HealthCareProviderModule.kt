package nl.rijksoverheid.mgo.data.healthcareprovider

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import nl.rijksoverheid.mgo.data.healthcareprovider.api.DvaApi
import nl.rijksoverheid.mgo.data.healthcareprovider.api.FhirConverterFactory
import nl.rijksoverheid.mgo.framework.environment.Environment
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal class HealthCareProviderModule {
    private fun createApi(
        okHttpClient: OkHttpClient,
        baseUrl: String,
    ): DvaApi {
        val retrofit =
            Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(FhirConverterFactory())
                .build()
        return retrofit.create(DvaApi::class.java)
    }

    @Provides
    @Singleton
    fun provideDvaRepository(dvaApi: DvaApi): DvaRepository {
        return DefaultDvaRepository(dvaApi)
    }

    @Provides
    @Singleton
    fun provideDvaApi(
        okHttpClient: OkHttpClient,
        @Named("dvaBaseUrl") baseUrl: String,
    ): DvaApi {
        return createApi(okHttpClient = okHttpClient, baseUrl = baseUrl)
    }

    // TODO Set urls for other environments when available.
    @Provides
    @Singleton
    @Named("dvaBaseUrl")
    fun provideDvaBaseUrl(environment: Environment): String {
        return when (environment) {
            Environment.Acc -> "https://dva.test.mgo.irealisatie.nl/"
            Environment.Custom -> "https://dva.test.mgo.irealisatie.nl/"
            Environment.Prod -> "https://dva.test.mgo.irealisatie.nl/"
            Environment.Tst -> "https://dva.test.mgo.irealisatie.nl/"
        }
    }
}
