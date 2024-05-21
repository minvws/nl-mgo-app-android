package nl.rijksoverheid.mgo.framework.navigation

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

    fun buildRoute(): String {
        return buildString {
            append(name)
            arguments.values.forEach { argument ->
                append("/$argument")
            }
        }
    }
}
