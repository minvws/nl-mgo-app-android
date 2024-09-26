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
                emit(HealthCareDataState(loading = true, organization = organization, category = category, uiSchemaListResults = listOf()))
                val uiSchemaListResults = uiSchemaRepository.getUiSchema(organization = organization, category = category)
                emit(
                    HealthCareDataState(
                        loading = false,
                        organization = organization,
                        category = category,
                        uiSchemaListResults = uiSchemaListResults,
                    ),
                )
            }
    }
