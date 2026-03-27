package nl.rijksoverheid.mgo.data.organization.api

import java.io.InputStream

data class OrganizationApiResponse(
  val response: InputStream,
  val cached: Boolean,
)
