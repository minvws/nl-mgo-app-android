package nl.rijksoverheid.mgo.data.fhirParser.mgoResource

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import org.json.JSONObject

typealias MgoResourceReferenceId = String
typealias MgoResourceProfile = String

/**
 * Represents a wrapper for FHIR resource data returned from the shared codebase.
 *
 * The shared codebase provides a JSON string, which is primarily used for passing data
 * between functions rather than direct manipulation. This class allows FHIR resource data
 * to be passed as arguments when navigating between screens in the UI.
 *
 * @param referenceId The unique identifier for the FHIR resource, extracted from [jsonBase64].
 * @param profile The FHIR resource profile URL, also extracted from [jsonBase64].
 * @param jsonBase64 A Base64-encoded JSON string containing the full FHIR resource data.
 */
@Serializable
@Parcelize
data class MgoResource(
  val referenceId: MgoResourceReferenceId,
  val profile: MgoResourceProfile,
  val jsonBase64: String,
) : Parcelable

/**
 * A test instance of [MgoResource] containing sample data for development and testing purposes.
 */
val TEST_MGO_RESOURCE =
  MgoResource(
    referenceId = "1",
    profile = "profile",
    jsonBase64 =
      "eyJpZCI6ImNhZmE4ZjQ1LTc0YmMtNDEwNy1hNmY4LTZlYjU4YzZlZDY3MCIsInJlZmVyZW5jZUlk\n" +
        "IjoiTWVkaWNhdGlvblN0YXRlbWVudC9jYWZhOGY0NS03NGJjLTQxMDctYTZmOC02ZWI1OGM2ZWQ2\n" +
        "NzAiLCJyZXNvdXJjZVR5cGUiOiJNZWRpY2F0aW9uU3RhdGVtZW50IiwicHJvZmlsZSI6Imh0dHA6\n" +
        "Ly9uaWN0aXoubmwvZmhpci9TdHJ1Y3R1cmVEZWZpbml0aW9uL3ppYi1NZWRpY2F0aW9uVXNlIiwi\n" +
        "ZmhpclZlcnNpb24iOiJSMyIsImFzQWdyZWVkSW5kaWNhdG9yIjp0cnVlLCJwcmVzY3JpYmVyIjp7\n" +
        "InJlZmVyZW5jZSI6IlByYWN0aXRpb25lclJvbGUvMWEyNDkzMzYtM2ZlNy00ODhmLWJjODgtNDRi\n" +
        "YzhlMWFkMmFhIiwiZGlzcGxheSI6Ikh1aXNhcnRzZW4sIG5pZXQgbmFkZXIgZ2VzcGVjaWZpY2Vl\n" +
        "cmQifSwiaWRlbnRpZmllciI6W3sic3lzdGVtIjoiaHR0cDovL2V4YW1wbGUtaW1wbGVtZW50ZXIu\n" +
        "Y29tL2ZoaXIvTWVkaWNhdGlvblVzZUlEIiwidmFsdWUiOiIxMjM0NTcwMDAwMDAifV0sInN0YXR1\n" +
        "cyI6ImFjdGl2ZSIsImNhdGVnb3J5Ijp7ImNvZGluZyI6W3siY29kZSI6IjYiLCJkaXNwbGF5Ijoi\n" +
        "TWVkaWNhdGllZ2VicnVpayIsInN5c3RlbSI6InVybjpvaWQ6Mi4xNi44NDAuMS4xMTM4ODMuMi40\n" +
        "LjMuMTEuNjAuMjAuNzcuNS4zIn1dfSwibWVkaWNhdGlvblJlZmVyZW5jZSI6eyJyZWZlcmVuY2Ui\n" +
        "OiJNZWRpY2F0aW9uLzhmMDE3YTQ4LWZkYWItNDJmNS1hMmQ3LWY3YmI2ZDg0YTc2MiIsImRpc3Bs\n" +
        "YXkiOiJaZXN0cmlsIHRhYmxldCAxMG1nIn0sImVmZmVjdGl2ZVBlcmlvZCI6eyJzdGFydCI6IjIw\n" +
        "MTgtMDYtMjgifSwiZWZmZWN0aXZlRHVyYXRpb24iOnsidmFsdWUiOjMsInVuaXQiOiJ3ZWVrIiwi\n" +
        "c3lzdGVtIjoiaHR0cDovL3VuaXRzb2ZtZWFzdXJlLm9yZyIsImNvZGUiOiJ3ayJ9LCJkYXRlQXNz\n" +
        "ZXJ0ZWQiOiIyMDE4LTA4LTE2IiwiaW5mb3JtYXRpb25Tb3VyY2UiOnsicmVmZXJlbmNlIjoiUGF0\n" +
        "aWVudC85M2NkZTI2OS1jZTM1LTQwNzctYTM5ZC0xOTI5NjY3MGU5NDkiLCJkaXNwbGF5IjoiSm9o\n" +
        "YW4gWFhYX0hlbGxlbWFuIn0sInN1YmplY3QiOnsicmVmZXJlbmNlIjoiUGF0aWVudC85M2NkZTI2\n" +
        "OS1jZTM1LTQwNzctYTM5ZC0xOTI5NjY3MGU5NDkiLCJkaXNwbGF5IjoiSm9oYW4gWFhYX0hlbGxl\n" +
        "bWFuIn0sInRha2VuIjoieSIsImRvc2FnZSI6W3sic2VxdWVuY2UiOjEsInRleHQiOiIxIG1hYWwg\n" +
        "cGVyIGRhZyAxIHRhYmxldCwgb3JhYWwiLCJyb3V0ZSI6eyJjb2RpbmciOlt7ImNvZGUiOiI5Iiwi\n" +
        "ZGlzcGxheSI6Ik9yYWFsIiwic3lzdGVtIjoidXJuOm9pZDoyLjE2Ljg0MC4xLjExMzg4My4yLjQu\n" +
        "NC45In1dfSwiZG9zZVF1YW50aXR5Ijp7InZhbHVlIjoxLCJ1bml0Ijoic3R1ayIsInN5c3RlbSI6\n" +
        "InVybjpvaWQ6Mi4xNi44NDAuMS4xMTM4ODMuMi40LjQuMS45MDAuMiIsImNvZGUiOiIyNDUifSwi\n" +
        "dGltaW5nIjp7InJlcGVhdCI6eyJmcmVxdWVuY3kiOjEsInBlcmlvZCI6MSwicGVyaW9kVW5pdCI6\n" +
        "ImQifX19XX0=",
  )

/**
 * Extension function to convert a FHIR bundle JSON string into an instance of [MgoResource].
 *
 * The FHIR bundle format is defined in the HL7 FHIR standard:
 * [FHIR Bundle Specification](https://www.hl7.org/fhir/bundle.html)
 *
 * @receiver A JSON string representing a FHIR bundle.
 * @param jsonBase64 The Base64-encoded JSON string containing the full FHIR resource data.
 * @return An instance of [MgoResource] populated with extracted data.
 * @throws org.json.JSONException If the input JSON does not contain required fields.
 */
fun String.toMgoResource(jsonBase64: String): MgoResource {
  val json = JSONObject(this)
  return MgoResource(
    referenceId = json.getString("referenceId"),
    profile = json.getString("profile"),
    jsonBase64 = jsonBase64,
  )
}
