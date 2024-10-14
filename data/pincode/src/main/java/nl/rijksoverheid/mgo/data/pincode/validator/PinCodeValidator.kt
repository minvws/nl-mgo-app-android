package nl.rijksoverheid.mgo.data.pincode.validator

interface PinCodeValidator {
    operator fun invoke(pinCode: List<Int>): Boolean
}
