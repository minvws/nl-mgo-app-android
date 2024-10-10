package nl.rijksoverheid.mgo.data.pincode.hash

interface PinCodeHasher {
    fun hash(pinCode: String): String

    fun validate(
        pinCode: String,
        hash: String,
    ): Boolean
}
