package nl.rijksoverheid.mgo.framework.navigation

sealed class NavigationScreen(val name: String, val placeholders: List<String> = listOf()) {
    protected var builder = NavigationRouteBuilder(name = name)

    fun getRoute(): String {
        return buildString {
            append(name)
            placeholders.forEach { placeholder ->
                append("/{$placeholder}")
            }
        }
    }

    open fun getNavigationRoute(): String {
        return getRoute()
    }

    sealed class Onboarding(name: String, placeholders: List<String> = listOf()) : NavigationScreen(name, placeholders) {
        data object Start : Onboarding("onboardingStart")

        data object Introduction : Onboarding("introduction")

        data object PrivacyOverview : Onboarding("privacyOverview")
    }

    sealed class AddHealthCare(name: String, placeholders: List<String> = listOf()) : NavigationScreen(name, placeholders) {
        data object Start : AddHealthCare("addHealthCareStart")

        data object Search : AddHealthCare("search")

        data object GetSearchResults : AddHealthCare(name = "getSearchResults", placeholders = listOf("name", "city")) {
            fun setName(name: String): GetSearchResults {
                builder.addArgument(name)
                return this
            }

            fun setCity(city: String): GetSearchResults {
                builder.addArgument(city)
                return this
            }
        }
    }

    sealed class Config(name: String, placeholders: List<String> = listOf()) : NavigationScreen(name, placeholders) {
        data object UpdatedRequired : Onboarding("updatedRequired")
    }

    data object Dashboard : NavigationScreen("dashboard")

    data class NavigationRouteBuilder(val name: String) {
        private var arguments: MutableList<String> = mutableListOf()

        fun addArgument(argument: String) {
            arguments.add(argument)
        }

        fun buildRoute(): String {
            return buildString {
                append(name)
                arguments.forEach { argument ->
                    append("/$argument")
                }
            }
        }
    }
}
