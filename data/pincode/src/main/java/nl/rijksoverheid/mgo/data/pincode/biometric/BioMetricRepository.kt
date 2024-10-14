package nl.rijksoverheid.mgo.data.pincode.biometric

interface BioMetricRepository {
    fun deviceHasSupport(): Boolean
}
