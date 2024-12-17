package nl.rijksoverheid.mgo.data.healthcare

import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal class DefaultHealthCareDataStateRepository
    @Inject
    constructor(private val uiSchemaRepository: UiSchemaRepository) :
    HealthCareDataStateRepository {
        override fun get(
            organization: MgoOrganization,
            category: HealthCareCategory,
        ): Flow<HealthCareDataState> =
            flow {
                emit(HealthCareDataState.Loading(organization = organization, category = category))
                val results = uiSchemaRepository.getUiSchema(organization = organization, category = category)
                if (results.isEmpty()) {
                    emit(HealthCareDataState.Empty(organization = organization, category = category))
                } else {
                    emit(
                        HealthCareDataState.Loaded(
                            results = results,
                            organization = organization,
                            category = category,
                        ),
                    )
                }
            }
    }
