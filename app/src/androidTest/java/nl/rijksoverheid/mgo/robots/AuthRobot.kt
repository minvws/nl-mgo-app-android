package nl.rijksoverheid.mgo.robots

import nl.rijksoverheid.mgo.data.digid.IsDigidAuthenticated
import nl.rijksoverheid.mgo.data.digid.SetDigidAuthenticated
import nl.rijksoverheid.mgo.data.pincode.HasPinCode
import nl.rijksoverheid.mgo.data.pincode.StorePinCode
import javax.inject.Inject

class AuthRobot
  @Inject
  constructor(
    private val digidAuthenticated: IsDigidAuthenticated,
    private val setDigidAuthenticated: SetDigidAuthenticated,
    private val storePinCode: StorePinCode,
    private val hasPinCode: HasPinCode,
  ) {
    fun setAuthenticatedWithDigid(): AuthRobot {
      setDigidAuthenticated()
      assertDigidAuthenticated()
      return this
    }

    fun assertDigidAuthenticated(): AuthRobot {
      check(digidAuthenticated()) { "User should be authenticated with DigiD" }
      return this
    }

    fun setPinCode(pinCode: List<Int>): AuthRobot {
      storePinCode(pinCode)
      assertHasPinCode()
      return this
    }

    fun assertHasPinCode(): AuthRobot {
      check(hasPinCode()) { "User should have pin code set" }
      return this
    }
  }
