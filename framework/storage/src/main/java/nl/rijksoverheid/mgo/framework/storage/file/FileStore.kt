package nl.rijksoverheid.mgo.framework.storage.file

interface FileStore {
    suspend fun <O : Any> saveFile(
        clazz: O,
        name: String,
    )

    suspend fun <O : Any> getFile(
        clazz: Class<O>,
        name: String,
    ): O?
}
