package nl.rijksoverheid.mgo.devicerooted

import com.scottyab.rootbeer.RootBeer
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KEY_IS_ROOT_CHECKED
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KeyValueStore
import kotlinx.coroutines.runBlocking

internal class ShowDeviceRootedDialog(private val rootBeer: RootBeer, private val keyValueStore: KeyValueStore) {
    /**
     * Checks if the device has been rooted.
     * @return True if device is rooted and never returned true before. False otherwise.
     */
    operator fun invoke(): Boolean {
        val isRooted = rootBeer.isRooted
        val hasSeenDialog = runBlocking { keyValueStore.getBoolean(KEY_IS_ROOT_CHECKED) }
        if (isRooted) {
            runBlocking { keyValueStore.setBoolean(KEY_IS_ROOT_CHECKED, true) }
        }
        return isRooted && !hasSeenDialog
    }
}
