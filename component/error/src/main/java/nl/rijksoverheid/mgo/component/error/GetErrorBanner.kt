package nl.rijksoverheid.mgo.component.error

import kotlinx.coroutines.flow.Flow
import nl.rijksoverheid.mgo.component.organization.MgoOrganization
import nl.rijksoverheid.mgo.data.healthCategories.models.HealthCategoryGroup

interface GetErrorBanner {
  operator fun invoke(
    categories: List<HealthCategoryGroup.HealthCategory>,
    organizations: List<MgoOrganization>,
  ): Flow<ErrorBannerState?>
}
