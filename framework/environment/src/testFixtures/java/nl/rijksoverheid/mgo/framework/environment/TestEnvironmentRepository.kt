package nl.rijksoverheid.mgo.framework.environment

class TestEnvironmentRepository : EnvironmentRepository {
  private var environment: Environment = Environment.Tst(versionCode = 1, deeplinkHost = "mgo")

  fun setEnvironment(environment: Environment) {
    this.environment = environment
  }

  override fun getEnvironment(): Environment {
    return environment
  }
}
