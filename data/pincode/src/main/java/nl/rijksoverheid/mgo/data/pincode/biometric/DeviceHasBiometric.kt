package nl.rijksoverheid.mgo.data.pincode.biometric

interface DeviceHasBiometric {
    operator fun invoke(): Boolean
}
