package nl.rijksoverheid.mgo.data.pincode.strength

interface PinCodeStrengthValidator {
    operator fun invoke(pinCode: List<Int>): Boolean
}
