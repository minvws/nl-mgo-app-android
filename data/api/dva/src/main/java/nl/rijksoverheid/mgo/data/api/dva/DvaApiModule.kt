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

fun createDvaApi(
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

@InstallIn(SingletonComponent::class)
@Module
internal class DvaApiModule {
    @Provides
    @Singleton
    fun provideDvaApi(
        okHttpClient: OkHttpClient,
        @Named("dvaApiBaseUrl") baseUrl: String,
    ): DvaApi {
        return createDvaApi(okHttpClient = okHttpClient, baseUrl = baseUrl)
    }

    // TODO Set urls for other environments when available.
    @Provides
    @Singleton
    @Named("dvaApiBaseUrl")
    fun provideBaseUrl(environment: Environment): String {
        return when (environment) {
            is Environment.Acc -> "https://dva.test.mgo.irealisatie.nl/"
            is Environment.Prod -> "https://dva.test.mgo.irealisatie.nl/"
            is Environment.Tst -> "https://dva.test.mgo.irealisatie.nl/"
            is Environment.Custom -> environment.url
        }
    }
}
