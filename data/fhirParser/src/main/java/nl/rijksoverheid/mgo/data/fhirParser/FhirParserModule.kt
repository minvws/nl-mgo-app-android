package nl.rijksoverheid.mgo.data.fhirParser

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import nl.rijksoverheid.mgo.data.fhirParser.js.DefaultJsRuntimeRepository
import nl.rijksoverheid.mgo.data.fhirParser.js.JsRuntimeRepository
import nl.rijksoverheid.mgo.data.fhirParser.mgoResource.DefaultMgoResourceRepository
import nl.rijksoverheid.mgo.data.fhirParser.mgoResource.MgoResourceRepository
import nl.rijksoverheid.mgo.data.fhirParser.uiSchema.DefaultUiSchemaRepository
import nl.rijksoverheid.mgo.data.fhirParser.uiSchema.UiSchemaRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class FhirParserModule {
    @Binds
    @Singleton
    abstract fun bindJsRuntimeRepository(default: DefaultJsRuntimeRepository): JsRuntimeRepository

    @Binds
    @Singleton
    abstract fun bindMgoResourceRepository(default: DefaultMgoResourceRepository): MgoResourceRepository

    @Binds
    @Singleton
    abstract fun bindUiSchemaRepository(default: DefaultUiSchemaRepository): UiSchemaRepository
}
