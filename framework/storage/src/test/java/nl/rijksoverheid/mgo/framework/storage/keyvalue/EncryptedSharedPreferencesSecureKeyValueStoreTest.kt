package nl.rijksoverheid.mgo.framework.storage.keyvalue

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.test.core.app.ApplicationProvider
import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
internal class EncryptedSharedPreferencesSecureKeyValueStoreTest {
  private val context = ApplicationProvider.getApplicationContext<Context>()
  private val sharedPreferences = context.getSharedPreferences("test_prefs", Context.MODE_PRIVATE)
  private val keyValueStore = EncryptedSharedPreferencesSecureKeyValueStore(sharedPreferences)

  @Test
  fun validateBoolean() =
    runTest {
      // Given
      val preferenceKey1 = booleanPreferencesKey("test1")
      val preferenceKey2 = booleanPreferencesKey("test2")

      // When
      keyValueStore.setBoolean(preferenceKey1, true)
      keyValueStore.setBoolean(preferenceKey2, true)
      keyValueStore.removeBoolean(preferenceKey2)

      // Then
      assertTrue(keyValueStore.getBoolean(preferenceKey1))
      assertFalse(keyValueStore.getBoolean(preferenceKey2))
    }

  @Test
  fun validateObserveBoolean() =
    runTest {
      // Given: boolean preference
      val preferenceKey1 = booleanPreferencesKey("test1")
      keyValueStore.setBoolean(preferenceKey1, true)

      // When: Observing boolean preference
      keyValueStore.observeBoolean(preferenceKey1).test {
        // Then: value is emitted
        assertTrue(awaitItem())

        // When: updating boolean
        keyValueStore.setBoolean(preferenceKey1, false)

        // Then: value is emitted
        assertFalse(awaitItem())
      }
    }

  @Test
  fun validateString() =
    runTest {
      // Given
      val preferenceKey1 = stringPreferencesKey("test1")
      val preferenceKey2 = stringPreferencesKey("test2")

      // When
      keyValueStore.setString(preferenceKey1, "123")
      keyValueStore.setString(preferenceKey2, "123")
      keyValueStore.removeString(preferenceKey2)

      // Then
      assertEquals("123", keyValueStore.getString(preferenceKey1))
      assertNull(keyValueStore.getString(preferenceKey2))
    }

  @Test
  fun validateObserveString() =
    runTest {
      // Given: string preference
      val preferenceKey1 = stringPreferencesKey("test1")
      keyValueStore.setString(preferenceKey1, "123")

      // When: Observing string preference
      keyValueStore.observeString(preferenceKey1).test {
        // Then: value is emitted
        assertEquals("123", awaitItem())

        // When: updating string
        keyValueStore.setString(preferenceKey1, "321")

        // Then: value is emitted
        assertEquals("321", awaitItem())
      }
    }

  @Test
  fun validateStringSet() =
    runTest {
      // Given
      val preferenceKey1 = stringSetPreferencesKey("test1")
      val preferenceKey2 = stringSetPreferencesKey("test2")

      // When
      keyValueStore.setStringSet(preferenceKey1, setOf("123", "456"))
      keyValueStore.setStringSet(preferenceKey2, setOf("789", "123"))
      keyValueStore.removeStringSet(preferenceKey2)

      // Then
      assertEquals(setOf("123", "456"), keyValueStore.getStringSet(preferenceKey1))
      assertNull(keyValueStore.getStringSet(preferenceKey2))
    }

  @Test
  fun validateObserveStringSet() =
    runTest {
      // Given: string set preference
      val preferenceKey1 = stringSetPreferencesKey("test1")
      keyValueStore.setStringSet(preferenceKey1, setOf("123", "456"))

      // When: Observing string set preference
      keyValueStore.observeStringSet(preferenceKey1).test {
        // Then: value is emitted
        assertEquals(setOf("123", "456"), awaitItem())

        // When: updating string set
        keyValueStore.setStringSet(preferenceKey1, setOf("789", "123"))

        // Then: value is emitted
        assertEquals(setOf("789", "123"), awaitItem())
      }
    }

  @Test
  fun validateLong() =
    runTest {
      // Given
      val preferenceKey1 = longPreferencesKey("test1")
      val preferenceKey2 = longPreferencesKey("test2")

      // When
      keyValueStore.setLong(preferenceKey1, 1L)
      keyValueStore.setLong(preferenceKey2, 2L)
      keyValueStore.removeLong(preferenceKey2)

      // Then
      assertEquals(1L, keyValueStore.getLong(preferenceKey1))
      assertEquals(0L, keyValueStore.getLong(preferenceKey2))
    }

  @Test
  fun validateClear() =
    runTest {
      // Given
      val preferenceKey = booleanPreferencesKey("test")
      keyValueStore.setBoolean(preferenceKey, true)

      // When
      keyValueStore.clear()

      // Then
      assertEquals(false, keyValueStore.getBoolean(preferenceKey))
    }
}
