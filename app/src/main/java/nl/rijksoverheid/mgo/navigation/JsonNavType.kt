package nl.rijksoverheid.mgo.navigation

import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavType
import java.net.URLDecoder
import java.net.URLEncoder
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json

class JsonNavType<T : Parcelable>(
    private val clazz: Class<T>,
    private val serializer: KSerializer<T>,
) : NavType<T?>(isNullableAllowed = true) {
    private val json = Json

    override fun get(
        bundle: Bundle,
        key: String,
    ): T? =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            bundle.getParcelable(key, clazz) as T
        } else {
            @Suppress("DEPRECATION")
            bundle.getParcelable(key)
        }

    override fun put(
        bundle: Bundle,
        key: String,
        value: T?,
    ) {
        bundle.putParcelable(key, value)
    }

    override fun parseValue(value: String): T {
        val decodedJsonString = URLDecoder.decode(value, "UTF-8")
        val decoded = json.decodeFromString(serializer, decodedJsonString)
        return decoded
    }

    override fun serializeAsValue(value: T?): String {
        val jsonString = if (value == null) "" else json.encodeToString(serializer, value)
        val encodedJsonString = URLEncoder.encode(jsonString, "UTF-8")
        return encodedJsonString
    }
}
