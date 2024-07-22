package nl.rijksoverheid.mgo.framework.environment

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object EnvironmentModule {
    @Provides
    @Singleton
    fun provideEnvironmentRepository(
        @Named("appFlavor") appFlavor: String,
        @Named("versionCode") versionCode: Int,
    ): EnvironmentRepository {
        return DefaultEnvironmentRepository(appFlavor = appFlavor, versionCode = versionCode)
    }
}
