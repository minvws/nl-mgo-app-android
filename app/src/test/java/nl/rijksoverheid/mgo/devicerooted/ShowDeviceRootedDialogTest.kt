package nl.rijksoverheid.mgo.devicerooted

import com.scottyab.rootbeer.RootBeer
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KEY_IS_ROOT_CHECKED
import nl.rijksoverheid.mgo.framework.storage.keyvalue.TestKeyValueStore
import org.junit.Assert.assertEquals
import org.junit.Test

internal class ShowDeviceRootedDialogTest {
  @Test
  fun `Given device rooted and never returned true, When calling use case, Then return true`() =
    runTest {
      // Given
      val keyValueStore = TestKeyValueStore()
      keyValueStore.setBoolean(KEY_IS_ROOT_CHECKED, false)

      val rootBeer = mockk<RootBeer>()
      every { rootBeer.isRooted } answers { true }

      // When
      val showDeviceRootedDialog = ShowDeviceRootedDialog(rootBeer = rootBeer, keyValueStore = keyValueStore)

      // Then
      assertEquals(true, showDeviceRootedDialog())
    }

  @Test
  fun `Given device rooted and returned true before, When calling use case, Then return false`() =
    runTest {
      // Given
      val keyValueStore = TestKeyValueStore()
      keyValueStore.setBoolean(KEY_IS_ROOT_CHECKED, true)

      val rootBeer = mockk<RootBeer>()
      every { rootBeer.isRooted } answers { true }

      // When
      val showDeviceRootedDialog = ShowDeviceRootedDialog(rootBeer = rootBeer, keyValueStore = keyValueStore)

      // Then
      assertEquals(false, showDeviceRootedDialog())
    }

  @Test
  fun `Given device not rooted, When calling use case, Then return false`() =
    runTest {
      // Given
      val keyValueStore = TestKeyValueStore()
      keyValueStore.setBoolean(KEY_IS_ROOT_CHECKED, false)

      val rootBeer = mockk<RootBeer>()
      every { rootBeer.isRooted } answers { false }

      // When
      val showDeviceRootedDialog = ShowDeviceRootedDialog(rootBeer = rootBeer, keyValueStore = keyValueStore)

      // Then
      assertEquals(false, showDeviceRootedDialog())
    }
}
