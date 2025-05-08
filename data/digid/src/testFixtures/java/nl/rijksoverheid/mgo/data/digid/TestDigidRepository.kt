package nl.rijksoverheid.mgo.data.digid

class TestDigidRepository : DigidRepository {
  private var loginResult: Result<String> = Result.success("https://www.google.com")

  fun setLoginResult(result: Result<String>) {
    this.loginResult = result
  }

  override suspend fun login(): Result<String> {
    return loginResult
  }
}
