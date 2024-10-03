package nl.rijksoverheid.mgo.component.snackbar

import kotlinx.coroutines.flow.Flow

interface SnackBarRepository {
    fun show(visuals: MgoSnackBarVisuals)

    fun dismiss()

    fun get(): Flow<MgoSnackBarVisuals>
}
