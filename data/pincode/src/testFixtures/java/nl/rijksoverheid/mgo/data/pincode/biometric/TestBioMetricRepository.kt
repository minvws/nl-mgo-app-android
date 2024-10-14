package nl.rijksoverheid.mgo.data.pincode.biometric

class TestBioMetricRepository(private val deviceHasSupport: Boolean) : BioMetricRepository {
    override fun deviceHasSupport(): Boolean {
        return deviceHasSupport
    }
}
