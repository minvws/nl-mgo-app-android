package nl.rijksoverheid.mgo.feature.dashboard.uiSchema

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import nl.rijksoverheid.mgo.feature.dashboard.uiSchema.models.mapper.DefaultUISchemaSectionMapper
import nl.rijksoverheid.mgo.feature.dashboard.uiSchema.models.mapper.UISchemaSectionMapper

@Module
@InstallIn(ViewModelComponent::class)
internal abstract class UiSchemaModule {
  @Binds
  @ViewModelScoped
  abstract fun bindUiSchemaSectionMapper(default: DefaultUISchemaSectionMapper): UISchemaSectionMapper
}
