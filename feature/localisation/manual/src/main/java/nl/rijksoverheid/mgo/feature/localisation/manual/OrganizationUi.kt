package nl.rijksoverheid.mgo.feature.localisation.manual

import nl.rijksoverheid.mgo.component.organization.Organization
import nl.rijksoverheid.mgo.component.organization.TEST_ORGANIZATION_1

data class OrganizationUi(
  val organization: Organization,
  val supported: Boolean,
)

val TEST_ORGANIZATION_UI_1 =
  OrganizationUi(
    organization = TEST_ORGANIZATION_1,
    supported = true,
  )

val TEST_ORGANIZATION_UI_2 =
  OrganizationUi(
    organization = TEST_ORGANIZATION_1,
    supported = true,
  )

val TEST_ORGANIZATION_UI_3 =
  OrganizationUi(
    organization = TEST_ORGANIZATION_1,
    supported = true,
  )
