package nl.rijksoverheid.mgo.component.pdf

import java.io.File

interface CreatePdfForUiSchemas {
  operator fun invoke(): File
}
