package nl.rijksoverheid.mgo.framework.storage.file

import java.io.File

interface CacheFileStore {
    fun saveFile(
        name: String,
        contentType: String,
        content: String,
    ): File

    fun deleteAll()
}
