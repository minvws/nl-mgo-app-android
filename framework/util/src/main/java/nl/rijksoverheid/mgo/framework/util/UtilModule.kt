package nl.rijksoverheid.mgo.framework.util

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import nl.rijksoverheid.mgo.framework.util.base64.Base64Util
import nl.rijksoverheid.mgo.framework.util.base64.DefaultBase64Util
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class UtilModule {
  @Binds
  @Singleton
  abstract fun provideBas64Util(default: DefaultBase64Util): Base64Util
}
