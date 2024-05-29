package nl.rijksoverheid.mgo.data.api.dva

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import nl.rijksoverheid.mgo.framework.environment.Environment
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal class DvaApiModule {
    @Provides
    @Singleton
    fun provideDvaApi(
        okHttpClient: OkHttpClient,
        @Named("dvaApiBaseUrl") baseUrl: String,
    ): DvaApi {
        return createApi(okHttpClient = okHttpClient, baseUrl = baseUrl)
    }

    // TODO Set urls for other environments when available.
    @Provides
    @Singleton
    @Named("dvaApiBaseUrl")
    fun provideBaseUrl(environment: Environment): String {
        return when (environment) {
            Environment.Acc -> "https://dva.test.mgo.irealisatie.nl/"
            Environment.Custom -> "https://dva.test.mgo.irealisatie.nl/"
            Environment.Prod -> "https://dva.test.mgo.irealisatie.nl/"
            Environment.Tst -> "https://dva.test.mgo.irealisatie.nl/"
        }
    }

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
}
