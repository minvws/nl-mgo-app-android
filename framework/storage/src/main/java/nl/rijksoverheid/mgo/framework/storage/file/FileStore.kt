package nl.rijksoverheid.mgo.framework.storage.file

import kotlin.reflect.KClass

interface FileStore {
    suspend fun <O : Any> saveFile(
        value: O,
        clazz: KClass<O>,
        name: String,
    )

    suspend fun <O : Any> getFile(
        clazz: KClass<O>,
        name: String,
    ): O?
}
