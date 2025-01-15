package nl.rijksoverheid.mgo.data.healthcare.mgoResource.urlCreator

import android.net.Uri
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.HealthCareRequest
import javax.inject.Inject

internal class DefaultHealthCareUrlCreator
    @Inject
    constructor() : HealthCareUrlCreator {
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
