package nl.rijksoverheid.mgo.component.snackbar

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object MgoSnackBarModule {
    @Provides
    @Singleton
    fun provideSnackBarRepository(): SnackBarRepository {
        return DefaultSnackBarRepository()
    }
}
