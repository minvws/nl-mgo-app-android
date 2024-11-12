package nl.rijksoverheid.mgo.framework.storage.keyvalue

import nl.rijksoverheid.mgo.framework.storage.file.FileStore
import kotlin.reflect.KClass

class TestFileStore : FileStore {
    private val files = mutableMapOf<String, Any>()

    override suspend fun <O : Any> saveFile(
        value: O,
        clazz: KClass<O>,
        name: String,
    ) {
        files[name] = value
    }

    @Suppress("UNCHECKED_CAST")
    override suspend fun <O : Any> getFile(
        clazz: KClass<O>,
        name: String,
    ): O? {
        return files[name] as O?
    }

    override suspend fun clear() {
        files.clear()
    }
}
