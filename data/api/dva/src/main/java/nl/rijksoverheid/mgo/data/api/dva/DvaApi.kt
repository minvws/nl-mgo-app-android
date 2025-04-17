package nl.rijksoverheid.mgo.data.api.dva

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Url

/**
 * API interface for fetching FHIR resources from FHIR Server (https://www.hl7.org/fhir/).
 * This acts as a proxy that communicates with multiple servers to retrieve FHIR resources.
 */
interface DvaApi {
  /**
   * Retrieves the contents of a FHIR Binary resource.
   *
   * This endpoint fetches a binary resource (e.g., a file, image, or document) from a specified
   * FHIR server. The actual server to retrieve the resource from is provided in the request header.
   *
   * @param resourceEndpoint The target server's endpoint, provided in the "x-mgo-dva-target" header.
   * @param fhirBinary The ID of the binary resource to retrieve.
   * @param The accept header to send with the request. Defaults to application/fhir+json which is expected in this request.
   * @return A [BinaryResponse] containing the binary data.
   */
  @GET("fhir/{fhirBinary}")
  suspend fun binary(
    @Header("x-mgo-dva-target") resourceEndpoint: String,
    @Path(value = "fhirBinary", encoded = true) fhirBinary: String,
    @Header("Accept") accept: String = "application/fhir+json; fhirVersion=3.0s",
  ): BinaryResponse

  /**
   * Fetches a FHIR resource from a given endpoint.
   *
   * This method retrieves a generic FHIR resource by making a request to a specified URL.
   * The actual response type varies depending on the requested resource, so the response is
   * returned as a raw [ResponseBody] for further processing by a shared codebase.
   *
   * @param resourceEndpoint The target server's endpoint, provided in the "x-mgo-dva-target" header.
   * @param url The complete URL of the FHIR resource to retrieve.
   * @param accept The accept header to send with the request.
   * @return A [ResponseBody] containing the raw response data.
   */
  @GET
  suspend fun get(
    @Header("x-mgo-dva-target") resourceEndpoint: String,
    @Url url: String,
    @Header("Accept") accept: String,
  ): ResponseBody
}
