package nl.rijksoverheid.mgo.component.pdfViewer

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
  abstract fun providePdfGenerator(default: DefaultPdfGenerator): PdfGenerator
}
