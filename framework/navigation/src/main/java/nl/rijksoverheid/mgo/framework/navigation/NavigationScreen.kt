package nl.rijksoverheid.mgo.framework.navigation

import androidx.lifecycle.SavedStateHandle

sealed class NavigationScreen(val name: String, val placeholders: List<String> = listOf()) {
    protected var builder = NavigationRouteBuilder(name = name, placeholders = placeholders)

    fun getRoute(): String {
        return buildString {
            append(name)
            placeholders.forEach { placeholder ->
                append("/{$placeholder}")
            }
        }
    }

    open fun getNavigationRoute(): String {
        return builder.buildRoute()
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
                builder.addArgument(placeholders[0], name)
                return this
            }

            fun setCity(city: String): GetSearchResults {
                builder.addArgument(placeholders[1], city)
                return this
            }

            fun getName(savedStateHandle: SavedStateHandle): String {
                return requireNotNull(savedStateHandle[placeholders[0]])
            }

            fun getCity(savedStateHandle: SavedStateHandle): String {
                return requireNotNull(savedStateHandle[placeholders[1]])
            }
        }
    }

    sealed class Config(name: String, placeholders: List<String> = listOf()) : NavigationScreen(name, placeholders) {
        data object UpdatedRequired : Onboarding("updatedRequired")
    }

    data object Dashboard : NavigationScreen("dashboard")

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
}
