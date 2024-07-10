package nl.rijksoverheid.mgo.navigation

import android.net.Uri

abstract class NavigationScreen(open val name: String, open val placeholders: List<String> = listOf()) {
    protected val builder by lazy { NavigationRouteBuilder(name = name, placeholders = placeholders) }

    fun getRoute(): String =
        buildString {
            val uriBuilder = Uri.Builder()
            uriBuilder.appendPath(name)
            placeholders.forEach { placeholder ->
                uriBuilder.appendQueryParameter(placeholder, "{$placeholder}")
            }
            append(uriBuilder.build().toString())
        }

    open fun getNavigationRoute(): String {
        return builder.buildRoute()
    }
}
