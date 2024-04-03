package nl.rijksoverheid.mgo.framework.navigation

sealed class NavigationScreen {
    abstract fun getRoute(): String

    sealed class Onboarding : NavigationScreen() {
        data object Start : Onboarding() {
            override fun getRoute(): String {
                return "start"
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
