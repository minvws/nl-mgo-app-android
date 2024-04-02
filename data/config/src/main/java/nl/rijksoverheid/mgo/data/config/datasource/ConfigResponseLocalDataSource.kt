package nl.rijksoverheid.mgo.data.config.datasource

import com.squareup.moshi.Moshi
import nl.rijksoverheid.mgo.data.config.api.ConfigResponse
import okio.buffer
import okio.sink
import okio.source
import java.io.File
import java.io.FileNotFoundException
import java.time.Clock
import java.time.Instant
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
        private val clock: Clock,
    ) : ConfigResponseDataSource {
        private val file = File(cacheDir, "config.json")

        override suspend fun get(): Result<ConfigResponse> {
            return withContext(dispatcher) {
                if (file.exists()) {
                    try {
                        // Read local config file and create config from it
                        val sink = file.source()
                        val bufferSource = sink.buffer()
                        val content = bufferSource.readUtf8()
                        bufferSource.close()
                        val config = requireNotNull(configMoshi.adapter(ConfigResponse::class.java).fromJson(content))

                        // Check if this file is expired
                        val fileInstant = Instant.ofEpochMilli(file.lastModified())
                        val fileExpirationDate = clock.instant().plusSeconds(config.configTTL)
                        if (fileInstant.isAfter(fileExpirationDate)) {
                            // If the file is expired, consider it not found
                            Result.failure(FileNotFoundException())
                        } else {
                            Result.success(config)
                        }
                    } catch (e: Exception) {
                        // If anything fails, return failure
                        Result.failure(e)
                    }
                } else {
                    // In case the file does not exists
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
