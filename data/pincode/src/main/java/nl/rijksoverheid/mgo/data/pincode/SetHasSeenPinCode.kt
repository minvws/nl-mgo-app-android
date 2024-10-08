package nl.rijksoverheid.mgo.data.pincode

interface SetHasSeenPinCode {
    operator fun invoke(hasSeen: Boolean)
}
