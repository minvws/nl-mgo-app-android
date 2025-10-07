package nl.rijksoverheid.mgo.data.hcimParser

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import nl.rijksoverheid.mgo.data.hcimParser.javascript.AndroidQuickJsRepository
import nl.rijksoverheid.mgo.data.hcimParser.javascript.QuickJsRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class HcimParserModule {
  @Binds
  @Singleton
  abstract fun bindQuickJsRepository(default: AndroidQuickJsRepository): QuickJsRepository
}
