package nl.rijksoverheid.mgo.data.organization

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import nl.rijksoverheid.mgo.data.organization.api.OrganizationApiClient

fun createOrganizationRepositoryForJvm(apiClient: OrganizationApiClient = TestOrganizationApiClient()): OrganizationRepository {
  val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
  OrganizationsDatabase.Schema.create(driver)
  return OrganizationRepository(driver = driver, apiClient = apiClient, supportedDataServiceIds = listOf())
}
