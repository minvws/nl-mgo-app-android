package nl.rijksoverheid.mgo.data.uiSchema

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import nl.rijksoverheid.mgo.data.uiSchema.javascript.DefaultJsRuntimeRepository
import nl.rijksoverheid.mgo.data.uiSchema.javascript.JsRuntimeRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class UiSchemaModule {
    @Binds
    @Singleton
    abstract fun provideJsRuntimeRepository(default: DefaultJsRuntimeRepository): JsRuntimeRepository

    @Binds
    @Singleton
    abstract fun provideUiSchemaMapper(default: DefaultUiSchemaMapper): UiSchemaMapper

    @Binds
    @Singleton
    abstract fun provideHealthCareResourceMapper(default: DefaultHealthCareResourceMapper): HealthCareResourceMapper
}
