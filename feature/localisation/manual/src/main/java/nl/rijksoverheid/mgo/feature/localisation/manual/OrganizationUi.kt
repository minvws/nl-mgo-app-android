package nl.rijksoverheid.mgo.feature.localisation.manual

import nl.rijksoverheid.mgo.component.organization.Organization
import nl.rijksoverheid.mgo.component.organization.TEST_ORGANIZATION_1
import nl.rijksoverheid.mgo.component.organization.TEST_ORGANIZATION_2
import nl.rijksoverheid.mgo.component.organization.TEST_ORGANIZATION_3

data class OrganizationUi(
  val organization: Organization,
  val supported: Boolean,
)

val TEST_ORGANIZATION_UI_1 =
  OrganizationUi(
    organization = TEST_ORGANIZATION_1.copy(added = false),
    supported = true,
  )

val TEST_ORGANIZATION_UI_2 =
  OrganizationUi(
    organization = TEST_ORGANIZATION_2.copy(added = true),
    supported = true,
  )

val TEST_ORGANIZATION_UI_3 =
  OrganizationUi(
    organization = TEST_ORGANIZATION_3.copy(added = false),
    supported = false,
  )
