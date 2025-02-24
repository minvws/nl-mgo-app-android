package nl.rijksoverheid.mgo.data.pincode

/**
 * Use case that checks if the user has a pin code set.
 */
interface HasPinCode {
    /**
     * @return True if the user has a pin code set.
     */
    operator fun invoke(): Boolean
}
