package nl.rijksoverheid.mgo.framework.util.base64

/**
 * Util that handles encoding and decoding of Base64 strings.
 */
interface Base64Util {
  /**
   * Encodes a given string into its Base64 representation.
   *
   * @param str The input string to be encoded.
   * @return The Base64-encoded version of the input string.
   */
  fun encode(str: String): String

  /**
   * Decodes a given Base64-encoded string back to its original form.
   *
   * @param base64Str The Base64-encoded string to be decoded.
   * @return The decoded string.
   * @throws IllegalArgumentException If the input is not a valid Base64-encoded string.
   */
  fun decode(base64Str: String): String
}
