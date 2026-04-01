package nl.rijksoverheid.mgo.component.pdf

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
internal abstract class PdfModule {
  @Binds
  @ViewModelScoped
  abstract fun bindCreatePdfForUiSchemas(default: DefaultCreatePdfForUiSchemas): CreatePdfForUiSchemas
}
