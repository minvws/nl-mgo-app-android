package nl.rijksoverheid.mgo.data.pincode

interface ValidatePinCode {
    suspend operator fun invoke(pinCode: List<Int>): Boolean
}
