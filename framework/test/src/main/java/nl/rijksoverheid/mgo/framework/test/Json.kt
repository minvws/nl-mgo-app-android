package nl.rijksoverheid.mgo.framework.test

import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.lang.reflect.Type

inline fun <reified M : Any> String.jsonStringToList(): List<M> {
    val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    val type: Type = Types.newParameterizedType(MutableList::class.java, M::class.java)
    val jsonAdapter = moshi.adapter<List<M>>(type)
    return requireNotNull(jsonAdapter.fromJson(this))
}

inline fun <reified M : Any> String.jsonStringToObject(): M {
    val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    val jsonAdapter = moshi.adapter(M::class.java)
    return requireNotNull(jsonAdapter.fromJson(this))
}

inline fun <reified M : Any> List<M>.toJsonString(): String {
    val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    val type: Type = Types.newParameterizedType(MutableList::class.java, M::class.java)
    val jsonAdapter = moshi.adapter<List<M>>(type)
    return jsonAdapter.toJson(this)
}

inline fun <reified T : Any> T.toJsonString(): String {
    val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    val jsonAdapter = moshi.adapter(T::class.java)
    return jsonAdapter.toJson(this)
}
