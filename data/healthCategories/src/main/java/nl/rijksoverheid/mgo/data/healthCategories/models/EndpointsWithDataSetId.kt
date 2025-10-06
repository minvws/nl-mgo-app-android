package nl.rijksoverheid.mgo.data.healthCategories.models

data class EndpointsWithDataSetId(
  val id: DataSetId,
  val endpoints: List<DataSet.Endpoint>,
)
