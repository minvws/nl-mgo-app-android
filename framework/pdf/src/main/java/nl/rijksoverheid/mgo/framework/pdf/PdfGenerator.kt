package nl.rijksoverheid.mgo.framework.pdf

interface PdfGenerator {
  suspend operator fun invoke()
}
