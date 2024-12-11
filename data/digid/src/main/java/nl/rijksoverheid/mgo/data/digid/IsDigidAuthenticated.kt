package nl.rijksoverheid.mgo.data.digid

interface IsDigidAuthenticated {
    operator fun invoke(): Boolean
}
