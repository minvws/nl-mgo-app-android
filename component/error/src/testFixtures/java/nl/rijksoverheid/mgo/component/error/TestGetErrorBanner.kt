package nl.rijksoverheid.mgo.component.error

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import nl.rijksoverheid.mgo.component.organization.MgoOrganization
import nl.rijksoverheid.mgo.data.healthCategories.models.HealthCategoryGroup

class TestGetErrorBanner : GetErrorBanner {
  override fun invoke(
    categories: List<HealthCategoryGroup.HealthCategory>,
    organizations: List<MgoOrganization>,
  ): Flow<ErrorBannerState?> = flow { emit(null) }
}
