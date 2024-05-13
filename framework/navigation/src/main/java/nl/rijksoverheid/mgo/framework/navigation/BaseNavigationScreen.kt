package nl.rijksoverheid.mgo.framework.navigation

abstract class BaseNavigationScreen(open val name: String, open val placeholders: List<String> = listOf()) {
    protected val builder by lazy { NavigationRouteBuilder(name = name, placeholders = placeholders) }

    fun getRoute(): String {
        return buildString {
            append(name)
            placeholders.forEach { placeholder ->
                append("/{$placeholder}")
            }
        }
    }
}
