package nl.rijksoverheid.mgo.data.pincode.biometric

class TestDeviceHasBiometric(private val deviceHasSupport: Boolean) : DeviceHasBiometric {
  override fun invoke(): Boolean {
    return deviceHasSupport
  }
}
