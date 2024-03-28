package nl.rijksoverheid.mgo.data.config

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

@InstallIn(SingletonComponent::class)
@Module
internal object ConfigModule {
    @Provides
    fun provideConfigRepository(configApi: ConfigApi): ConfigRepository {
        return DefaultConfigRepository(configApi = configApi)
    }

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): ConfigApi {
        return retrofit.create(ConfigApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        @Named("configBaseUrl") configBaseUrl: String,
    ): Retrofit {
        val moshi = Moshi.Builder().build()
        return Retrofit.Builder()
            .baseUrl(configBaseUrl)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi).asLenient())
            .build()
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
}
