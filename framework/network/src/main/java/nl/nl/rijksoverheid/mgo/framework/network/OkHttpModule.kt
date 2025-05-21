package nl.nl.rijksoverheid.mgo.framework.network

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import java.io.File
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal object OkHttpModule {
  @Provides
  @Singleton
  fun provideOkHttpClient(
    @ApplicationContext context: Context,
  ): OkHttpClient {
    val cache =
      Cache(
        directory = File(context.cacheDir, "http_cache"),
        maxSize = 50L * 1024L * 1024L, // 50 MiB
      )
    return OkHttpClient
      .Builder()
      .cache(cache)
      .addInterceptor(ChuckerInterceptor(context))
      .build()
  }
}
