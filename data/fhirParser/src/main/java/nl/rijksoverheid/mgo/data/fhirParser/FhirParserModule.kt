package nl.rijksoverheid.mgo.data.fhirParser

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import nl.rijksoverheid.mgo.data.fhirParser.js.DefaultJsRuntimeRepository
import nl.rijksoverheid.mgo.data.fhirParser.js.JsRuntimeRepository
import nl.rijksoverheid.mgo.data.fhirParser.mgoResource.DefaultMgoResourceMapper
import nl.rijksoverheid.mgo.data.fhirParser.mgoResource.MgoResourceMapper
import nl.rijksoverheid.mgo.data.fhirParser.uiSchema.DefaultUiSchemaMapper
import nl.rijksoverheid.mgo.data.fhirParser.uiSchema.UiSchemaMapper
import nl.rijksoverheid.mgo.data.fhirParser.version.DefaultGetFhirParserVersion
import nl.rijksoverheid.mgo.data.fhirParser.version.GetFhirParserVersion
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class FhirParserModule {
  @Binds
  @Singleton
  abstract fun bindJsRuntimeRepository(default: DefaultJsRuntimeRepository): JsRuntimeRepository

  @Binds
  @Singleton
  abstract fun bindMgoResourceRepository(default: DefaultMgoResourceMapper): MgoResourceMapper

  @Binds
  @Singleton
  abstract fun bindUiSchemaRepository(default: DefaultUiSchemaMapper): UiSchemaMapper

  @Binds
  @Singleton
  abstract fun bindGetFhirParserVersion(default: DefaultGetFhirParserVersion): GetFhirParserVersion
}
