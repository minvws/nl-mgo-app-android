package nl.rijksoverheid.mgo.framework.navigation

sealed class NavigationScreen {
    abstract fun getRoute(): String

    data object Splash : NavigationScreen() {
        override fun getRoute(): String {
            return "splash"
        }
    }

    sealed class Onboarding : NavigationScreen() {
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

        data object PrivacyStatement : Onboarding() {
            override fun getRoute(): String {
                return "privacyStatement"
            }
        }
    }

    sealed class Dashboard : NavigationScreen() {
        override fun getRoute(): String {
            return "dashboard"
        }
    }
}
