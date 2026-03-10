package nl.rijksoverheid.mgo.data.organization

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver

fun createOrganizationRepositoryForJvm(): OrganizationRepository {
  val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
  OrganizationsDatabase.Schema.create(driver)
  return OrganizationRepository(driver = driver, supportedDataServiceIds = listOf())
}
