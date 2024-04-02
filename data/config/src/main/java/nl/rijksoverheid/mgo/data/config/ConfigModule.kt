package nl.rijksoverheid.mgo.data.config

import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import nl.rijksoverheid.mgo.data.config.api.ConfigApi
import nl.rijksoverheid.mgo.data.config.datasource.ConfigResponseLocalDataSource
import nl.rijksoverheid.mgo.data.config.datasource.ConfigResponseRemoteDataSource
import nl.rijksoverheid.mgo.data.config.repository.ConfigRepository
import nl.rijksoverheid.mgo.data.config.repository.DefaultConfigRepository
import nl.rijksoverheid.mgo.framework.environment.Environment
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

internal fun createMoshi(): Moshi {
    return Moshi.Builder().build()
}

@InstallIn(SingletonComponent::class)
@Module
internal object ConfigModule {
    @Provides
    fun provideConfigRepository(
        localDataSource: ConfigResponseLocalDataSource,
        remoteDataSource: ConfigResponseRemoteDataSource,
    ): ConfigRepository {
        return DefaultConfigRepository(localDataSource = localDataSource, remoteDataSource = remoteDataSource)
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
    fun provideConfigBaseUrl(environment: Environment): String {
        return when (environment) {
            Environment.Acc -> "https://app-api.test.mgo.irealisatie.nl/"
            Environment.Custom -> "https://app-api.test.mgo.irealisatie.nl/"
            Environment.Prod -> "https://app-api.test.mgo.irealisatie.nl/"
            Environment.Tst -> "https://app-api.test.mgo.irealisatie.nl/"
        }
    }

    @Provides
    @Singleton
    @Named("configMoshi")
    fun provideMoshi(): Moshi {
        return createMoshi()
    }
}
