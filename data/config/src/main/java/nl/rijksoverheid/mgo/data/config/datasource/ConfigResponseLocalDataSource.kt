package nl.rijksoverheid.mgo.data.config.datasource

import com.squareup.moshi.Moshi
import nl.rijksoverheid.mgo.data.config.api.ConfigResponse
import okio.buffer
import okio.sink
import okio.source
import java.io.File
import java.io.FileNotFoundException
import javax.inject.Inject
import javax.inject.Named
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

internal class ConfigResponseLocalDataSource
    @Inject
    constructor(
        @Named("cacheDir") private val cacheDir: File,
        @Named("configMoshi") private val configMoshi: Moshi,
        @Named("backgroundDispatcher") private val dispatcher: CoroutineDispatcher,
    ) : ConfigResponseDataSource {
        private val file = File(cacheDir, "config.json")

        override suspend fun get(): Result<ConfigResponse> {
            return withContext(dispatcher) {
                if (file.exists()) {
                    try {
                        val sink = file.source()
                        val bufferSource = sink.buffer()
                        val content = bufferSource.readUtf8()
                        bufferSource.close()
                        val config = requireNotNull(configMoshi.adapter(ConfigResponse::class.java).fromJson(content))
                        Result.success(config)
                    } catch (e: Exception) {
                        Result.failure(e)
                    }
                } else {
                    Result.failure(FileNotFoundException())
                }
            }
        }

        override fun store(response: ConfigResponse) {
            try {
                if (!file.exists()) {
                    file.createNewFile()
                }
                val sink = file.sink()
                val bufferSink = sink.buffer()
                val content = configMoshi.adapter(ConfigResponse::class.java).toJson(response)
                bufferSink.writeUtf8(content)
                bufferSink.close()
            } catch (e: Exception) {
                // Never fail
            }
        }
    }
