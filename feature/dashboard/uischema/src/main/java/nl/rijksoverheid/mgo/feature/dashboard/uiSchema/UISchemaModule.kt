package nl.rijksoverheid.mgo.feature.dashboard.uiSchema

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import nl.rijksoverheid.mgo.feature.dashboard.uiSchema.pdf.CreatePdfUiSchema
import nl.rijksoverheid.mgo.feature.dashboard.uiSchema.pdf.DefaultCreatePdfUiSchema

@Module
@InstallIn(ViewModelComponent::class)
internal abstract class UISchemaModule {
  @Binds
  @ViewModelScoped
  abstract fun provideCreatePdf(default: DefaultCreatePdfUiSchema): CreatePdfUiSchema
}
