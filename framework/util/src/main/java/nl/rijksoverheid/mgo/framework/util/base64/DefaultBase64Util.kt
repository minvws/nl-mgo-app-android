package nl.rijksoverheid.mgo.framework.util.base64

import android.util.Base64
import javax.inject.Inject

/**
 * Util that handles encoding and decoding of Base64 strings.
 */
internal class DefaultBase64Util
  @Inject
  constructor() : Base64Util {
    /**
     * Encodes a given string into its Base64 representation using UTF-8 encoding.
     *
     * @param str The input string to be encoded.
     * @return The Base64-encoded version of the input string.
     */
    override fun encode(str: String): String {
      return Base64.encodeToString(str.toByteArray(), Base64.DEFAULT)
    }

    /**
     * Decodes a given Base64-encoded string back to its original form using UTF-8 encoding.
     *
     * @param base64Str The Base64-encoded string to be decoded.
     * @return The decoded string.
     * @throws IllegalArgumentException If the input is not a valid Base64-encoded string.
     */
    override fun decode(base64Str: String): String {
      return String(Base64.decode(base64Str, Base64.DEFAULT), charset("UTF-8"))
    }
  }
