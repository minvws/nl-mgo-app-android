package nl.rijksoverheid.mgo.data.localisation

import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import nl.rijksoverheid.mgo.data.localisation.api.SearchApi
import nl.rijksoverheid.mgo.framework.environment.Environment
import nl.rijksoverheid.mgo.framework.storage.file.FileStore
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named
import javax.inject.Singleton

internal fun createApi(
    okHttpClient: OkHttpClient,
    baseUrl: String,
): SearchApi {
    val moshi = Moshi.Builder().build()
    val retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi).asLenient())
            .build()
    return retrofit.create(SearchApi::class.java)
}

internal fun createMoshi(): Moshi {
    return Moshi.Builder().build()
}

@InstallIn(SingletonComponent::class)
@Module
internal object SearchModule {
    @Provides
    @Singleton
    fun provideSearchRepository(
        searchApi: SearchApi,
        fileStore: FileStore,
    ): HealthCareProviderRepository {
        return DefaultHealthCareProviderRepository(searchApi = searchApi, fileStore = fileStore)
    }

    @Provides
    @Singleton
    fun provideApi(
        okHttpClient: OkHttpClient,
        @Named("searchBaseUrl") baseUrl: String,
    ): SearchApi {
        return createApi(okHttpClient = okHttpClient, baseUrl = baseUrl)
    }

    // TODO Set urls for other environments when available.
    @Provides
    @Singleton
    @Named("searchBaseUrl")
    fun provideConfigBaseUrl(environment: Environment): String {
        return when (environment) {
            Environment.Acc -> "https://lo-ad.test.mgo.irealisatie.nl/"
            Environment.Custom -> "https://lo-ad.test.mgo.irealisatie.nl/"
            Environment.Prod -> "https://lo-ad.test.mgo.irealisatie.nl/"
            Environment.Tst -> "https://lo-ad.test.mgo.irealisatie.nl/"
        }
    }

    @Provides
    @Singleton
    @Named("configMoshi")
    fun provideMoshi(): Moshi {
        return createMoshi()
    }
}
