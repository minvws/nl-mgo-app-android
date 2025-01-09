package nl.rijksoverheid.mgo.data.config

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import nl.rijksoverheid.mgo.data.config.api.ConfigApi
import nl.rijksoverheid.mgo.framework.environment.Environment
import nl.rijksoverheid.mgo.framework.environment.EnvironmentRepository
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Named
import javax.inject.Singleton
import kotlinx.serialization.json.Json

internal fun createApi(
    okHttpClient: OkHttpClient,
    baseUrl: String,
): ConfigApi {
    val json = Json { ignoreUnknownKeys = true }
    val retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(
                json.asConverterFactory(
                    "application/json; charset=UTF8".toMediaType(),
                ),
            )
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
            is Environment.Demo -> "https://app-api.test.mgo.irealisatie.nl/"
            is Environment.Custom -> environment.url
        }
    }
}
