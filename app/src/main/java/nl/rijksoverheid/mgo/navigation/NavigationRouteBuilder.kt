package nl.rijksoverheid.mgo.navigation

import android.net.Uri

data class NavigationRouteBuilder(val name: String, val placeholders: List<String>) {
    private var arguments: MutableMap<String, String?> = mutableMapOf()

    init {
        placeholders.forEach { placeholder ->
            arguments[placeholder] = null
        }
    }

    fun addArgument(
        key: String,
        value: String,
    ) {
        arguments[key] = value
    }

    fun buildRoute(): String =
        buildString {
            val uriBuilder = Uri.Builder()
            uriBuilder.appendPath(name)
            arguments.forEach { entry ->
                uriBuilder.appendQueryParameter(entry.key, entry.value.toString())
            }
            append(uriBuilder.build().toString())
        }
}
