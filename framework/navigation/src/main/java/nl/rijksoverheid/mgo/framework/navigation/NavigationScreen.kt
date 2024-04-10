package nl.rijksoverheid.mgo.framework.navigation

sealed class NavigationScreen {
    abstract fun getRoute(): String

    sealed class Onboarding : NavigationScreen() {
        data object Start : Onboarding() {
            override fun getRoute(): String {
                return "onboardingStart"
            }
        }

        data object Introduction : Onboarding() {
            override fun getRoute(): String {
                return "introduction"
            }
        }

        data object PrivacyOverview : Onboarding() {
            override fun getRoute(): String {
                return "privacyOverview"
            }
        }
    }

    sealed class AddHealthCare : NavigationScreen() {
        data object Start : Onboarding() {
            override fun getRoute(): String {
                return "addHealthCareStart"
            }
        }

        data object Search : Onboarding() {
            override fun getRoute(): String {
                return "search"
            }
        }

        data object GetSearchResults : Onboarding() {
            override fun getRoute(): String {
                return "searchResults"
            }
        }
    }

    sealed class Config : NavigationScreen() {
        data object UpdatedRequired : Onboarding() {
            override fun getRoute(): String {
                return "updateRequired"
            }
        }
    }

    data object Dashboard : NavigationScreen() {
        override fun getRoute(): String {
            return "dashboard"
        }
    }
}
