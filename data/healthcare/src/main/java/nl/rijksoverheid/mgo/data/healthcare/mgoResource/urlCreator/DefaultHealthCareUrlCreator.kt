package nl.rijksoverheid.mgo.data.healthcare.mgoResource.urlCreator

import android.net.Uri
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.HealthCareRequest
import javax.inject.Inject

/**
 * Create an url based on a base url an a [HealthCareRequest].
 */
internal class DefaultHealthCareUrlCreator
  @Inject
  constructor() : HealthCareUrlCreator {
    /**
     * @param baseUrl The base url.
     * @param request The [HealthCareRequest].
     * @return The constructed url from the base url and the [HealthCareRequest].
     */
    override fun invoke(
      baseUrl: String,
      request: HealthCareRequest,
    ): String {
      val builder = Uri.parse(baseUrl).buildUpon()
      for ((key, value) in request.queryParameters) {
        builder.appendQueryParameter(key.value, value)
      }
      return builder.build().toString()
    }
  }
