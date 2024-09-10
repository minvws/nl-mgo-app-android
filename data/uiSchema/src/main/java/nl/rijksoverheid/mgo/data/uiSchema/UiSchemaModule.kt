package nl.rijksoverheid.mgo.data.uiSchema

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import nl.rijksoverheid.mgo.data.uiSchema.repository.DefaultUiSchemaRepository
import nl.rijksoverheid.mgo.data.uiSchema.repository.UiSchemaRepository
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object UiSchemaModule {
    @Provides
    @Singleton
    fun provideUiSchemaRepository(): UiSchemaRepository {
        return DefaultUiSchemaRepository()
    }

    @Provides
    @Singleton
    fun provideUiSchemaMapper(
        @ApplicationContext context: Context,
    ): UiSchemaMapper {
        return DefaultUiSchemaMapper(context)
    }
}
