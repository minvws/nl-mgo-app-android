package nl.rijksoverheid.mgo.data.healthcare.healthCareDataState

import nl.rijksoverheid.mgo.data.healthcare.healthCareData.HealthCareCategory
import nl.rijksoverheid.mgo.data.healthcare.healthCareData.HealthCareDataRepository
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Fetches health care data. Emits different states so the UI knows the state.
 */
internal class DefaultHealthCareDataStateRepository
    @Inject
    constructor(private val healthCareDataRepository: HealthCareDataRepository) :
    HealthCareDataStateRepository {
        /**
         * Fetches health care data state.
         * @param organization The organization you want to fetch health care data from.
         * @param category The category of health care data it should fetch.
         */
        override fun get(
            organization: MgoOrganization,
            category: HealthCareCategory,
        ): Flow<HealthCareDataState> =
            flow {
                emit(HealthCareDataState.Loading(organization = organization, category = category))
                val results = healthCareDataRepository.get(organization = organization, category = category)
                if (results.isEmpty()) {
                    emit(
                        HealthCareDataState.Empty(
                            organization = organization,
                            category = category,
                        ),
                    )
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
