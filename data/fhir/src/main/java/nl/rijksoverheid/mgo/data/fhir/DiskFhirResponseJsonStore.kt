package nl.rijksoverheid.mgo.data.fhir

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject

class DiskFhirResponseJsonStore
  @Inject
  constructor(
    @ApplicationContext private val context: Context,
  ) : FhirResponseJsonStore {
    private val rootDir =
      File(context.filesDir, "fhir").also {
        if (!it.exists()) {
          check(it.mkdir()) {
            "Could not create fhir dhir"
          }
        }
      }

    override suspend fun get(
      organizationId: String,
      dataServiceId: String,
      endpointId: String,
    ): FhirResponseJsonSource {
      val file = File(rootDir, "$organizationId/$dataServiceId/$endpointId.json")
      return FhirResponseJsonSource.Disk(file)
    }

    override suspend fun store(
      organizationId: String,
      dataServiceId: String,
      endpointId: String,
      json: FhirResponseJson,
    ): FhirResponseJsonSource {
      val file = File(rootDir, "$organizationId/$dataServiceId/$endpointId.json")
      file.parentFile?.mkdirs()

      file.outputStream().use { outputStream ->
        outputStream.write(json.toByteArray())
      }

      return FhirResponseJsonSource.Disk(file)
    }
  }
