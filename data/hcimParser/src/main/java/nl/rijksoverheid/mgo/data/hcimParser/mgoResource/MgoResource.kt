package nl.rijksoverheid.mgo.data.hcimParser.mgoResource

import kotlinx.serialization.json.Json
import nl.rijksoverheid.mgo.data.hcimParser.uiSchema.models.ExtensionValue_Of_MgoCodeableConcept
import nl.rijksoverheid.mgo.data.hcimParser.uiSchema.models.HealthUiGroup
import nl.rijksoverheid.mgo.data.hcimParser.uiSchema.models.IheMhdMinimalDocumentReference
import nl.rijksoverheid.mgo.data.hcimParser.uiSchema.models.MgoAnnotation
import nl.rijksoverheid.mgo.data.hcimParser.uiSchema.models.MgoCodeableConcept
import nl.rijksoverheid.mgo.data.hcimParser.uiSchema.models.MgoCodingProps
import nl.rijksoverheid.mgo.data.hcimParser.uiSchema.models.MgoDateTime
import nl.rijksoverheid.mgo.data.hcimParser.uiSchema.models.MgoIdentifier
import nl.rijksoverheid.mgo.data.hcimParser.uiSchema.models.MgoInstant
import nl.rijksoverheid.mgo.data.hcimParser.uiSchema.models.MgoReference
import nl.rijksoverheid.mgo.data.hcimParser.uiSchema.models.R4BbsDocumentReference
import nl.rijksoverheid.mgo.data.hcimParser.uiSchema.models.ZibProblem
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

typealias MgoResourceReferenceId = String

/**
 * Represents a resource generated from a shared JavaScript library.
 *
 * The [json] field contains the serialized resource payload that can later be
 * transformed into UI models such as [HealthUiGroup], or deserialized into a
 * strongly typed generated resource class that is stored in [decodedObject].
 *
 * Concrete generated resource types include classes such as
 * `IheMhdMinimalDocumentReference`.
 *
 */
data class MgoResource(
  val organizationId: String,
  val organizationName: String,
  val referenceId: MgoResourceReferenceId,
  val profile: String,
  val json: String,
  val decodedObject: Any?,
)

val TEST_MGO_RESOURCE =
  MgoResource(
    organizationId = "",
    organizationName = "",
    referenceId = "1",
    profile = "",
    json = "",
    decodedObject = null,
  )

inline fun <reified O : Any> createMgoResource(
  organizationId: String,
  profile: String,
  decodedObject: O,
): MgoResource {
  val json =
    Json {
      ignoreUnknownKeys = true
      encodeDefaults = true
    }
  return MgoResource(
    organizationId = organizationId,
    organizationName = "",
    referenceId = "1",
    profile = profile,
    json = json.encodeToString(decodedObject),
    decodedObject = decodedObject,
  )
}

@Suppress("ktlint:standard:function-naming")
fun TEST_IHEMHDMINIMAL_DOCUMENT_REFERENCE(
  id: String = "1",
  date: LocalDateTime? = LocalDateTime.of(1991, 7, 2, 13, 0),
): IheMhdMinimalDocumentReference {
  val dateString =
    date
      ?.atZone(ZoneOffset.UTC)
      ?.format(DateTimeFormatter.ISO_INSTANT)

  return IheMhdMinimalDocumentReference(
    id = id,
    resourceType = "1",
    referenceId = "1",
    context = IheMhdMinimalDocumentReference.Context(),
    indexed = dateString?.let { MgoInstant(value = it) },
  )
}

@Suppress("ktlint:standard:function-naming")
fun TEST_R4BBS_DOCUMENT_REFERENCE(
  id: String = "1",
  date: LocalDateTime? = LocalDateTime.of(1991, 7, 2, 13, 0),
): R4BbsDocumentReference {
  val dateString =
    date
      ?.atZone(ZoneOffset.UTC)
      ?.format(DateTimeFormatter.ISO_INSTANT)

  return R4BbsDocumentReference(
    id = id,
    referenceId = "1",
    resourceType = "1",
    context = R4BbsDocumentReference.Context(),
    date = dateString?.let { MgoDateTime(value = it) },
  )
}

@Suppress("ktlint:standard:function-naming")
fun TEST_ZIB_PROBLEM(id: String = "nictiz-case-1-diabetes") =
  ZibProblem(
    id = id,
    referenceId = "Condition/nictiz-case-1-diabetes",
    resourceType = "Condition",
    identifier =
      listOf(
        MgoIdentifier(
          system = "urn:oid:2.16.840.1.113883.2.4.3.11.999.7.6",
          value = "nictiz-case-1",
        ),
      ),
    subject =
      MgoReference(reference = "Patient/93cde269-ce35-4077-a39d-19296670e949", display = "Johan Helleman"),
    onsetDateTime = MgoDateTime(value = "2012-09-20"),
    asserter = MgoReference(display = "Huisarts"),
    verificationStatus =
      ZibProblem.VerificationStatus(
        verificatieStatusCodelijst =
          ExtensionValue_Of_MgoCodeableConcept(
            _ext = true,
            coding = listOf(MgoCodingProps(code = "410605003", display = "aanwezigheid bevestigd (kwalificatiewaarde)", system = "http://snomed.info/sct")),
          ),
      ),
    clinicalStatus =
      ZibProblem.ClinicalStatus(
        problemStatusCodelist =
          ExtensionValue_Of_MgoCodeableConcept(
            _ext = true,
            coding =
              listOf(
                MgoCodingProps(
                  code = "55561003",
                  display = "Actueel",
                  system = "http://snomed.info/sct",
                ),
              ),
          ),
      ),
    category =
      listOf(
        MgoCodeableConcept(
          coding =
            listOf(
              MgoCodingProps(
                code = "439401001",
                display = "diagnose",
                system = "http://snomed.info/sct",
              ),
            ),
        ),
      ),
    code =
      MgoCodeableConcept(
        coding =
          listOf(
            MgoCodingProps(
              code = "44054006",
              display = "Diabetes mellitus type 2",
              system = "http://snomed.info/sct",
            ),
          ),
      ),
    note =
      listOf(
        MgoAnnotation(text = "Controles bij huisarts, dieet en medicatie"),
      ),
  )
