package nl.rijksoverheid.mgo.framework.environment

interface EnvironmentRepository {
    fun getEnvironment(): Environment
}
