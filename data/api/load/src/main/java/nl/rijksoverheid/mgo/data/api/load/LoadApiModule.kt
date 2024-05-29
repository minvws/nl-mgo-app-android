package nl.rijksoverheid.mgo.data.api.load

import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import nl.rijksoverheid.mgo.framework.environment.Environment
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named
import javax.inject.Singleton

fun createLoadApi(
    okHttpClient: OkHttpClient,
    baseUrl: String,
): LoadApi {
    val retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(createMoshi()).asLenient())
            .build()
    return retrofit.create(LoadApi::class.java)
}

private fun createMoshi(): Moshi {
    return Moshi.Builder().build()
}

@InstallIn(SingletonComponent::class)
@Module
internal object LoadApiModule {
    @Provides
    @Singleton
    fun provideApi(
        okHttpClient: OkHttpClient,
        @Named("loadApiBaseUrl") baseUrl: String,
    ): LoadApi {
        return createLoadApi(okHttpClient = okHttpClient, baseUrl = baseUrl)
    }

    // TODO Set urls for other environments when available.
    @Provides
    @Singleton
    @Named("loadApiBaseUrl")
    fun provideBaseUrl(environment: Environment): String {
        return when (environment) {
            Environment.Acc -> "https://lo-ad.test.mgo.irealisatie.nl/"
            Environment.Custom -> "https://lo-ad.test.mgo.irealisatie.nl/"
            Environment.Prod -> "https://lo-ad.test.mgo.irealisatie.nl/"
            Environment.Tst -> "https://lo-ad.test.mgo.irealisatie.nl/"
        }
    }
}
