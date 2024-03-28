package nl.rijksoverheid.mgo.framework.environment

sealed class Environment {
    data object Tst : Environment()

    data object Acc : Environment()

    data object Prod : Environment()

    data object Custom : Environment()
}
