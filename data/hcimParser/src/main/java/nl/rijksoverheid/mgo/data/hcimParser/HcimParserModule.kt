package nl.rijksoverheid.mgo.data.hcimParser

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import nl.rijksoverheid.mgo.data.hcimParser.javascript.AndroidQuickJsRepository
import nl.rijksoverheid.mgo.data.hcimParser.javascript.QuickJsRepository
import nl.rijksoverheid.mgo.data.hcimParser.version.AndroidGetHcimParserVersion
import nl.rijksoverheid.mgo.data.hcimParser.version.GetHcimParserVersion
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class HcimParserModule {
  @Binds
  @Singleton
  abstract fun bindQuickJsRepository(default: AndroidQuickJsRepository): QuickJsRepository

  @Binds
  @Singleton
  abstract fun bindGetHcimParserVersion(default: AndroidGetHcimParserVersion): GetHcimParserVersion
}
