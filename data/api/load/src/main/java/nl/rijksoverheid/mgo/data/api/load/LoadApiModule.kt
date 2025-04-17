package nl.rijksoverheid.mgo.data.api.load

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import nl.nl.rijksoverheid.mgo.framework.network.BasicAuthInterceptor
import nl.nl.rijksoverheid.mgo.framework.network.auth.MgoAuthentication
import nl.rijksoverheid.mgo.framework.environment.Environment
import nl.rijksoverheid.mgo.framework.environment.EnvironmentRepository
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Named
import javax.inject.Singleton

fun createLoadApi(
  okHttpClient: OkHttpClient,
  baseUrl: String,
): LoadApi {
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
  return retrofit.create(LoadApi::class.java)
}

@InstallIn(SingletonComponent::class)
@Module
object LoadApiModule {
  @Provides
  @Singleton
  fun provideApi(
    okHttpClient: OkHttpClient,
    mgoAuthentication: MgoAuthentication,
    @Named("loadApiBaseUrl") baseUrl: String,
  ): LoadApi {
    val okHttpClientBuilder = okHttpClient.newBuilder()
    if (mgoAuthentication is MgoAuthentication.Basic) {
      okHttpClientBuilder.addInterceptor(BasicAuthInterceptor(user = mgoAuthentication.user, password = mgoAuthentication.password))
    }
    val okHttpClientWithAuth = okHttpClientBuilder.build()
    return createLoadApi(okHttpClient = okHttpClientWithAuth, baseUrl = baseUrl)
  }

  // TODO Set urls for other environments when available.
  @Provides
  @Singleton
  @Named("loadApiBaseUrl")
  fun provideBaseUrl(environmentRepository: EnvironmentRepository): String {
    return when (val environment = environmentRepository.getEnvironment()) {
      is Environment.Acc -> "https://lo-ad.acc.mgo.irealisatie.nl"
      is Environment.Prod -> "https://lo-ad.acc.mgo.irealisatie.nl"
      is Environment.Tst -> "https://lo-ad.test.mgo.irealisatie.nl"
      is Environment.Demo -> "https://lo-ad.acc.mgo.irealisatie.nl"
      is Environment.Custom -> environment.url
    }
  }
}
