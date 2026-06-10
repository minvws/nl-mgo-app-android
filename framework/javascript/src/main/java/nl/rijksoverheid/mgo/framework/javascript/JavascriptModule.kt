package nl.rijksoverheid.mgo.framework.javascript

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class JavascriptModule {
  @Binds
  @Singleton
  abstract fun bindJavascriptEngineRepository(default: AndroidJavascriptEngineRepository): JavascriptEngineRepository
}
