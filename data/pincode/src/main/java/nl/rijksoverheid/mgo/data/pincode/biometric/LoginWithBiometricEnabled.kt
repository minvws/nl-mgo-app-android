package nl.rijksoverheid.mgo.data.pincode.biometric

interface LoginWithBiometricEnabled {
    operator fun invoke(): Boolean
}
