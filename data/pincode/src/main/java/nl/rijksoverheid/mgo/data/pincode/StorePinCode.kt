package nl.rijksoverheid.mgo.data.pincode

interface StorePinCode {
    operator fun invoke(pinCode: List<Int>)
}
