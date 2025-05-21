package nl.rijksoverheid.mgo.data.pincode

/**
 * Check if a pin code is stored.
 */
interface HasPinCode {
  /**
   * @return True if a pin code is stored.
   */
  operator fun invoke(): Boolean
}
