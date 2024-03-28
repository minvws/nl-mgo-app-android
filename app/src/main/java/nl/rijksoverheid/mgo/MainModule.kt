package nl.rijksoverheid.mgo

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import nl.rijksoverheid.mgo.framework.environment.Environment
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal object MainModule {
    @Provides
    @Singleton
    fun provideEnvironment(): Environment {
        return when (BuildConfig.FLAVOR) {
            "tst" -> Environment.Tst
            "acc" -> Environment.Acc
            "prod" -> Environment.Prod
            else -> Environment.Tst
        }
    }
}
