package nl.rijksoverheid.mgo.component.pdf

import nl.rijksoverheid.mgo.data.hcimParser.uiSchema.models.HealthUiSchema
import java.io.File
import javax.inject.Inject

internal class DefaultCreatePdfForUiSchemas
  @Inject
  constructor(
    private val createPdf: CreatePdf,
  ) : CreatePdfForUiSchemas {
    override operator fun invoke(uiSchemas: List<HealthUiSchema>): File {
      val pdf = uiSchemas.toMgoPdf()
      return createPdf(pdf)
    }
  }
