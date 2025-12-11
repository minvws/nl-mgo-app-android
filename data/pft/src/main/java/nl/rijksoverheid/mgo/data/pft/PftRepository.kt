package nl.rijksoverheid.mgo.data.pft

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.serialization.json.Json
import nl.nl.rijksoverheid.mgo.framework.network.executeRequest
import okhttp3.OkHttpClient
import okhttp3.Request
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class PftRepository
  @Inject
  constructor(
    private val okHttpClient: OkHttpClient,
    @Named("pftUrl") private val url: String,
  ) {
    private val json = Json.Default
    private val pfts = MutableStateFlow<Map<PftSnomedCode, Pft>>(emptyMap())

    fun sync() {
      val request =
        Request
          .Builder()
          .url(url)
          .get()
          .build()

      okHttpClient
        .executeRequest(request)
        .onSuccess { response ->
          val responseJson = response.body?.string()
          if (responseJson != null) {
            val responseMap: Map<String, Pft> = json.decodeFromString(responseJson)
            val pfts: Map<PftSnomedCode, Pft> = responseMap.mapKeys { (key, _) -> PftSnomedCode(key) }
            this.pfts.tryEmit(pfts)
          }
          Timber.d("Successfully fetched Patient Friendly Terms")
        }.onFailure { error ->
          Timber.e(error, "Failed to get Patient Friendly Terms")
        }
    }

    fun observe(snomedCode: PftSnomedCode): Flow<Pft> = pfts.mapNotNull { pvts -> pvts[snomedCode] }
  }
