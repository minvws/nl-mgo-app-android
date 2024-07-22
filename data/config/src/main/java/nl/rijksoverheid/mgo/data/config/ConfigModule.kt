package nl.rijksoverheid.mgo.data.config

import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import nl.rijksoverheid.mgo.data.config.api.ConfigApi
import nl.rijksoverheid.mgo.framework.environment.Environment
import nl.rijksoverheid.mgo.framework.environment.EnvironmentRepository
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named
import javax.inject.Singleton

internal fun createApi(
    okHttpClient: OkHttpClient,
    baseUrl: String,
): ConfigApi {
    val moshi = Moshi.Builder().build()
    val retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi).asLenient())
            .build()
    return retrofit.create(ConfigApi::class.java)
}

@InstallIn(SingletonComponent::class)
@Module
internal object ConfigModule {
    @Provides
    @Singleton
    fun provideConfigRepository(
        environmentRepository: EnvironmentRepository,
        configApi: ConfigApi,
    ): ConfigRepository {
        return DefaultConfigRepository(environmentRepository = environmentRepository, configApi = configApi)
    }

    @Provides
    @Singleton
    fun provideApi(
        okHttpClient: OkHttpClient,
        @Named("configBaseUrl") baseUrl: String,
    ): ConfigApi {
        return createApi(okHttpClient = okHttpClient, baseUrl = baseUrl)
    }

    // TODO Set urls for other environments when available.
    @Provides
    @Singleton
    @Named("configBaseUrl")
    fun provideConfigBaseUrl(environmentRepository: EnvironmentRepository): String {
        return when (val environment = environmentRepository.getEnvironment()) {
            is Environment.Acc -> "https://app-api.test.mgo.irealisatie.nl/"
            is Environment.Prod -> "https://app-api.test.mgo.irealisatie.nl/"
            is Environment.Tst -> "https://app-api.test.mgo.irealisatie.nl/"
            is Environment.Custom -> environment.url
        }
    }

    @Provides
    @Singleton
    @Named("configMoshi")
    fun provideMoshi(): Moshi {
        return Moshi.Builder().build()
    }
}
