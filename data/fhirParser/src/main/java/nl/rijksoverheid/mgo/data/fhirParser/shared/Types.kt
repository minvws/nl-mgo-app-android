@file:Suppress("ktlint:standard:no-wildcard-imports", "ktlint:standard:max-line-length")

package nl.rijksoverheid.mgo.data.fhirParser.shared

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*
import kotlinx.serialization.json.*

@Serializable
@Parcelize
data class DownloadBinary(
    val label: String,
    val reference: String? = null,
    val type: DownloadBinaryType,
) : Parcelable

@Serializable
enum class DownloadBinaryType(val value: String) {
    @SerialName("DOWNLOAD_BINARY")
    DownloadBinary("DOWNLOAD_BINARY"),
}

@Serializable
@Parcelize
data class DownloadLink(
    val label: String,
    val type: DownloadLinkType,
    val url: String? = null,
) : Parcelable

@Serializable
enum class DownloadLinkType(val value: String) {
    @SerialName("DOWNLOAD_LINK")
    DownloadLink("DOWNLOAD_LINK"),
}

@Serializable
@Parcelize
data class EAfspraakAppointment(
    val description: String? = null,
    val end: String? = null,
    val fhirVersion: FhirVersionR3,
    val id: String? = null,
    val participant: List<Participant>? = null,
    val profile: EAfspraakAppointmentProfile,
    @SerialName("referenceId")
    val referenceID: String,
    val resourceType: String? = null,
    val specialty: List<MgoCodeableConcept>? = null,
    val start: String? = null,
    val status: String? = null,
) : Parcelable

@Serializable
enum class FhirVersionR3(val value: String) {
    @SerialName("R3")
    R3("R3"),
}

@Serializable
@Parcelize
data class Participant(
    val actor: MgoReference? = null,
) : Parcelable

@Serializable
@Parcelize
data class MgoReference(
    val display: String? = null,
    val reference: String? = null,
) : Parcelable

@Serializable
enum class EAfspraakAppointmentProfile(val value: String) {
    @SerialName("http://nictiz.nl/fhir/StructureDefinition/eAfspraak-Appointment")
    HTTPNictizNlFhirStructureDefinitionEAfspraakAppointment("http://nictiz.nl/fhir/StructureDefinition/eAfspraak-Appointment"),
}

@Serializable
@Parcelize
data class MgoCodeableConcept(
    val coding: List<MgoCoding>,
    val text: String? = null,
) : Parcelable

@Serializable
@Parcelize
data class MgoCoding(
    val code: String? = null,
    val display: String? = null,
    val system: String? = null,
) : Parcelable

@Serializable
@Parcelize
data class GpDiagnosticResult(
    val code: MgoCodeableConcept? = null,
    val comment: String? = null,
    val context: MgoReference? = null,
    val effective: String? = null,
    val fhirVersion: FhirVersionR3,
    val id: String? = null,
    val identifier: List<MgoIdentifier>? = null,
    val method: MgoCodeableConcept? = null,
    val performer: List<MgoReference>? = null,
    val profile: GpDiagnosticResultProfile,
    @SerialName("referenceId")
    val referenceID: String,
    val resourceType: String? = null,
    val status: String? = null,
    val subject: MgoReference? = null,
    val valueBoolean: Boolean? = null,
    val valueCodeableConcept: MgoCodeableConcept? = null,
    val valueDateTime: String? = null,
    val valuePeriod: MgoPeriod? = null,
    val valueQuantity: MgoDuration? = null,
    val valueRange: MgoRange? = null,
    val valueString: String? = null,
) : Parcelable

@Serializable
@Parcelize
data class MgoIdentifier(
    val system: String? = null,
    val type: MgoCodeableConcept? = null,
    val use: String? = null,
    val value: String? = null,
) : Parcelable

@Serializable
enum class GpDiagnosticResultProfile(val value: String) {
    @SerialName("http://nictiz.nl/fhir/StructureDefinition/gp-DiagnosticResult")
    HTTPNictizNlFhirStructureDefinitionGpDiagnosticResult("http://nictiz.nl/fhir/StructureDefinition/gp-DiagnosticResult"),
}

@Serializable
@Parcelize
data class MgoPeriod(
    val end: String? = null,
    val start: String? = null,
) : Parcelable

@Serializable
@Parcelize
data class MgoDuration(
    val code: String? = null,
    val comparator: String? = null,
    val system: String? = null,
    val unit: String? = null,
    val value: Double? = null,
) : Parcelable

@Serializable
@Parcelize
data class MgoRange(
    val high: MgoDuration? = null,
    val low: MgoDuration? = null,
) : Parcelable

@Serializable
@Parcelize
data class GpEncounter(
    @SerialName("class")
    val gpEncounterClass: MgoCoding? = null,
    val fhirVersion: FhirVersionR3,
    val id: String? = null,
    val participant: List<EncounterParticipant>? = null,
    val period: MgoPeriod? = null,
    val profile: GpEncounterProfile,
    val reason: List<MgoCodeableConcept>? = null,
    @SerialName("referenceId")
    val referenceID: String,
    val resourceType: String? = null,
    val serviceProvider: MgoReference? = null,
) : Parcelable

@Serializable
@Parcelize
data class EncounterParticipant(
    val individual: MgoReference? = null,
) : Parcelable

@Serializable
enum class GpEncounterProfile(val value: String) {
    @SerialName("http://nictiz.nl/fhir/StructureDefinition/gp-Encounter")
    HTTPNictizNlFhirStructureDefinitionGpEncounter("http://nictiz.nl/fhir/StructureDefinition/gp-Encounter"),
}

@Serializable
@Parcelize
data class GpEncounterReport(
    val author: List<MgoReference>? = null,
    val date: String? = null,
    val encounter: MgoReference? = null,
    val fhirVersion: FhirVersionR3,
    val id: String? = null,
    val identifier: MgoIdentifier? = null,
    val profile: GpEncounterReportProfile,
    @SerialName("referenceId")
    val referenceID: String,
    val resourceType: String? = null,
    val section: List<Section>? = null,
    val status: String? = null,
    val title: String? = null,
    val type: List<MgoCoding>? = null,
) : Parcelable

@Serializable
enum class GpEncounterReportProfile(val value: String) {
    @SerialName("http://nictiz.nl/fhir/StructureDefinition/gp-EncounterReport")
    HTTPNictizNlFhirStructureDefinitionGpEncounterReport("http://nictiz.nl/fhir/StructureDefinition/gp-EncounterReport"),
}

@Serializable
@Parcelize
data class Section(
    val code: MgoCodeableConcept? = null,
    val entry: List<MgoReference>? = null,
) : Parcelable

@Serializable
@Parcelize
data class GpJournalEntry(
    val code: MgoCodeableConcept? = null,
    val context: MgoReference? = null,
    val effectiveDateTime: String? = null,
    val effectivePeriod: MgoPeriod? = null,
    val fhirVersion: FhirVersionR3,
    @SerialName("ICPC_E")
    val icpcE: IcpcE,
    @SerialName("ICPC_S")
    val icpcS: IcpcS,
    val id: String? = null,
    val identifier: List<MgoIdentifier>? = null,
    val performer: List<MgoReference>? = null,
    val profile: GpJournalEntryProfile,
    @SerialName("referenceId")
    val referenceID: String,
    val resourceType: String? = null,
    val status: String? = null,
    val valueString: String? = null,
) : Parcelable

@Serializable
@Parcelize
data class IcpcE(
    val valueCodeableConcept: MgoCodeableConcept? = null,
) : Parcelable

@Serializable
@Parcelize
data class IcpcS(
    val valueCodeableConcept: MgoCodeableConcept? = null,
) : Parcelable

@Serializable
enum class GpJournalEntryProfile(val value: String) {
    @SerialName("http://nictiz.nl/fhir/StructureDefinition/gp-JournalEntry")
    HTTPNictizNlFhirStructureDefinitionGpJournalEntry("http://nictiz.nl/fhir/StructureDefinition/gp-JournalEntry"),
}

@Serializable
@Parcelize
data class GpLaboratoryResult(
    val basedOn: List<MgoReference>? = null,
    val code: MgoCodeableConcept? = null,
    val comment: String? = null,
    val context: MgoReference? = null,
    val effectiveDateTime: String? = null,
    val effectivePeriod: MgoPeriod? = null,
    val fhirVersion: FhirVersionR3,
    val id: String? = null,
    val identifier: List<MgoIdentifier>? = null,
    val interpretation: MgoCodeableConcept? = null,
    val laboratoryTestResultCode: List<MgoCodeableConcept>? = null,
    val method: MgoCodeableConcept? = null,
    val performer: List<MgoReference>? = null,
    val profile: GpLaboratoryResultProfile,
    @SerialName("referenceId")
    val referenceID: String,
    val referenceRange: List<GpLaboratoryResultReferenceRange>? = null,
    val related: List<GpLaboratoryResultRelated>? = null,
    val resourceType: String? = null,
    val resultType: List<MgoCodeableConcept>? = null,
    val specimen: MgoReference? = null,
    val status: GpLaboratoryResultStatus? = null,
    val subject: MgoReference? = null,
    val valueBoolean: Boolean? = null,
    val valueCodeableConcept: MgoCodeableConcept? = null,
    val valueDateTime: String? = null,
    val valuePeriod: MgoPeriod? = null,
    val valueQuantity: MgoDuration? = null,
    val valueRange: MgoRange? = null,
    val valueRatio: MgoRatio? = null,
    val valueString: String? = null,
) : Parcelable

@Serializable
enum class GpLaboratoryResultProfile(val value: String) {
    @SerialName("http://nictiz.nl/fhir/StructureDefinition/gp-LaboratoryResult")
    HTTPNictizNlFhirStructureDefinitionGpLaboratoryResult("http://nictiz.nl/fhir/StructureDefinition/gp-LaboratoryResult"),
}

@Serializable
@Parcelize
data class GpLaboratoryResultReferenceRange(
    val age: MgoRange? = null,
    val appliesTo: List<MgoCodeableConcept>? = null,
    val high: MgoDuration? = null,
    val low: MgoDuration? = null,
    val type: MgoCodeableConcept? = null,
) : Parcelable

@Serializable
@Parcelize
data class GpLaboratoryResultRelated(
    val target: MgoReference? = null,
) : Parcelable

@Serializable
enum class GpLaboratoryResultStatus(val value: String) {
    @SerialName("amended")
    Amended("amended"),

    @SerialName("cancelled")
    Cancelled("cancelled"),

    @SerialName("corrected")
    Corrected("corrected"),

    @SerialName("entered-in-error")
    EnteredInError("entered-in-error"),

    @SerialName("final")
    Final("final"),

    @SerialName("preliminary")
    Preliminary("preliminary"),

    @SerialName("registered")
    Registered("registered"),

    @SerialName("unknown")
    Unknown("unknown"),
}

@Serializable
@Parcelize
data class MgoRatio(
    val denominator: MgoDuration? = null,
    val numerator: MgoDuration? = null,
) : Parcelable

@Serializable
@Parcelize
data class IheMhdMinimalDocumentReference(
    val author: List<MgoReference>? = null,
    @SerialName("class")
    val iheMhdMinimalDocumentReferenceClass: MgoCodeableConcept? = null,
    val content: IheMhdMinimalDocumentReferenceContent,
    val fhirVersion: FhirVersionR3,
    val id: String? = null,
    val indexed: String? = null,
    val masterIdentifier: MgoIdentifier? = null,
    val profile: IheMhdMinimalDocumentReferenceProfile,
    @SerialName("referenceId")
    val referenceID: String,
    val resourceType: String? = null,
    val securityLabel: List<MgoCodeableConcept>? = null,
    val status: IheMhdMinimalDocumentReferenceStatus? = null,
    val subject: MgoReference? = null,
    val type: MgoCodeableConcept? = null,
) : Parcelable

@Serializable
@Parcelize
data class IheMhdMinimalDocumentReferenceContent(
    val attachment: Attachment? = null,
) : Parcelable

@Serializable
@Parcelize
data class Attachment(
    val contentType: String? = null,
    val creation: String? = null,
    val data: String? = null,
    val hash: String? = null,
    val language: String? = null,
    val size: Double? = null,
    val title: String? = null,
    val url: String? = null,
) : Parcelable

@Serializable
enum class IheMhdMinimalDocumentReferenceProfile(val value: String) {
    @SerialName("http://nictiz.nl/fhir/StructureDefinition/IHE.MHD.Minimal.DocumentReference")
    HTTPNictizNlFhirStructureDefinitionIHEMHDMinimalDocumentReference(
        "http://nictiz.nl/fhir/StructureDefinition/IHE.MHD.Minimal.DocumentReference",
    ),
}

@Serializable
enum class IheMhdMinimalDocumentReferenceStatus(val value: String) {
    @SerialName("current")
    Current("current"),

    @SerialName("entered-in-error")
    EnteredInError("entered-in-error"),

    @SerialName("superseded")
    Superseded("superseded"),
}

@Serializable
@Parcelize
data class MultipleGroupedValues(
    val display: List<List<String>>? = null,
    val label: String,
    val type: MultipleGroupedValuesType,
) : Parcelable

@Serializable
enum class MultipleGroupedValuesType(val value: String) {
    @SerialName("MULTIPLE_GROUPED_VALUES")
    MultipleGroupedValues("MULTIPLE_GROUPED_VALUES"),
}

@Serializable
@Parcelize
data class MultipleValues(
    val display: List<String>? = null,
    val label: String,
    val type: MultipleValuesType,
) : Parcelable

@Serializable
enum class MultipleValuesType(val value: String) {
    @SerialName("MULTIPLE_VALUES")
    MultipleValues("MULTIPLE_VALUES"),
}

@Serializable
@Parcelize
data class NlCoreObservation(
    val bodySite: MgoCodeableConcept? = null,
    val category: List<MgoCodeableConcept>? = null,
    val comment: String? = null,
    val context: MgoReference? = null,
    val dataAbsentReason: MgoCodeableConcept? = null,
    val effectiveDateTime: String? = null,
    val effectivePeriod: MgoPeriod? = null,
    val fhirVersion: FhirVersionR3,
    val id: String? = null,
    val identifier: List<MgoIdentifier>? = null,
    val method: MgoCodeableConcept? = null,
    val profile: NlCoreObservationProfile,
    @SerialName("referenceId")
    val referenceID: String,
    val resourceType: String? = null,
    val status: GpLaboratoryResultStatus? = null,
    val subject: MgoReference? = null,
    val valueQuantity: MgoDuration? = null,
) : Parcelable

@Serializable
enum class NlCoreObservationProfile(val value: String) {
    @SerialName("http://fhir.nl/fhir/StructureDefinition/nl-core-observation")
    HTTPFhirNlFhirStructureDefinitionNlCoreObservation("http://fhir.nl/fhir/StructureDefinition/nl-core-observation"),
}

@Serializable
@Parcelize
data class NlCoreOrganization(
    val address: List<NlCoreAddress>? = null,
    val departmentSpecialty: List<MgoCodeableConcept>? = null,
    val fhirVersion: FhirVersionR3,
    val id: String? = null,
    val identifier: List<MgoIdentifier>? = null,
    val name: String? = null,
    val organizationType: List<MgoCodeableConcept>? = null,
    val profile: NlCoreOrganizationProfile,
    @SerialName("referenceId")
    val referenceID: String,
    val resourceType: String? = null,
    val telecom: List<NlCoreContactpoint>? = null,
) : Parcelable

@Serializable
@Parcelize
data class NlCoreAddress(
    val city: String? = null,
    val country: String? = null,
    val district: String? = null,
    val line: List<String>? = null,
    val period: MgoPeriod? = null,
    val postalCode: String? = null,
    val state: String? = null,
    val text: String? = null,
    val type: String? = null,
    val use: String? = null,
) : Parcelable

@Serializable
enum class NlCoreOrganizationProfile(val value: String) {
    @SerialName("http://fhir.nl/fhir/StructureDefinition/nl-core-organization")
    HTTPFhirNlFhirStructureDefinitionNlCoreOrganization("http://fhir.nl/fhir/StructureDefinition/nl-core-organization"),
}

@Serializable
@Parcelize
data class NlCoreContactpoint(
    val period: MgoPeriod? = null,
    val rank: Double? = null,
    val system: String? = null,
    val use: String? = null,
    val value: String? = null,
) : Parcelable

@Serializable
@Parcelize
data class NlCorePatient(
    val active: Boolean? = null,
    val address: List<NlCoreAddress>? = null,
    val birthDate: String? = null,
    val communication: List<Communication>? = null,
    val contact: List<Contact>? = null,
    val deceased: Boolean? = null,
    val deceasedDateTime: String? = null,
    val fhirVersion: FhirVersionR3,
    val gender: Gender? = null,
    val generalPractitioner: List<MgoReference>? = null,
    val id: String? = null,
    val identifier: List<MgoIdentifier>? = null,
    val link: List<Link>? = null,
    val managingOrganization: MgoReference? = null,
    val maritalStatus: MgoCodeableConcept? = null,
    val multipleBirth: Boolean? = null,
    val multipleBirthInteger: Double? = null,
    val name: List<NlCoreHumanname>? = null,
    val photo: List<Attachment>? = null,
    val profile: NlCorePatientProfile,
    @SerialName("referenceId")
    val referenceID: String,
    val resourceType: String? = null,
    val telecom: List<NlCoreContactpoint>? = null,
) : Parcelable

@Serializable
@Parcelize
data class Communication(
    val language: MgoCodeableConcept? = null,
    val preferred: Boolean? = null,
) : Parcelable

@Serializable
@Parcelize
data class Contact(
    val address: NlCoreAddress,
    val gender: String? = null,
    val name: NlCoreHumanname,
    val organization: MgoReference? = null,
    val period: MgoPeriod? = null,
    val relationship: List<MgoCodeableConcept>,
    val telecom: List<NlCoreContactpoint>,
) : Parcelable

@Serializable
@Parcelize
data class NlCoreHumanname(
    val family: String? = null,
    val given: List<String>? = null,
    val period: MgoPeriod? = null,
    val prefix: List<String>? = null,
    val suffix: List<String>? = null,
    val text: String? = null,
    val use: String? = null,
) : Parcelable

@Serializable
enum class Gender(val value: String) {
    @SerialName("female")
    Female("female"),

    @SerialName("male")
    Male("male"),

    @SerialName("other")
    Other("other"),

    @SerialName("unknown")
    Unknown("unknown"),
}

@Serializable
@Parcelize
data class Link(
    val other: MgoReference? = null,
    val type: String? = null,
) : Parcelable

@Serializable
enum class NlCorePatientProfile(val value: String) {
    @SerialName("http://fhir.nl/fhir/StructureDefinition/nl-core-patient")
    HTTPFhirNlFhirStructureDefinitionNlCorePatient("http://fhir.nl/fhir/StructureDefinition/nl-core-patient"),
}

@Serializable
@Parcelize
data class NlCorePractitioner(
    val address: List<NlCoreAddress>? = null,
    val fhirVersion: FhirVersionR3,
    val id: String? = null,
    val identifier: List<MgoIdentifier>? = null,
    val name: List<NlCoreHumanname>? = null,
    val profile: NlCorePractitionerProfile,
    @SerialName("referenceId")
    val referenceID: String,
    val resourceType: String? = null,
    val telecom: List<NlCoreContactpoint>? = null,
) : Parcelable

@Serializable
enum class NlCorePractitionerProfile(val value: String) {
    @SerialName("http://fhir.nl/fhir/StructureDefinition/nl-core-practitioner")
    HTTPFhirNlFhirStructureDefinitionNlCorePractitioner("http://fhir.nl/fhir/StructureDefinition/nl-core-practitioner"),
}

@Serializable
@Parcelize
data class NlCorePractitionerRole(
    val fhirVersion: FhirVersionR3,
    val id: String? = null,
    val identifier: List<MgoIdentifier>? = null,
    val organization: MgoReference? = null,
    val profile: NlCorePractitionerRoleProfile,
    @SerialName("referenceId")
    val referenceID: String,
    val resourceType: String? = null,
    val specialty: List<MgoCodeableConcept>? = null,
    val telecom: List<NlCoreContactpoint>? = null,
) : Parcelable

@Serializable
enum class NlCorePractitionerRoleProfile(val value: String) {
    @SerialName("http://fhir.nl/fhir/StructureDefinition/nl-core-practitionerrole")
    HTTPFhirNlFhirStructureDefinitionNlCorePractitionerrole("http://fhir.nl/fhir/StructureDefinition/nl-core-practitionerrole"),
}

@Serializable
@Parcelize
data class R4NlCoreHealtcareProvider(
    val address: R4NlCoreAddressInformation,
    val emailAddresses: List<R4NlCoreContactInformationEmailAddresses>? = null,
    val fhirVersion: FhirVersionR4,
    val id: String? = null,
    val identifier: List<MgoIdentifier>? = null,
    val managingOrganization: MgoReference? = null,
    val name: String? = null,
    val profile: R4NlCoreHealtcareProviderProfile,
    @SerialName("referenceId")
    val referenceID: String,
    val resourceType: String? = null,
    val telephoneNumbers: List<R4NlCoreContactInformationTelephoneNumbers>? = null,
) : Parcelable

@Serializable
@Parcelize
data class R4NlCoreAddressInformation(
    val additionalInformation: String? = null,
    val addressType: MgoCodeableConcept? = null,
    val city: String? = null,
    val country: String? = null,
    val countryCode: MgoCodeableConcept? = null,
    val district: String? = null,
    val houseNumber: String? = null,
    val houseNumberAddition: String? = null,
    val houseNumberIndication: String? = null,
    val line: String? = null,
    val period: MgoPeriod? = null,
    val postalCode: String? = null,
    val streetName: String? = null,
) : Parcelable

@Serializable
@Parcelize
data class R4NlCoreContactInformationEmailAddresses(
    val system: R4NlCoreContactInformationEmailAddressesSystem,
    val use: String? = null,
    val value: String? = null,
) : Parcelable

@Serializable
enum class R4NlCoreContactInformationEmailAddressesSystem(val value: String) {
    @SerialName("email")
    Email("email"),
}

@Serializable
enum class FhirVersionR4(val value: String) {
    @SerialName("R4")
    R4("R4"),
}

@Serializable
enum class R4NlCoreHealtcareProviderProfile(val value: String) {
    @SerialName("http://nictiz.nl/fhir/StructureDefinition/nl-core-HealthcareProvider")
    HTTPNictizNlFhirStructureDefinitionNlCoreHealthcareProvider("http://nictiz.nl/fhir/StructureDefinition/nl-core-HealthcareProvider"),
}

@Serializable
@Parcelize
data class R4NlCoreContactInformationTelephoneNumbers(
    val comment: String? = null,
    val system: R4NlCoreContactInformationTelephoneNumbersSystem,
    val telecomType: MgoCodeableConcept? = null,
    val use: String? = null,
    val value: String? = null,
) : Parcelable

@Serializable
enum class R4NlCoreContactInformationTelephoneNumbersSystem(val value: String) {
    @SerialName("phone")
    Phone("phone"),
}

@Serializable
@Parcelize
data class R4NlCoreHealthcareProviderOrganization(
    val address: List<R4NlCoreAddressInformation>? = null,
    val departmentSpecialty: List<MgoCodeableConcept>? = null,
    val emailAddresses: List<R4NlCoreContactInformationEmailAddresses>? = null,
    val fhirVersion: FhirVersionR4,
    val id: String? = null,
    val identifier: List<MgoIdentifier>? = null,
    val name: String? = null,
    val organizationType: List<MgoCodeableConcept>? = null,
    val profile: R4NlCoreHealthcareProviderOrganizationProfile,
    @SerialName("referenceId")
    val referenceID: String,
    val resourceType: String? = null,
    val telephoneNumbers: List<R4NlCoreContactInformationTelephoneNumbers>? = null,
) : Parcelable

@Serializable
enum class R4NlCoreHealthcareProviderOrganizationProfile(val value: String) {
    @SerialName("http://nictiz.nl/fhir/StructureDefinition/nl-core-HealthcareProvider-Organization")
    HTTPNictizNlFhirStructureDefinitionNlCoreHealthcareProviderOrganization(
        "http://nictiz.nl/fhir/StructureDefinition/nl-core-HealthcareProvider-Organization",
    ),
}

@Serializable
@Parcelize
data class R4NlCoreHealthProfessionalPractitioner(
    val address: List<R4NlCoreAddressInformation>? = null,
    val birthDate: String? = null,
    val communication: List<MgoCodeableConcept>? = null,
    val emailAddresses: List<R4NlCoreContactInformationEmailAddresses>? = null,
    val fhirVersion: FhirVersionR4,
    val gender: Gender? = null,
    val id: String? = null,
    val identifier: List<MgoIdentifier>? = null,
    val name: List<R4NlCoreHealthProfessionalPractitionerName>? = null,
    val profile: R4NlCoreHealthProfessionalPractitionerProfile,
    val qualification: List<Qualification>? = null,
    @SerialName("referenceId")
    val referenceID: String,
    val resourceType: String? = null,
    val telephoneNumbers: List<R4NlCoreContactInformationTelephoneNumbers>? = null,
) : Parcelable

@Serializable
@Parcelize
data class R4NlCoreHealthProfessionalPractitionerName(
    val family: String? = null,
    val given: List<String>? = null,
    val givenInitials: List<String>? = null,
    val givenNames: List<String>? = null,
    val nameUsage: String? = null,
    val period: MgoPeriod? = null,
    val prefix: List<String>? = null,
    val suffix: List<String>? = null,
    val text: String? = null,
    val use: NameUse,
) : Parcelable

@Serializable
enum class NameUse(val value: String) {
    @SerialName("official")
    Official("official"),

    @SerialName("usual")
    Usual("usual"),
}

@Serializable
enum class R4NlCoreHealthProfessionalPractitionerProfile(val value: String) {
    @SerialName("http://nictiz.nl/fhir/StructureDefinition/nl-core-HealthProfessional-Practitioner")
    HTTPNictizNlFhirStructureDefinitionNlCoreHealthProfessionalPractitioner(
        "http://nictiz.nl/fhir/StructureDefinition/nl-core-HealthProfessional-Practitioner",
    ),
}

@Serializable
@Parcelize
data class Qualification(
    val code: MgoCodeableConcept? = null,
    val identifier: List<MgoIdentifier>? = null,
    val issuer: MgoReference? = null,
    val period: MgoPeriod? = null,
) : Parcelable

@Serializable
@Parcelize
data class R4NlCoreNameInformation(
    val family: String? = null,
    val given: List<String>? = null,
    val givenInitials: List<String>? = null,
    val givenNames: List<String>? = null,
    val nameUsage: String? = null,
    val period: MgoPeriod? = null,
    val prefix: List<String>? = null,
    val suffix: List<String>? = null,
    val text: String? = null,
    val use: R4NlCoreNameInformationUse,
) : Parcelable

@Serializable
enum class R4NlCoreNameInformationUse(val value: String) {
    @SerialName("official")
    Official("official"),
}

@Serializable
@Parcelize
data class R4NlCoreNameInformationGiven(
    val given: List<String>? = null,
    val period: MgoPeriod? = null,
    val text: String? = null,
    val use: R4NlCoreNameInformationGivenUse,
) : Parcelable

@Serializable
enum class R4NlCoreNameInformationGivenUse(val value: String) {
    @SerialName("usual")
    Usual("usual"),
}

@Serializable
@Parcelize
data class R4NlCoreHealthProfessionalPractitionerRole(
    val emailAddresses: List<R4NlCoreContactInformationEmailAddresses>? = null,
    val fhirVersion: FhirVersionR4,
    val id: String? = null,
    val location: List<MgoReference>? = null,
    val organization: MgoReference? = null,
    val practitioner: MgoReference? = null,
    val profile: R4NlCoreHealthProfessionalPractitionerRoleProfile,
    @SerialName("referenceId")
    val referenceID: String,
    val resourceType: String? = null,
    val speciality: List<MgoCodeableConcept>? = null,
    val telephoneNumbers: List<R4NlCoreContactInformationTelephoneNumbers>? = null,
) : Parcelable

@Serializable
enum class R4NlCoreHealthProfessionalPractitionerRoleProfile(val value: String) {
    @SerialName("http://nictiz.nl/fhir/StructureDefinition/nl-core-HealthProfessional-PractitionerRole")
    HTTPNictizNlFhirStructureDefinitionNlCoreHealthProfessionalPractitionerRole(
        "http://nictiz.nl/fhir/StructureDefinition/nl-core-HealthProfessional-PractitionerRole",
    ),
}

@Serializable
@Parcelize
data class R4NlCorePatient(
    val address: List<R4NlCoreAddressInformation>? = null,
    val birthDate: String? = null,
    val deceased: Boolean? = null,
    val deceasedDateTime: String? = null,
    val fhirVersion: FhirVersionR4,
    val gender: Gender? = null,
    val generalPractitioner: List<MgoReference>? = null,
    val id: String? = null,
    val identifier: List<MgoIdentifier>? = null,
    val managingOrganization: MgoReference? = null,
    val maritalStatus: MgoCodeableConcept? = null,
    val multipleBirth: Boolean? = null,
    val name: List<R4NlCorePatientName>? = null,
    val profile: R4NlCorePatientProfile,
    @SerialName("referenceId")
    val referenceID: String,
    val resourceType: String? = null,
) : Parcelable

@Serializable
@Parcelize
data class R4NlCorePatientName(
    val family: String? = null,
    val given: List<String>? = null,
    val givenInitials: List<String>? = null,
    val givenNames: List<String>? = null,
    val nameUsage: String? = null,
    val period: MgoPeriod? = null,
    val prefix: List<String>? = null,
    val suffix: List<String>? = null,
    val text: String? = null,
    val use: NameUse,
) : Parcelable

@Serializable
enum class R4NlCorePatientProfile(val value: String) {
    @SerialName("http://nictiz.nl/fhir/StructureDefinition/nl-core-Patient")
    HTTPNictizNlFhirStructureDefinitionNlCorePatient("http://nictiz.nl/fhir/StructureDefinition/nl-core-Patient"),
}

@Serializable
@Parcelize
data class R4NlCorePharmaceuticalProduct(
    val amount: MgoRatio? = null,
    val batch: Batch,
    val code: MgoCodeableConcept? = null,
    val description: String? = null,
    val fhirVersion: FhirVersionR4,
    val form: MgoCodeableConcept? = null,
    val id: String? = null,
    val identifier: List<MgoIdentifier>? = null,
    val ingredient: List<Ingredient>? = null,
    val manufacturer: MgoReference? = null,
    val name: String? = null,
    val profile: R4NlCorePharmaceuticalProductProfile,
    @SerialName("referenceId")
    val referenceID: String,
    val resourceType: String? = null,
    val status: R4NlCorePharmaceuticalProductStatus? = null,
) : Parcelable

@Serializable
@Parcelize
data class Batch(
    val expirationDate: String? = null,
    val lotNumber: String? = null,
) : Parcelable

@Serializable
@Parcelize
data class Ingredient(
    val itemCodeableConcept: MgoCodeableConcept? = null,
    val itemReference: MgoReference? = null,
    val strength: MgoRatio? = null,
) : Parcelable

@Serializable
enum class R4NlCorePharmaceuticalProductProfile(val value: String) {
    @SerialName("http://nictiz.nl/fhir/StructureDefinition/nl-core-PharmaceuticalProduct")
    HTTPNictizNlFhirStructureDefinitionNlCorePharmaceuticalProduct(
        "http://nictiz.nl/fhir/StructureDefinition/nl-core-PharmaceuticalProduct",
    ),
}

@Serializable
enum class R4NlCorePharmaceuticalProductStatus(val value: String) {
    @SerialName("active")
    Active("active"),

    @SerialName("entered-in-error")
    EnteredInError("entered-in-error"),

    @SerialName("inactive")
    Inactive("inactive"),
}

@Serializable
@Parcelize
data class R4NlCoreVaccinationEvent(
    val doseQuantity: MgoDuration? = null,
    val fhirVersion: FhirVersionR4,
    val id: String? = null,
    val identifier: List<MgoIdentifier>? = null,
    val location: MgoReference? = null,
    val note: List<MgoAnnotation>? = null,
    val occurrenceDateTime: String? = null,
    val patient: MgoReference? = null,
    val performer: List<MgoReference>? = null,
    val pharmaceuticalProduct: MgoReference? = null,
    val profile: R4NlCoreVaccinationEventProfile,
    val protocolApplied: List<ProtocolApplied>? = null,
    @SerialName("referenceId")
    val referenceID: String,
    val resourceType: String? = null,
    val route: MgoCodeableConcept? = null,
    val site: MgoCodeableConcept? = null,
    val status: String? = null,
    val vaccinationIndication: List<MgoCodeableConcept>? = null,
    val vaccinationMotive: List<MgoCodeableConcept>? = null,
    val vaccineCode: MgoCodeableConcept? = null,
) : Parcelable

@Serializable
@Parcelize
data class MgoAnnotation(
    val author: MgoReference? = null,
    val text: String? = null,
    val time: String? = null,
) : Parcelable

@Serializable
enum class R4NlCoreVaccinationEventProfile(val value: String) {
    @SerialName("http://nictiz.nl/fhir/StructureDefinition/nl-core-Vaccination-event")
    HTTPNictizNlFhirStructureDefinitionNlCoreVaccinationEvent("http://nictiz.nl/fhir/StructureDefinition/nl-core-Vaccination-event"),
}

@Serializable
@Parcelize
data class ProtocolApplied(
    val authority: MgoReference? = null,
    val doseNumberPositiveInt: Double? = null,
    val doseNumberString: String? = null,
    val seriesDosesPositiveInt: Double? = null,
    val seriesDosesString: String? = null,
    val targetDisease: List<MgoCodeableConcept>? = null,
) : Parcelable

@Serializable
@Parcelize
data class ReferenceLink(
    val label: String,
    val reference: String,
    val type: ReferenceLinkType,
) : Parcelable

@Serializable
enum class ReferenceLinkType(val value: String) {
    @SerialName("REFERENCE_LINK")
    ReferenceLink("REFERENCE_LINK"),
}

@Serializable
@Parcelize
data class ReferenceValue(
    val display: String? = null,
    val label: String,
    val reference: String? = null,
    val type: ReferenceValueType,
) : Parcelable

@Serializable
enum class ReferenceValueType(val value: String) {
    @SerialName("REFERENCE_VALUE")
    ReferenceValue("REFERENCE_VALUE"),
}

@Serializable
@Parcelize
data class SingleValue(
    val display: String? = null,
    val label: String,
    val type: SingleValueType,
) : Parcelable

@Serializable
enum class SingleValueType(val value: String) {
    @SerialName("SINGLE_VALUE")
    SingleValue("SINGLE_VALUE"),
}

@Serializable
@Parcelize
data class UISchema(
    val children: List<UISchemaGroup>,
    val label: String,
) : Parcelable

@Serializable
@Parcelize
data class UISchemaGroup(
    val children: List<UIElement>,
    val label: String? = null,
) : Parcelable

@Serializable
@Parcelize
data class UIElement(
    @Serializable(with = UIElementDisplaySerializer::class)
    val display: UIElementDisplay? = null,
    val label: String,
    val type: UIElementType,
    val reference: String? = null,
    val url: String? = null,
) : Parcelable

@Serializable
@Parcelize
sealed class UIElementDisplay : Parcelable {
    @Parcelize
    class StringValue(val value: String) : UIElementDisplay()

    @Parcelize
    class UnionArrayValue(val value: List<DisplayElement>) : UIElementDisplay()
}

@Serializable
@Parcelize
sealed class DisplayElement : Parcelable {
    @Parcelize
    class StringArrayValue(val value: List<String>) : DisplayElement()

    @Parcelize
    class StringValue(val value: String) : DisplayElement()
}

@Serializable
enum class UIElementType(val value: String) {
    @SerialName("DOWNLOAD_BINARY")
    DownloadBinary("DOWNLOAD_BINARY"),

    @SerialName("DOWNLOAD_LINK")
    DownloadLink("DOWNLOAD_LINK"),

    @SerialName("MULTIPLE_GROUPED_VALUES")
    MultipleGroupedValues("MULTIPLE_GROUPED_VALUES"),

    @SerialName("MULTIPLE_VALUES")
    MultipleValues("MULTIPLE_VALUES"),

    @SerialName("REFERENCE_LINK")
    ReferenceLink("REFERENCE_LINK"),

    @SerialName("REFERENCE_VALUE")
    ReferenceValue("REFERENCE_VALUE"),

    @SerialName("SINGLE_VALUE")
    SingleValue("SINGLE_VALUE"),
}

@Serializable
@Parcelize
data class ZibAdministrationAgreement(
    val additionalInformation: MgoCodeableConcept? = null,
    val agreementReason: String? = null,
    val authoredOn: String? = null,
    val category: MgoCodeableConcept? = null,
    val daysSupply: MgoDuration? = null,
    val dossageInstruction: List<ZibInstructionsForUse>? = null,
    val fhirVersion: FhirVersionR3,
    val id: String? = null,
    val identifier: List<MgoIdentifier>? = null,
    val medicationReference: MgoReference? = null,
    val medicationTreatment: MgoIdentifier? = null,
    val note: List<MgoAnnotation>? = null,
    val profile: ZibAdministrationAgreementProfile,
    val quantity: MgoDuration? = null,
    @SerialName("referenceId")
    val referenceID: String,
    val repeatPeriodCyclicalSchedule: MgoDuration? = null,
    val resourceType: String? = null,
    val status: ZibAdministrationAgreementStatus? = null,
    val stopType: MgoCodeableConcept? = null,
    val usageDuration: MgoDuration? = null,
) : Parcelable

@Serializable
@Parcelize
data class ZibInstructionsForUse(
    val additionalInstruction: List<MgoCodeableConcept>? = null,
    val asNeeded: MgoCodeableConcept? = null,
    val doseQuantity: MgoDuration? = null,
    val doseRange: MgoRange? = null,
    val maxDosePerPeriod: MgoRatio? = null,
    val rateQuantity: MgoDuration? = null,
    val rateRange: MgoRange? = null,
    val rateRatio: MgoRatio? = null,
    val route: MgoCodeableConcept? = null,
    val sequence: Double? = null,
    val text: String? = null,
    val timing: ZibAdministrationSchedule,
) : Parcelable

@Serializable
@Parcelize
data class ZibAdministrationSchedule(
    val repeat: Repeat,
) : Parcelable

@Serializable
@Parcelize
data class Repeat(
    val boundsDuration: MgoDuration? = null,
    val boundsPeriod: MgoPeriod? = null,
    val boundsRange: MgoRange? = null,
    val dayOfWeek: List<String>? = null,
    val duration: Double? = null,
    val durationUnit: String? = null,
    val frequency: Double? = null,
    val frequencyMax: Double? = null,
    val period: Double? = null,
    val periodUnit: String? = null,
    val timeOfDay: List<String>? = null,
    @SerialName("when")
    val repeatWhen: List<String>? = null,
) : Parcelable

@Serializable
enum class ZibAdministrationAgreementProfile(val value: String) {
    @SerialName("http://nictiz.nl/fhir/StructureDefinition/zib-AdministrationAgreement")
    HTTPNictizNlFhirStructureDefinitionZibAdministrationAgreement("http://nictiz.nl/fhir/StructureDefinition/zib-AdministrationAgreement"),
}

@Serializable
enum class ZibAdministrationAgreementStatus(val value: String) {
    @SerialName("completed")
    Completed("completed"),

    @SerialName("entered-in-error")
    EnteredInError("entered-in-error"),

    @SerialName("in-progress")
    InProgress("in-progress"),

    @SerialName("on-hold")
    OnHold("on-hold"),

    @SerialName("preparation")
    Preparation("preparation"),

    @SerialName("stopped")
    Stopped("stopped"),
}

@Serializable
@Parcelize
data class ZibAdvanceDirective(
    val category: List<MgoCodeableConcept>? = null,
    val comment: String? = null,
    val consentingParty: List<MgoReference>? = null,
    val dateTime: String? = null,
    val disorder: MgoReference? = null,
    val fhirVersion: FhirVersionR3,
    val id: String? = null,
    val profile: ZibAdvanceDirectiveProfile,
    @SerialName("referenceId")
    val referenceID: String,
    val resourceType: String? = null,
    val source: Source,
) : Parcelable

@Serializable
enum class ZibAdvanceDirectiveProfile(val value: String) {
    @SerialName("http://nictiz.nl/fhir/StructureDefinition/zib-AdvanceDirective")
    HTTPNictizNlFhirStructureDefinitionZibAdvanceDirective("http://nictiz.nl/fhir/StructureDefinition/zib-AdvanceDirective"),
}

@Serializable
@Parcelize
data class Source(
    val attachment: Attachment,
    val identifier: MgoIdentifier? = null,
    val reference: MgoReference? = null,
) : Parcelable

@Serializable
@Parcelize
data class ZibAlcoholUse(
    val bodySite: MgoCodeableConcept? = null,
    val category: List<MgoCodeableConcept>? = null,
    val comment: String? = null,
    val context: MgoReference? = null,
    val dataAbsentReason: MgoCodeableConcept? = null,
    val effectivePeriod: MgoPeriod? = null,
    val fhirVersion: FhirVersionR3,
    val id: String? = null,
    val identifier: List<MgoIdentifier>? = null,
    val method: MgoCodeableConcept? = null,
    val profile: ZibAlcoholUseProfile,
    @SerialName("referenceId")
    val referenceID: String,
    val resourceType: String? = null,
    val status: GpLaboratoryResultStatus? = null,
    val subject: MgoReference? = null,
    val valueQuantity: MgoDuration? = null,
) : Parcelable

@Serializable
enum class ZibAlcoholUseProfile(val value: String) {
    @SerialName("http://nictiz.nl/fhir/StructureDefinition/zib-AlcoholUse")
    HTTPNictizNlFhirStructureDefinitionZibAlcoholUse("http://nictiz.nl/fhir/StructureDefinition/zib-AlcoholUse"),
}

@Serializable
@Parcelize
data class ZibAlert(
    val author: MgoReference? = null,
    val category: MgoCodeableConcept? = null,
    val code: MgoCodeableConcept? = null,
    val encounter: MgoReference? = null,
    val fhirVersion: FhirVersionR3,
    val id: String? = null,
    val identifier: List<MgoIdentifier>? = null,
    val period: MgoPeriod? = null,
    val profile: ZibAlertProfile,
    @SerialName("referenceId")
    val referenceID: String,
    val resourceType: String? = null,
    val status: R4NlCorePharmaceuticalProductStatus? = null,
    val subject: MgoReference? = null,
) : Parcelable

@Serializable
enum class ZibAlertProfile(val value: String) {
    @SerialName("http://nictiz.nl/fhir/StructureDefinition/zib-Alert")
    HTTPNictizNlFhirStructureDefinitionZibAlert("http://nictiz.nl/fhir/StructureDefinition/zib-Alert"),
}

@Serializable
@Parcelize
data class ZibAllergyIntolerance(
    val category: List<String>? = null,
    val clinicalStatus: ZibAllergyIntoleranceClinicalStatus? = null,
    val code: MgoCodeableConcept? = null,
    val criticality: Criticality? = null,
    val fhirVersion: FhirVersionR3,
    val id: String? = null,
    val identifier: List<MgoIdentifier>? = null,
    val patient: MgoReference? = null,
    val profile: ZibAllergyIntoleranceProfile,
    @SerialName("referenceId")
    val referenceID: String,
    val resourceType: String? = null,
    val type: ZibAllergyIntoleranceType? = null,
    val verificationStatus: ZibAllergyIntoleranceVerificationStatus? = null,
) : Parcelable

@Serializable
enum class ZibAllergyIntoleranceClinicalStatus(val value: String) {
    @SerialName("active")
    Active("active"),

    @SerialName("inactive")
    Inactive("inactive"),

    @SerialName("resolved")
    Resolved("resolved"),
}

@Serializable
enum class Criticality(val value: String) {
    @SerialName("high")
    High("high"),

    @SerialName("low")
    Low("low"),

    @SerialName("unable-to-assess")
    UnableToAssess("unable-to-assess"),
}

@Serializable
enum class ZibAllergyIntoleranceProfile(val value: String) {
    @SerialName("http://nictiz.nl/fhir/StructureDefinition/zib-AllergyIntolerance")
    HTTPNictizNlFhirStructureDefinitionZibAllergyIntolerance("http://nictiz.nl/fhir/StructureDefinition/zib-AllergyIntolerance"),
}

@Serializable
enum class ZibAllergyIntoleranceType(val value: String) {
    @SerialName("allergy")
    Allergy("allergy"),

    @SerialName("intolerance")
    Intolerance("intolerance"),
}

@Serializable
enum class ZibAllergyIntoleranceVerificationStatus(val value: String) {
    @SerialName("confirmed")
    Confirmed("confirmed"),

    @SerialName("entered-in-error")
    EnteredInError("entered-in-error"),

    @SerialName("refuted")
    Refuted("refuted"),

    @SerialName("unconfirmed")
    Unconfirmed("unconfirmed"),
}

@Serializable
@Parcelize
data class ZibBloodPressure(
    val averageBloodPressureLOINC: AverageBloodPressureLOINC,
    val averageBloodPressureSNOMED: AverageBloodPressureSNOMED,
    val bodySite: MgoCodeableConcept? = null,
    val category: List<MgoCodeableConcept>? = null,
    val comment: String? = null,
    val context: MgoReference? = null,
    val cuffTypeLOINC: CuffTypeLOINC,
    val cuffTypeSNOMED: CuffTypeSNOMED,
    val dataAbsentReason: MgoCodeableConcept? = null,
    val diastolicBP: DiastolicBP,
    val diastolicEndpoint: DiastolicEndpoint,
    val effectiveDateTime: String? = null,
    val effectivePeriod: MgoPeriod? = null,
    val fhirVersion: FhirVersionR3,
    val id: String? = null,
    val identifier: List<MgoIdentifier>? = null,
    val method: MgoCodeableConcept? = null,
    val positionLOINC: PositionLOINC,
    val positionSNOMED: PositionSNOMED,
    val profile: ZibBloodPressureProfile,
    @SerialName("referenceId")
    val referenceID: String,
    val resourceType: String? = null,
    val status: GpLaboratoryResultStatus? = null,
    val subject: MgoReference? = null,
    val systolicBP: SystolicBP,
    val valueQuantity: MgoDuration? = null,
) : Parcelable

@Serializable
@Parcelize
data class AverageBloodPressureLOINC(
    val valueQuantity: MgoDuration? = null,
) : Parcelable

@Serializable
@Parcelize
data class AverageBloodPressureSNOMED(
    val valueQuantity: MgoDuration? = null,
) : Parcelable

@Serializable
@Parcelize
data class CuffTypeLOINC(
    val valueCodeableConcept: MgoCodeableConcept? = null,
) : Parcelable

@Serializable
@Parcelize
data class CuffTypeSNOMED(
    val valueCodeableConcept: MgoCodeableConcept? = null,
) : Parcelable

@Serializable
@Parcelize
data class DiastolicBP(
    val valueQuantity: MgoDuration? = null,
) : Parcelable

@Serializable
@Parcelize
data class DiastolicEndpoint(
    val valueCodeableConcept: MgoCodeableConcept? = null,
) : Parcelable

@Serializable
@Parcelize
data class PositionLOINC(
    val valueCodeableConcept: MgoCodeableConcept? = null,
) : Parcelable

@Serializable
@Parcelize
data class PositionSNOMED(
    val valueCodeableConcept: MgoCodeableConcept? = null,
) : Parcelable

@Serializable
enum class ZibBloodPressureProfile(val value: String) {
    @SerialName("http://nictiz.nl/fhir/StructureDefinition/zib-BloodPressure")
    HTTPNictizNlFhirStructureDefinitionZibBloodPressure("http://nictiz.nl/fhir/StructureDefinition/zib-BloodPressure"),
}

@Serializable
@Parcelize
data class SystolicBP(
    val valueQuantity: MgoDuration? = null,
) : Parcelable

@Serializable
@Parcelize
data class ZibBodyHeight(
    val bodySite: MgoCodeableConcept? = null,
    val category: List<MgoCodeableConcept>? = null,
    val comment: String? = null,
    val context: MgoReference? = null,
    val dataAbsentReason: MgoCodeableConcept? = null,
    val effectiveDateTime: String? = null,
    val effectivePeriod: MgoPeriod? = null,
    val fhirVersion: FhirVersionR3,
    val id: String? = null,
    val identifier: List<MgoIdentifier>? = null,
    val method: MgoCodeableConcept? = null,
    val profile: ZibBodyHeightProfile,
    @SerialName("referenceId")
    val referenceID: String,
    val resourceType: String? = null,
    val status: GpLaboratoryResultStatus? = null,
    val subject: MgoReference? = null,
    val valueQuantity: MgoDuration? = null,
) : Parcelable

@Serializable
enum class ZibBodyHeightProfile(val value: String) {
    @SerialName("http://nictiz.nl/fhir/StructureDefinition/zib-BodyHeight")
    HTTPNictizNlFhirStructureDefinitionZibBodyHeight("http://nictiz.nl/fhir/StructureDefinition/zib-BodyHeight"),
}

@Serializable
@Parcelize
data class ZibBodyWeight(
    val bodySite: MgoCodeableConcept? = null,
    val category: List<MgoCodeableConcept>? = null,
    val clothing: Clothing,
    val comment: String? = null,
    val context: MgoReference? = null,
    val dataAbsentReason: MgoCodeableConcept? = null,
    val effectiveDateTime: String? = null,
    val effectivePeriod: MgoPeriod? = null,
    val fhirVersion: FhirVersionR3,
    val id: String? = null,
    val identifier: List<MgoIdentifier>? = null,
    val method: MgoCodeableConcept? = null,
    val profile: ZibBodyWeightProfile,
    @SerialName("referenceId")
    val referenceID: String,
    val resourceType: String? = null,
    val status: GpLaboratoryResultStatus? = null,
    val subject: MgoReference? = null,
    val valueQuantity: MgoDuration? = null,
) : Parcelable

@Serializable
@Parcelize
data class Clothing(
    val valueCodeableConcept: MgoCodeableConcept? = null,
) : Parcelable

@Serializable
enum class ZibBodyWeightProfile(val value: String) {
    @SerialName("http://nictiz.nl/fhir/StructureDefinition/zib-BodyWeight")
    HTTPNictizNlFhirStructureDefinitionZibBodyWeight("http://nictiz.nl/fhir/StructureDefinition/zib-BodyWeight"),
}

@Serializable
@Parcelize
data class ZibDrugUse(
    val bodySite: MgoCodeableConcept? = null,
    val category: List<MgoCodeableConcept>? = null,
    val comment: String? = null,
    val context: MgoReference? = null,
    val dataAbsentReason: MgoCodeableConcept? = null,
    val effectivePeriod: MgoPeriod? = null,
    val fhirVersion: FhirVersionR3,
    val id: String? = null,
    val identifier: List<MgoIdentifier>? = null,
    val method: MgoCodeableConcept? = null,
    val profile: ZibDrugUseProfile,
    @SerialName("referenceId")
    val referenceID: String,
    val resourceType: String? = null,
    val status: GpLaboratoryResultStatus? = null,
    val subject: MgoReference? = null,
    val valueQuantity: MgoDuration? = null,
) : Parcelable

@Serializable
enum class ZibDrugUseProfile(val value: String) {
    @SerialName("http://nictiz.nl/fhir/StructureDefinition/zib-DrugUse")
    HTTPNictizNlFhirStructureDefinitionZibDrugUse("http://nictiz.nl/fhir/StructureDefinition/zib-DrugUse"),
}

@Serializable
@Parcelize
data class ZibEncounter(
    @SerialName("class")
    val zibEncounterClass: MgoCoding? = null,
    val diagnosis: List<Diagnosis>? = null,
    val fhirVersion: FhirVersionR3,
    val hospitalization: Hospitalization,
    val id: String? = null,
    val participant: List<EncounterParticipant>? = null,
    val period: MgoPeriod? = null,
    val profile: ZibEncounterProfile,
    val reason: List<MgoCodeableConcept>? = null,
    @SerialName("referenceId")
    val referenceID: String,
    val resourceType: String? = null,
    val serviceProvider: MgoReference? = null,
) : Parcelable

@Serializable
@Parcelize
data class Diagnosis(
    val condition: MgoReference? = null,
    val rank: Double? = null,
    val role: MgoCodeableConcept? = null,
) : Parcelable

@Serializable
@Parcelize
data class Hospitalization(
    val admitSource: MgoCodeableConcept? = null,
    val dischargeDisposition: MgoCodeableConcept? = null,
) : Parcelable

@Serializable
enum class ZibEncounterProfile(val value: String) {
    @SerialName("http://nictiz.nl/fhir/StructureDefinition/zib-Encounter")
    HTTPNictizNlFhirStructureDefinitionZibEncounter("http://nictiz.nl/fhir/StructureDefinition/zib-Encounter"),
}

@Serializable
@Parcelize
data class ZibFunctionalOrMentalStatus(
    val bodySite: MgoCodeableConcept? = null,
    val category: List<MgoCodeableConcept>? = null,
    val comment: String? = null,
    val context: MgoReference? = null,
    val dataAbsentReason: MgoCodeableConcept? = null,
    val effectivePeriod: MgoPeriod? = null,
    val fhirVersion: FhirVersionR3,
    val id: String? = null,
    val identifier: List<MgoIdentifier>? = null,
    val method: MgoCodeableConcept? = null,
    val profile: ZibFunctionalOrMentalStatusProfile,
    @SerialName("referenceId")
    val referenceID: String,
    val resourceType: String? = null,
    val status: GpLaboratoryResultStatus? = null,
    val subject: MgoReference? = null,
    val valueQuantity: MgoDuration? = null,
) : Parcelable

@Serializable
enum class ZibFunctionalOrMentalStatusProfile(val value: String) {
    @SerialName("http://nictiz.nl/fhir/StructureDefinition/zib-FunctionalOrMentalStatus")
    HTTPNictizNlFhirStructureDefinitionZibFunctionalOrMentalStatus(
        "http://nictiz.nl/fhir/StructureDefinition/zib-FunctionalOrMentalStatus",
    ),
}

@Serializable
@Parcelize
data class ZibLaboratoryTestResultObservation(
    val basedOn: List<MgoReference>? = null,
    val code: MgoCodeableConcept? = null,
    val comment: String? = null,
    val context: MgoReference? = null,
    val effectiveDateTime: String? = null,
    val effectivePeriod: MgoPeriod? = null,
    val fhirVersion: FhirVersionR3,
    val id: String? = null,
    val identifier: List<MgoIdentifier>? = null,
    val interpretation: MgoCodeableConcept? = null,
    val laboratoryTestResultCode: List<MgoCodeableConcept>? = null,
    val method: MgoCodeableConcept? = null,
    val performer: List<MgoReference>? = null,
    val profile: ZibLaboratoryTestResultObservationProfile,
    @SerialName("referenceId")
    val referenceID: String,
    val referenceRange: List<ZibLaboratoryTestResultObservationReferenceRange>? = null,
    val related: List<ZibLaboratoryTestResultObservationRelated>? = null,
    val resourceType: String? = null,
    val resultType: List<MgoCodeableConcept>? = null,
    val specimen: MgoReference? = null,
    val status: GpLaboratoryResultStatus? = null,
    val subject: MgoReference? = null,
    val valueBoolean: Boolean? = null,
    val valueCodeableConcept: MgoCodeableConcept? = null,
    val valueDateTime: String? = null,
    val valuePeriod: MgoPeriod? = null,
    val valueQuantity: MgoDuration? = null,
    val valueRange: MgoRange? = null,
    val valueRatio: MgoRatio? = null,
    val valueString: String? = null,
) : Parcelable

@Serializable
enum class ZibLaboratoryTestResultObservationProfile(val value: String) {
    @SerialName("http://nictiz.nl/fhir/StructureDefinition/zib-LaboratoryTestResult-Observation")
    HTTPNictizNlFhirStructureDefinitionZibLaboratoryTestResultObservation(
        "http://nictiz.nl/fhir/StructureDefinition/zib-LaboratoryTestResult-Observation",
    ),
}

@Serializable
@Parcelize
data class ZibLaboratoryTestResultObservationReferenceRange(
    val age: MgoRange? = null,
    val appliesTo: List<MgoCodeableConcept>? = null,
    val high: MgoDuration? = null,
    val low: MgoDuration? = null,
    val type: MgoCodeableConcept? = null,
) : Parcelable

@Serializable
@Parcelize
data class ZibLaboratoryTestResultObservationRelated(
    val target: MgoReference? = null,
) : Parcelable

@Serializable
@Parcelize
data class ZibLaboratoryTestResultSpecimen(
    val collection: ZibLaboratoryTestResultSpecimenCollection,
    val container: List<ZibLaboratoryTestResultSpecimenContainer>? = null,
    val fhirVersion: FhirVersionR3,
    val id: String? = null,
    val identifier: List<MgoIdentifier>? = null,
    val note: List<MgoAnnotation>? = null,
    val profile: ZibLaboratoryTestResultSpecimenProfile,
    val receivedTime: String? = null,
    @SerialName("referenceId")
    val referenceID: String,
    val resourceType: String? = null,
    val subject: MgoReference? = null,
    val type: MgoCodeableConcept? = null,
) : Parcelable

@Serializable
@Parcelize
data class ZibLaboratoryTestResultSpecimenCollection(
    val bodySite: PurpleBodySite,
    val collectedDateTime: String? = null,
    val collectedPeriod: MgoPeriod? = null,
    val method: MgoCodeableConcept? = null,
    val quantity: MgoDuration? = null,
) : Parcelable

@Serializable
@Parcelize
data class PurpleBodySite(
    val laterality: MgoCodeableConcept? = null,
    val morphology: MgoCodeableConcept? = null,
    val value: MgoCodeableConcept? = null,
) : Parcelable

@Serializable
@Parcelize
data class ZibLaboratoryTestResultSpecimenContainer(
    val identifier: List<MgoIdentifier>? = null,
    val type: MgoCodeableConcept? = null,
) : Parcelable

@Serializable
enum class ZibLaboratoryTestResultSpecimenProfile(val value: String) {
    @SerialName("http://nictiz.nl/fhir/StructureDefinition/zib-LaboratoryTestResult-Specimen")
    HTTPNictizNlFhirStructureDefinitionZibLaboratoryTestResultSpecimen(
        "http://nictiz.nl/fhir/StructureDefinition/zib-LaboratoryTestResult-Specimen",
    ),
}

@Serializable
@Parcelize
data class ZibLaboratoryTestResultSpecimenIsolate(
    val collection: ZibLaboratoryTestResultSpecimenIsolateCollection,
    val container: List<ZibLaboratoryTestResultSpecimenIsolateContainer>? = null,
    val fhirVersion: FhirVersionR3,
    val id: String? = null,
    val identifier: List<MgoIdentifier>? = null,
    val note: List<MgoAnnotation>? = null,
    val profile: ZibLaboratoryTestResultSpecimenIsolateProfile,
    val receivedTime: String? = null,
    @SerialName("referenceId")
    val referenceID: String,
    val resourceType: String? = null,
    val subject: MgoReference? = null,
    val type: MgoCodeableConcept? = null,
) : Parcelable

@Serializable
@Parcelize
data class ZibLaboratoryTestResultSpecimenIsolateCollection(
    val bodySite: FluffyBodySite,
    val collectedDateTime: String? = null,
    val collectedPeriod: MgoPeriod? = null,
    val method: MgoCodeableConcept? = null,
    val quantity: MgoDuration? = null,
) : Parcelable

@Serializable
@Parcelize
data class FluffyBodySite(
    val laterality: MgoCodeableConcept? = null,
    val morphology: MgoCodeableConcept? = null,
    val value: MgoCodeableConcept? = null,
) : Parcelable

@Serializable
@Parcelize
data class ZibLaboratoryTestResultSpecimenIsolateContainer(
    val identifier: List<MgoIdentifier>? = null,
    val type: MgoCodeableConcept? = null,
) : Parcelable

@Serializable
enum class ZibLaboratoryTestResultSpecimenIsolateProfile(val value: String) {
    @SerialName("http://nictiz.nl/fhir/StructureDefinition/zib-LaboratoryTestResult-Specimen-Isolate")
    HTTPNictizNlFhirStructureDefinitionZibLaboratoryTestResultSpecimenIsolate(
        "http://nictiz.nl/fhir/StructureDefinition/zib-LaboratoryTestResult-Specimen-Isolate",
    ),
}

@Serializable
@Parcelize
data class ZibLaboratoryTestResultSubstance(
    val category: List<MgoCodeableConcept>? = null,
    val code: MgoCodeableConcept? = null,
    val description: String? = null,
    val fhirVersion: FhirVersionR3,
    val id: String? = null,
    val identifier: List<MgoIdentifier>? = null,
    val profile: ZibLaboratoryTestResultSubstanceProfile,
    @SerialName("referenceId")
    val referenceID: String,
    val resourceType: String? = null,
    val status: String? = null,
) : Parcelable

@Serializable
enum class ZibLaboratoryTestResultSubstanceProfile(val value: String) {
    @SerialName("http://nictiz.nl/fhir/StructureDefinition/zib-LaboratoryTestResult-Substance")
    HTTPNictizNlFhirStructureDefinitionZibLaboratoryTestResultSubstance(
        "http://nictiz.nl/fhir/StructureDefinition/zib-LaboratoryTestResult-Substance",
    ),
}

@Serializable
@Parcelize
data class ZibLivingSituation(
    val bodySite: MgoCodeableConcept? = null,
    val category: List<MgoCodeableConcept>? = null,
    val comment: String? = null,
    val context: MgoReference? = null,
    val dataAbsentReason: MgoCodeableConcept? = null,
    val effectiveDateTime: String? = null,
    val effectivePeriod: MgoPeriod? = null,
    val fhirVersion: FhirVersionR3,
    val id: String? = null,
    val identifier: List<MgoIdentifier>? = null,
    val method: MgoCodeableConcept? = null,
    val profile: ZibLivingSituationProfile,
    @SerialName("referenceId")
    val referenceID: String,
    val resourceType: String? = null,
    val status: GpLaboratoryResultStatus? = null,
    val subject: MgoReference? = null,
    val valueQuantity: MgoDuration? = null,
) : Parcelable

@Serializable
enum class ZibLivingSituationProfile(val value: String) {
    @SerialName("http://nictiz.nl/fhir/StructureDefinition/zib-LivingSituation")
    HTTPNictizNlFhirStructureDefinitionZibLivingSituation("http://nictiz.nl/fhir/StructureDefinition/zib-LivingSituation"),
}

@Serializable
@Parcelize
data class ZibMedicalDevice(
    val bodySite: MgoCodeableConcept? = null,
    val device: MgoReference? = null,
    val fhirVersion: FhirVersionR3,
    val id: String? = null,
    val identifier: List<MgoIdentifier>? = null,
    val laterality: MgoCodeableConcept? = null,
    val note: List<MgoAnnotation>? = null,
    val organization: MgoReference? = null,
    val patient: MgoReference? = null,
    val practitioner: MgoReference? = null,
    val profile: ZibMedicalDeviceProfile,
    val reason: MgoReference? = null,
    val recordedOn: String? = null,
    @SerialName("referenceId")
    val referenceID: String,
    val resourceType: String? = null,
    val source: MgoReference? = null,
    val status: ZibMedicalDeviceStatus? = null,
    val whenUsed: MgoPeriod? = null,
) : Parcelable

@Serializable
enum class ZibMedicalDeviceProfile(val value: String) {
    @SerialName("http://nictiz.nl/fhir/StructureDefinition/zib-MedicalDevice")
    HTTPNictizNlFhirStructureDefinitionZibMedicalDevice("http://nictiz.nl/fhir/StructureDefinition/zib-MedicalDevice"),
}

@Serializable
enum class ZibMedicalDeviceStatus(val value: String) {
    @SerialName("active")
    Active("active"),

    @SerialName("completed")
    Completed("completed"),

    @SerialName("entered-in-error")
    EnteredInError("entered-in-error"),

    @SerialName("intended")
    Intended("intended"),

    @SerialName("on-hold")
    OnHold("on-hold"),

    @SerialName("stopped")
    Stopped("stopped"),
}

@Serializable
@Parcelize
data class ZibMedicalDeviceProduct(
    val expirationDate: String? = null,
    val fhirVersion: FhirVersionR3,
    val id: String? = null,
    val note: List<MgoAnnotation>? = null,
    val patient: MgoReference? = null,
    val profile: ZibMedicalDeviceProductProfile,
    @SerialName("referenceId")
    val referenceID: String,
    val resourceType: String? = null,
) : Parcelable

@Serializable
enum class ZibMedicalDeviceProductProfile(val value: String) {
    @SerialName("http://nictiz.nl/fhir/StructureDefinition/zib-MedicalDeviceProduct")
    HTTPNictizNlFhirStructureDefinitionZibMedicalDeviceProduct("http://nictiz.nl/fhir/StructureDefinition/zib-MedicalDeviceProduct"),
}

@Serializable
@Parcelize
data class ZibMedicalDeviceRequest(
    val codeCodeableConcept: MgoCodeableConcept? = null,
    val codeReference: MgoReference? = null,
    val fhirVersion: FhirVersionR3,
    val id: String? = null,
    val intent: MgoCodeableConcept? = null,
    val occurrence: MgoPeriod? = null,
    val perfomer: MgoReference? = null,
    val profile: ZibMedicalDeviceRequestProfile,
    @SerialName("referenceId")
    val referenceID: String,
    val resourceType: String? = null,
    val status: String? = null,
    val subject: MgoReference? = null,
) : Parcelable

@Serializable
enum class ZibMedicalDeviceRequestProfile(val value: String) {
    @SerialName("http://nictiz.nl/fhir/StructureDefinition/zib-MedicalDeviceRequest")
    HTTPNictizNlFhirStructureDefinitionZibMedicalDeviceRequest("http://nictiz.nl/fhir/StructureDefinition/zib-MedicalDeviceRequest"),
}

@Serializable
@Parcelize
data class ZibMedicationAgreement(
    val basedOn: List<MgoReference>? = null,
    val category: MgoCodeableConcept? = null,
    val definition: List<MgoReference>? = null,
    val dossageInstruction: List<ZibInstructionsForUse>? = null,
    val fhirVersion: FhirVersionR3,
    val groupIdentifier: MgoIdentifier? = null,
    val id: String? = null,
    val identifier: List<MgoIdentifier>? = null,
    val intent: Intent? = null,
    val medicationReference: MgoReference? = null,
    val medicationTreatment: MgoIdentifier? = null,
    val note: List<MgoAnnotation>? = null,
    val periodOfUse: MgoPeriod? = null,
    val priority: Priority? = null,
    val profile: ZibMedicationAgreementProfile,
    @SerialName("referenceId")
    val referenceID: String,
    val repeatPeriodCyclicalSchedule: MgoDuration? = null,
    val resourceType: String? = null,
    val status: ZibMedicationAgreementStatus? = null,
    val stopType: MgoCodeableConcept? = null,
    val usageDuration: MgoDuration? = null,
) : Parcelable

@Serializable
enum class Intent(val value: String) {
    @SerialName("instance-order")
    InstanceOrder("instance-order"),

    @SerialName("order")
    Order("order"),

    @SerialName("plan")
    Plan("plan"),

    @SerialName("proposal")
    Proposal("proposal"),
}

@Serializable
enum class Priority(val value: String) {
    @SerialName("asap")
    Asap("asap"),

    @SerialName("routine")
    Routine("routine"),

    @SerialName("stat")
    Stat("stat"),

    @SerialName("urgent")
    Urgent("urgent"),
}

@Serializable
enum class ZibMedicationAgreementProfile(val value: String) {
    @SerialName("http://nictiz.nl/fhir/StructureDefinition/zib-MedicationAgreement")
    HTTPNictizNlFhirStructureDefinitionZibMedicationAgreement("http://nictiz.nl/fhir/StructureDefinition/zib-MedicationAgreement"),
}

@Serializable
enum class ZibMedicationAgreementStatus(val value: String) {
    @SerialName("active")
    Active("active"),

    @SerialName("cancelled")
    Cancelled("cancelled"),

    @SerialName("completed")
    Completed("completed"),

    @SerialName("draft")
    Draft("draft"),

    @SerialName("entered-in-error")
    EnteredInError("entered-in-error"),

    @SerialName("on-hold")
    OnHold("on-hold"),

    @SerialName("stopped")
    Stopped("stopped"),

    @SerialName("unknown")
    Unknown("unknown"),
}

@Serializable
@Parcelize
data class ZibMedicationUse(
    val asAgreedIndicator: Boolean? = null,
    val author: MgoReference? = null,
    val category: MgoCodeableConcept? = null,
    val dateAsserted: String? = null,
    val dosage: List<ZibInstructionsForUse>? = null,
    val effectiveDuration: MgoDuration? = null,
    val effectivePeriod: MgoPeriod? = null,
    val fhirVersion: FhirVersionR3,
    val id: String? = null,
    val identifier: List<MgoIdentifier>? = null,
    val informationSource: MgoReference? = null,
    val medicationReference: MgoReference? = null,
    val medicationTreatment: MgoIdentifier? = null,
    val note: List<MgoAnnotation>? = null,
    val prescriber: MgoReference? = null,
    val profile: ZibMedicationUseProfile,
    val reasonCode: List<MgoCodeableConcept>? = null,
    val reasonForChangeOrDiscontinuationOfUse: MgoCodeableConcept? = null,
    @SerialName("referenceId")
    val referenceID: String,
    val repeatPeriodCyclicalSchedule: MgoDuration? = null,
    val resourceType: String? = null,
    val status: ZibMedicalDeviceStatus? = null,
    val subject: MgoReference? = null,
    val taken: Taken? = null,
) : Parcelable

@Serializable
enum class ZibMedicationUseProfile(val value: String) {
    @SerialName("http://nictiz.nl/fhir/StructureDefinition/zib-MedicationUse")
    HTTPNictizNlFhirStructureDefinitionZibMedicationUse("http://nictiz.nl/fhir/StructureDefinition/zib-MedicationUse"),
}

@Serializable
enum class Taken(val value: String) {
    @SerialName("n")
    N("n"),

    @SerialName("na")
    Na("na"),

    @SerialName("unk")
    Unk("unk"),

    @SerialName("y")
    Y("y"),
}

@Serializable
@Parcelize
data class ZibNutritionAdvice(
    val comment: String? = null,
    val dateTime: String? = null,
    val fhirVersion: FhirVersionR3,
    val foodPreferenceModifier: List<MgoCodeableConcept>? = null,
    val id: String? = null,
    val identifier: List<MgoIdentifier>? = null,
    val patient: MgoReference? = null,
    val profile: ZibNutritionAdviceProfile,
    @SerialName("referenceId")
    val referenceID: String,
    val resourceType: String? = null,
    val status: ZibNutritionAdviceStatus? = null,
) : Parcelable

@Serializable
enum class ZibNutritionAdviceProfile(val value: String) {
    @SerialName("http://nictiz.nl/fhir/StructureDefinition/zib-NutritionAdvice")
    HTTPNictizNlFhirStructureDefinitionZibNutritionAdvice("http://nictiz.nl/fhir/StructureDefinition/zib-NutritionAdvice"),
}

@Serializable
enum class ZibNutritionAdviceStatus(val value: String) {
    @SerialName("active")
    Active("active"),

    @SerialName("cancelled")
    Cancelled("cancelled"),

    @SerialName("completed")
    Completed("completed"),

    @SerialName("draft")
    Draft("draft"),

    @SerialName("entered-in-error")
    EnteredInError("entered-in-error"),

    @SerialName("on-hold")
    OnHold("on-hold"),

    @SerialName("planned")
    Planned("planned"),

    @SerialName("proposed")
    Proposed("proposed"),

    @SerialName("requested")
    Requested("requested"),
}

@Serializable
@Parcelize
data class ZibPayer(
    val beneficiary: MgoReference? = null,
    val contract: List<MgoReference>? = null,
    val dependent: String? = null,
    val fhirVersion: FhirVersionR3,
    val grouping: Grouping,
    val id: String? = null,
    val identifier: List<MgoIdentifier>? = null,
    val network: String? = null,
    val order: Double? = null,
    val payor: List<MgoReference>? = null,
    val period: MgoPeriod? = null,
    val policyHolder: MgoReference? = null,
    val profile: ZibPayerProfile,
    @SerialName("referenceId")
    val referenceID: String,
    val relationship: MgoCodeableConcept? = null,
    val resourceType: String? = null,
    val sequence: String? = null,
    val status: ZibPayerStatus? = null,
    val subscriber: MgoReference? = null,
    @SerialName("subscriberId")
    val subscriberID: String? = null,
    val type: MgoCodeableConcept? = null,
) : Parcelable

@Serializable
@Parcelize
data class Grouping(
    @SerialName("class")
    val groupingClass: String? = null,
    val classDisplay: String? = null,
    val group: String? = null,
    val groupDisplay: String? = null,
    val plan: String? = null,
    val planDisplay: String? = null,
    val subClass: String? = null,
    val subClassDisplay: String? = null,
    val subGroup: String? = null,
    val subGroupDisplay: String? = null,
    val subPlan: String? = null,
    val subPlanDisplay: String? = null,
) : Parcelable

@Serializable
enum class ZibPayerProfile(val value: String) {
    @SerialName("http://nictiz.nl/fhir/StructureDefinition/zib-Payer")
    HTTPNictizNlFhirStructureDefinitionZibPayer("http://nictiz.nl/fhir/StructureDefinition/zib-Payer"),
}

@Serializable
enum class ZibPayerStatus(val value: String) {
    @SerialName("active")
    Active("active"),

    @SerialName("cancelled")
    Cancelled("cancelled"),

    @SerialName("draft")
    Draft("draft"),

    @SerialName("entered-in-error")
    EnteredInError("entered-in-error"),
}

@Serializable
@Parcelize
data class ZibProblem(
    val abatementDateTime: String? = null,
    val assertedDate: String? = null,
    val asserter: MgoReference? = null,
    val bodySite: List<MgoCodeableConcept>? = null,
    val category: List<MgoCodeableConcept>? = null,
    val clinicalStatus: ZibProblemClinicalStatus? = null,
    val code: MgoCodeableConcept? = null,
    val context: MgoReference? = null,
    val evidence: List<Evidence>? = null,
    val fhirVersion: FhirVersionR3,
    val id: String? = null,
    val identifier: List<MgoIdentifier>? = null,
    val note: List<MgoAnnotation>? = null,
    val onsetDateTime: String? = null,
    val profile: ZibProblemProfile,
    @SerialName("referenceId")
    val referenceID: String,
    val resourceType: String? = null,
    val severity: MgoCodeableConcept? = null,
    val stage: Stage,
    val subject: MgoReference? = null,
    val verificationStatus: ZibProblemVerificationStatus? = null,
) : Parcelable

@Serializable
enum class ZibProblemClinicalStatus(val value: String) {
    @SerialName("active")
    Active("active"),

    @SerialName("inactive")
    Inactive("inactive"),

    @SerialName("recurrence")
    Recurrence("recurrence"),

    @SerialName("remission")
    Remission("remission"),

    @SerialName("resolved")
    Resolved("resolved"),
}

@Serializable
@Parcelize
data class Evidence(
    val code: List<MgoCodeableConcept>? = null,
    val detail: List<MgoReference>? = null,
) : Parcelable

@Serializable
enum class ZibProblemProfile(val value: String) {
    @SerialName("http://nictiz.nl/fhir/StructureDefinition/zib-Problem")
    HTTPNictizNlFhirStructureDefinitionZibProblem("http://nictiz.nl/fhir/StructureDefinition/zib-Problem"),
}

@Serializable
@Parcelize
data class Stage(
    val assessment: List<MgoReference>? = null,
    val summary: MgoCodeableConcept? = null,
) : Parcelable

@Serializable
enum class ZibProblemVerificationStatus(val value: String) {
    @SerialName("confirmed")
    Confirmed("confirmed"),

    @SerialName("differential")
    Differential("differential"),

    @SerialName("entered-in-error")
    EnteredInError("entered-in-error"),

    @SerialName("provisional")
    Provisional("provisional"),

    @SerialName("refuted")
    Refuted("refuted"),

    @SerialName("unknown")
    Unknown("unknown"),
}

@Serializable
@Parcelize
data class ZibProcedure(
    val bodySite: List<MgoCodeableConcept>? = null,
    val bodySiteQualifier: List<MgoCodeableConcept>? = null,
    val code: MgoCodeableConcept? = null,
    val fhirVersion: FhirVersionR3,
    val focalDevice: List<FocalDevice>? = null,
    val id: String? = null,
    val location: MgoReference? = null,
    val performedPeriod: MgoPeriod? = null,
    val performer: List<Performer>? = null,
    val procedureMethod: MgoCodeableConcept? = null,
    val profile: ZibProcedureProfile,
    val reasonReference: List<MgoReference>? = null,
    @SerialName("referenceId")
    val referenceID: String,
    val resourceType: String? = null,
    val subject: MgoReference? = null,
) : Parcelable

@Serializable
@Parcelize
data class FocalDevice(
    val manipulated: MgoReference? = null,
) : Parcelable

@Serializable
@Parcelize
data class Performer(
    val actor: MgoReference? = null,
) : Parcelable

@Serializable
enum class ZibProcedureProfile(val value: String) {
    @SerialName("http://nictiz.nl/fhir/StructureDefinition/zib-Procedure")
    HTTPNictizNlFhirStructureDefinitionZibProcedure("http://nictiz.nl/fhir/StructureDefinition/zib-Procedure"),
}

@Serializable
@Parcelize
data class ZibProcedureRequest(
    val code: MgoCodeableConcept? = null,
    val fhirVersion: FhirVersionR3,
    val id: String? = null,
    val intent: String? = null,
    val occurrence: MgoPeriod? = null,
    val perfomer: MgoReference? = null,
    val profile: ZibProcedureRequestProfile,
    val reason: List<MgoReference>? = null,
    @SerialName("referenceId")
    val referenceID: String,
    val resourceType: String? = null,
    val status: String? = null,
    val subject: MgoReference? = null,
) : Parcelable

@Serializable
enum class ZibProcedureRequestProfile(val value: String) {
    @SerialName("http://nictiz.nl/fhir/StructureDefinition/zib-ProcedureRequest")
    HTTPNictizNlFhirStructureDefinitionZibProcedureRequest("http://nictiz.nl/fhir/StructureDefinition/zib-ProcedureRequest"),
}

@Serializable
@Parcelize
data class ZibProduct(
    val code: MgoCodeableConcept? = null,
    val description: String? = null,
    val fhirVersion: FhirVersionR3,
    val form: MgoCodeableConcept? = null,
    val id: String? = null,
    val ingredient: List<ZibProductIngredient>? = null,
    @SerialName("package")
    val zibProductPackage: Package,
    val profile: ZibProductProfile,
    @SerialName("referenceId")
    val referenceID: String,
    val resourceType: String? = null,
) : Parcelable

@Serializable
@Parcelize
data class ZibProductIngredient(
    val amount: MgoRatio? = null,
    val item: MgoCodeableConcept? = null,
) : Parcelable

@Serializable
enum class ZibProductProfile(val value: String) {
    @SerialName("http://nictiz.nl/fhir/StructureDefinition/zib-Product")
    HTTPNictizNlFhirStructureDefinitionZibProduct("http://nictiz.nl/fhir/StructureDefinition/zib-Product"),
}

@Serializable
@Parcelize
data class Package(
    val content: List<PackageContent>? = null,
) : Parcelable

@Serializable
@Parcelize
data class PackageContent(
    val item: MgoCodeableConcept? = null,
    val reference: MgoReference? = null,
) : Parcelable

@Serializable
@Parcelize
data class ZibProductPackage(
    val content: List<ZibProductPackageContent>? = null,
) : Parcelable

@Serializable
@Parcelize
data class ZibProductPackageContent(
    val item: MgoCodeableConcept? = null,
    val reference: MgoReference? = null,
) : Parcelable

@Serializable
@Parcelize
data class ZibTobaccoUse(
    val bodySite: MgoCodeableConcept? = null,
    val category: List<MgoCodeableConcept>? = null,
    val comment: String? = null,
    val context: MgoReference? = null,
    val dataAbsentReason: MgoCodeableConcept? = null,
    val effectivePeriod: MgoPeriod? = null,
    val fhirVersion: FhirVersionR3,
    val id: String? = null,
    val identifier: List<MgoIdentifier>? = null,
    val method: MgoCodeableConcept? = null,
    val profile: ZibTobaccoUseProfile,
    @SerialName("referenceId")
    val referenceID: String,
    val resourceType: String? = null,
    val status: GpLaboratoryResultStatus? = null,
    val subject: MgoReference? = null,
    val valueQuantity: MgoDuration? = null,
) : Parcelable

@Serializable
enum class ZibTobaccoUseProfile(val value: String) {
    @SerialName("http://nictiz.nl/fhir/StructureDefinition/zib-TobaccoUse")
    HTTPNictizNlFhirStructureDefinitionZibTobaccoUse("http://nictiz.nl/fhir/StructureDefinition/zib-TobaccoUse"),
}

@Serializable
@Parcelize
data class ZibTreatmentDirective(
    val action: List<MgoCodeableConcept>? = null,
    val actor: List<ZibTreatmentDirectiveActor>? = null,
    val category: List<MgoCodeableConcept>? = null,
    val consentingParty: List<MgoReference>? = null,
    val data: List<ZibTreatmentDirectiveDatum>? = null,
    val dataPeriod: MgoPeriod? = null,
    val dateTime: String? = null,
    val except: List<Except>? = null,
    val fhirVersion: FhirVersionR3,
    val id: String? = null,
    val identifier: MgoIdentifier? = null,
    val organization: List<MgoReference>? = null,
    val patient: MgoReference? = null,
    val period: MgoPeriod? = null,
    val policy: List<Policy>? = null,
    val policyRule: String? = null,
    val profile: ZibTreatmentDirectiveProfile,
    val purpose: List<MgoCoding>? = null,
    @SerialName("referenceId")
    val referenceID: String,
    val resourceType: String? = null,
    val securityLabel: List<MgoCoding>? = null,
    val sourceAttachment: Attachment,
    val sourceIdentifier: MgoIdentifier? = null,
    val sourceReference: MgoReference? = null,
    val status: ZibTreatmentDirectiveStatus? = null,
) : Parcelable

@Serializable
@Parcelize
data class ZibTreatmentDirectiveActor(
    val reference: MgoReference? = null,
    val role: MgoCodeableConcept? = null,
) : Parcelable

@Serializable
@Parcelize
data class ZibTreatmentDirectiveDatum(
    val meaning: String? = null,
    val reference: MgoReference? = null,
) : Parcelable

@Serializable
@Parcelize
data class Except(
    val action: List<MgoCodeableConcept>? = null,
    val actor: List<ExceptActor>? = null,
    @SerialName("class")
    val exceptClass: List<MgoCoding>? = null,
    val code: List<MgoCoding>? = null,
    val data: List<ExceptDatum>? = null,
    val dataPeriod: MgoPeriod? = null,
    val period: MgoPeriod? = null,
    val purpose: List<MgoCoding>? = null,
    val securityLabel: List<MgoCoding>? = null,
    val type: String? = null,
) : Parcelable

@Serializable
@Parcelize
data class ExceptActor(
    val reference: MgoReference? = null,
    val role: MgoCodeableConcept? = null,
) : Parcelable

@Serializable
@Parcelize
data class ExceptDatum(
    val meaning: String? = null,
    val reference: MgoReference? = null,
) : Parcelable

@Serializable
@Parcelize
data class Policy(
    val authority: String? = null,
    val id: String? = null,
    val uri: String? = null,
) : Parcelable

@Serializable
enum class ZibTreatmentDirectiveProfile(val value: String) {
    @SerialName("http://nictiz.nl/fhir/StructureDefinition/zib-TreatmentDirective")
    HTTPNictizNlFhirStructureDefinitionZibTreatmentDirective("http://nictiz.nl/fhir/StructureDefinition/zib-TreatmentDirective"),
}

@Serializable
enum class ZibTreatmentDirectiveStatus(val value: String) {
    @SerialName("active")
    Active("active"),

    @SerialName("draft")
    Draft("draft"),

    @SerialName("entered-in-error")
    EnteredInError("entered-in-error"),

    @SerialName("inactive")
    Inactive("inactive"),

    @SerialName("proposed")
    Proposed("proposed"),

    @SerialName("rejected")
    Rejected("rejected"),
}

@Serializable
@Parcelize
data class ZibVaccination(
    val dose: MgoDuration? = null,
    val fhirVersion: FhirVersionR3,
    val id: String? = null,
    val identifier: List<MgoIdentifier>? = null,
    val note: List<MgoAnnotation>? = null,
    val patient: MgoReference? = null,
    val practitioner: List<Practitioner>? = null,
    val profile: ZibVaccinationProfile,
    @SerialName("referenceId")
    val referenceID: String,
    val resourceType: String? = null,
    val vaccinationDate: String? = null,
    val vaccineCode: MgoCodeableConcept? = null,
) : Parcelable

@Serializable
@Parcelize
data class Practitioner(
    val actor: MgoReference? = null,
) : Parcelable

@Serializable
enum class ZibVaccinationProfile(val value: String) {
    @SerialName("http://nictiz.nl/fhir/StructureDefinition/zib-Vaccination")
    HTTPNictizNlFhirStructureDefinitionZibVaccination("http://nictiz.nl/fhir/StructureDefinition/zib-Vaccination"),
}

@Serializable
@Parcelize
data class ZibVaccinationRecommendation(
    val fhirVersion: FhirVersionR3,
    val id: String? = null,
    val orderStatus: MgoCodeableConcept? = null,
    val profile: ZibVaccinationRecommendationProfile,
    val recommendation: List<Recommendation>? = null,
    @SerialName("referenceId")
    val referenceID: String,
    val resourceType: String? = null,
) : Parcelable

@Serializable
enum class ZibVaccinationRecommendationProfile(val value: String) {
    @SerialName("http://nictiz.nl/fhir/StructureDefinition/zib-VaccinationRecommendation")
    HTTPNictizNlFhirStructureDefinitionZibVaccinationRecommendation(
        "http://nictiz.nl/fhir/StructureDefinition/zib-VaccinationRecommendation",
    ),
}

@Serializable
@Parcelize
data class Recommendation(
    val code: MgoCodeableConcept? = null,
    val date: String? = null,
    val dateCriterion: List<String>? = null,
) : Parcelable
