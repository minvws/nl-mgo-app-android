package nl.rijksoverheid.mgo.framework.storage.file

import android.content.Context
import com.squareup.moshi.Moshi
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject
import javax.inject.Named
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class DefaultFileStore
    @Inject
    constructor(
        @ApplicationContext val context: Context,
        @Named("storageMoshi") private val moshi: Moshi,
    ) : FileStore {
        override suspend fun <O : Any> saveFile(
            file: O,
            name: String,
        ) {
            withContext(Dispatchers.IO) {
                val json = moshi.adapter<O>(file::class.java).toJson(file)
                val cacheFile = File(context.cacheDir, name)
                cacheFile.writeText(json)
            }
        }

        override suspend fun <O : Any> getFile(
            clazz: Class<O>,
            name: String,
        ): O? {
            return withContext(Dispatchers.IO) {
                val cacheFile = File(context.cacheDir, name)
                if (!cacheFile.exists()) {
                    cacheFile.createNewFile()
                }
                val jsonString = cacheFile.readText()
                if (jsonString.isEmpty()) {
                    null
                } else {
                    moshi.adapter(clazz).fromJson(jsonString)
                }
            }
        }
    }
