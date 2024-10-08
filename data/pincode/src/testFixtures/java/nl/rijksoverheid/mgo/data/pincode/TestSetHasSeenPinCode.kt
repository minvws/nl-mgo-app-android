package nl.rijksoverheid.mgo.data.pincode

class TestSetHasSeenPinCode : SetHasSeenPinCode {
    private var hasSeen: Boolean = false

    fun get(): Boolean {
        return hasSeen
    }

    override fun invoke(hasSeen: Boolean) {
        this.hasSeen = true
    }
}
