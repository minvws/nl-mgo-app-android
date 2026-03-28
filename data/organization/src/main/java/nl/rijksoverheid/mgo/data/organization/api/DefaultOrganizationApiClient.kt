package nl.rijksoverheid.mgo.data.organization.api

import nl.nl.rijksoverheid.mgo.framework.network.executeRequest
import okhttp3.OkHttpClient
import okhttp3.Request
import javax.inject.Inject
import javax.inject.Named

class DefaultOrganizationApiClient
  @Inject
  constructor(
    private val okHttpClient: OkHttpClient,
    @Named("organizationsUrl") private val organizationsUrl: String,
    @Named("endpointsUrl") private val endpointsUrl: String,
  ) : OrganizationApiClient {
    override fun getOrganizations(): Result<OrganizationApiResponse> {
      // Create request
      val request =
        Request
          .Builder()
          .url(organizationsUrl)
          .get()
          .build()

      // Execute request and return response
      return okHttpClient
        .executeRequest(request)
        .mapCatching { response ->
          OrganizationApiResponse(response = response.body.byteStream(), cached = response.cacheResponse != null)
        }
    }

    override fun getEndpoints(): Result<OrganizationApiResponse> {
      // Create request
      val request =
        Request
          .Builder()
          .url(endpointsUrl)
          .get()
          .build()

      // Execute request and return response
      return okHttpClient
        .executeRequest(request)
        .mapCatching { response ->
          OrganizationApiResponse(response = response.body.byteStream(), cached = response.cacheResponse != null)
        }
    }
  }
