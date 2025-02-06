package nl.rijksoverheid.mgo.lifecycle

sealed class AppLifecycleState {
    data object FromBackground : AppLifecycleState()

    data object ToBackground : AppLifecycleState()
}
