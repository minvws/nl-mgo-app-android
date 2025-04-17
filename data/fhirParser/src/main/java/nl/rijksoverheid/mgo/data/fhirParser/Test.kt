package nl.rijksoverheid.mgo.data.fhirParser

import nl.rijksoverheid.mgo.data.fhirParser.models.DownloadBinary
import nl.rijksoverheid.mgo.data.fhirParser.models.DownloadLink
import nl.rijksoverheid.mgo.data.fhirParser.models.HealthUiGroup
import nl.rijksoverheid.mgo.data.fhirParser.models.HealthUiSchema
import nl.rijksoverheid.mgo.data.fhirParser.models.SingleValue

/**
 * Represents a [SingleValue].
 */
val TEST_UI_ENTRY =
  SingleValue(
    label = "UI Entry Label",
    display = "Display",
    type = "SINGLE_VALUE",
  )

/**
 * Represents a [DownloadLink].
 */
val TEST_UI_ENTRY_BINARY =
  DownloadBinary(
    type = "DOWNLOAD_BINARY",
    label = "UI Entry Label",
    reference = "fhir",
  )

/**
 * Represents a [HealthUiGroup].
 */
val TEST_UI_SCHEMA_GROUP =
  HealthUiGroup(
    label = "UI Schema Group",
    children =
      listOf(
        TEST_UI_ENTRY,
      ),
  )

/**
 * Represents a [HealthUiSchema].
 */
val TEST_UI_SCHEMA =
  HealthUiSchema(
    label = "UI Schema Label",
    children = listOf(TEST_UI_SCHEMA_GROUP),
  )
