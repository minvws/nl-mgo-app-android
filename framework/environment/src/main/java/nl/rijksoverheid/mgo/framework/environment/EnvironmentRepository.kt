package nl.rijksoverheid.mgo.framework.environment

/**
 * Repository for retrieving the current environment configuration.
 */
interface EnvironmentRepository {
  fun getEnvironment(): Environment
}
