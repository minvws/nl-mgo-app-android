package nl.rijksoverheid.mgo.framework.pdf

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
internal abstract class PdfModule {
  @Binds
  abstract fun providePdfGenerator(default: DefaultPdfGenerator): PdfGenerator
}
