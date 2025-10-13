package nl.rijksoverheid.mgo.data.healthCategories.models

data class EndpointsWithDataSet(
  val dataSet: DataSet,
  val endpoints: List<DataSet.Endpoint>,
)
