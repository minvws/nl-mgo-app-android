package nl.rijksoverheid.mgo.data.api.dva

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import nl.nl.rijksoverheid.mgo.framework.network.BasicAuthInterceptor
import nl.nl.rijksoverheid.mgo.framework.network.auth.MgoAuthentication
import nl.rijksoverheid.mgo.framework.environment.Environment
import nl.rijksoverheid.mgo.framework.environment.EnvironmentRepository
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
        mgoAuthentication: MgoAuthentication,
        @Named("dvaApiBaseUrl") baseUrl: String,
    ): DvaApi {
        val okHttpClientBuilder = okHttpClient.newBuilder()
        if (mgoAuthentication is MgoAuthentication.Basic) {
            okHttpClientBuilder.addInterceptor(BasicAuthInterceptor(user = mgoAuthentication.user, password = mgoAuthentication.password))
        }
        val okHttpClientWithAuth = okHttpClientBuilder.build()
        return createDvaApi(okHttpClient = okHttpClientWithAuth, baseUrl = baseUrl)
    }

    // TODO Set urls for other environments when available.
    @Provides
    @Singleton
    @Named("dvaApiBaseUrl")
    fun provideBaseUrl(environmentRepository: EnvironmentRepository): String {
        return when (val environment = environmentRepository.getEnvironment()) {
            is Environment.Acc -> "https://dva.acc.mgo.irealisatie.nl/"
            is Environment.Prod -> "https://dva.acc.mgo.irealisatie.nl/"
            is Environment.Tst -> "https://dva.test.mgo.irealisatie.nl/"
            is Environment.Custom -> environment.url
        }
    }
}
