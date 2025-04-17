package nl.rijksoverheid.mgo.data.pincode.hash

import org.mindrot.jbcrypt.BCrypt
import javax.inject.Inject

/**
 * BCrypt (https://en.wikipedia.org/wiki/Bcrypt) hashing algorithm for a pin code.
 */
internal class BcryptPinCodeHasher
  @Inject
  constructor() : PinCodeHasher {
    /**
     * Hash a pin code with BCrypt.
     * @param pinCode The pin code to hash.
     * @return The hashed pin code.
     */
    override fun hash(pinCode: String): String {
      return BCrypt.hashpw(pinCode, BCrypt.gensalt())
    }

    /**
     * Validates a hashed pin code with BCrypt.
     * @param pinCode The pin code to validate.
     * @param hash The hashed pin code to check against.
     * @return True if the pin codes match.
     */
    override fun validate(
      pinCode: String,
      hash: String,
    ): Boolean {
      return BCrypt.checkpw(pinCode, hash)
    }
  }
