package nl.rijksoverheid.mgo.data.categories

interface HealthGroupRepository {
  fun get(): List<HealthGroup>
}
