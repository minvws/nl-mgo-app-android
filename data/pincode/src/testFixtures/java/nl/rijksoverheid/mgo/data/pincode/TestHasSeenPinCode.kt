package nl.rijksoverheid.mgo.data.pincode

class TestHasSeenPinCode : HasSeenPinCode {
    private var hasSeen: Boolean = false

    fun set(hasSeen: Boolean) {
        this.hasSeen = hasSeen
    }

    override fun invoke(): Boolean {
        return hasSeen
    }
}
