package nl.rijksoverheid.mgo.data.pincode.hash

import org.mindrot.jbcrypt.BCrypt
import javax.inject.Inject

internal class BcryptPinCodeHasher
    @Inject
    constructor() : PinCodeHasher {
        override fun hash(pinCode: String): String {
            return BCrypt.hashpw(pinCode, BCrypt.gensalt())
        }

        override fun validate(
            pinCode: String,
            hash: String,
        ): Boolean {
            return BCrypt.checkpw(pinCode, hash)
        }
    }
