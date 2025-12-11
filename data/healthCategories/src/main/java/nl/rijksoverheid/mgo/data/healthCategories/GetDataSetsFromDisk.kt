package nl.rijksoverheid.mgo.data.healthCategories

import nl.rijksoverheid.mgo.data.healthCategories.models.DataSet

interface GetDataSetsFromDisk {
  operator fun invoke(): List<DataSet>
}
