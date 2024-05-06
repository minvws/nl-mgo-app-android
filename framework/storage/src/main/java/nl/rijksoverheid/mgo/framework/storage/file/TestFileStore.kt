package nl.rijksoverheid.mgo.framework.storage.file

class TestFileStore : FileStore {
    private val files = mutableMapOf<String, Any>()

    override suspend fun <O : Any> saveFile(
        file: O,
        name: String,
    ) {
        files[name] = file
    }

    @Suppress("UNCHECKED_CAST")
    override suspend fun <O : Any> getFile(
        clazz: Class<O>,
        name: String,
    ): O? {
        return files[name] as O?
    }

    fun clear() {
        files.clear()
    }
}
