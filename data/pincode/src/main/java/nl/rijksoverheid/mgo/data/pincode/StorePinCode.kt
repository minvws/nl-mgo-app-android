package nl.rijksoverheid.mgo.data.pincode

/**
 * Stores a pin code.
 */
interface StorePinCode {
  /**
   * @param pinCode The pin code to store.
   */
  operator fun invoke(pinCode: List<Int>)
}
