package nl.rijksoverheid.mgo.devicerooted

import com.scottyab.rootbeer.RootBeer
import kotlinx.coroutines.runBlocking
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KEY_IS_ROOT_CHECKED
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KeyValueStore

/**
 * Use case that checks if the device has been rooted.
 */
internal class ShowDeviceRootedDialog(private val rootBeer: RootBeer, private val keyValueStore: KeyValueStore) {
  /**
   * @return True once if the device is rooted and never returned true before.
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
