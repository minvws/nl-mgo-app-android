package nl.rijksoverheid.mgo.navigation.onboarding

import kotlinx.serialization.Serializable

sealed class OnboardingNavigation {
    @Serializable
    object Root

    @Serializable
    object Introduction

    @Serializable
    object Proposition
}
