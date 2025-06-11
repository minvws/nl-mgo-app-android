package nl.rijksoverheid.mgo.feature.dashboard.healthCategory

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import nl.rijksoverheid.mgo.feature.dashboard.healthCategory.pdf.CreatePdfForHealthCategories
import nl.rijksoverheid.mgo.feature.dashboard.healthCategory.pdf.DefaultCreatePdfForHealthCategories

@Module
@InstallIn(ViewModelComponent::class)
internal abstract class HealthCategoryModule {
  @Binds
  @ViewModelScoped
  abstract fun provideCreatePdf(default: DefaultCreatePdfForHealthCategories): CreatePdfForHealthCategories
}
