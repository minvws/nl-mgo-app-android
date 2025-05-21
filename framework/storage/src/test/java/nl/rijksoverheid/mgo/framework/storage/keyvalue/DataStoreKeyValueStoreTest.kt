package nl.rijksoverheid.mgo.framework.storage.keyvalue

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
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
internal class DataStoreKeyValueStoreTest {
  private val context = ApplicationProvider.getApplicationContext<Context>()

  companion object {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "test")
  }

  @Test
  fun validateBoolean() =
    runTest {
      // Given
      val keyValueStore = DataStoreKeyValueStore(dataStore = context.dataStore)

      // When
      keyValueStore.setBoolean(KEY_HAS_SEEN_ONBOARDING, true)
      keyValueStore.setBoolean(KEY_LOGIN_WITH_BIOMETRIC_ENABLED, true)
      keyValueStore.setBoolean(KEY_IS_ROOT_CHECKED, true)
      keyValueStore.removeBoolean(KEY_IS_ROOT_CHECKED)

      // Then
      assertTrue(keyValueStore.getBoolean(KEY_HAS_SEEN_ONBOARDING))
      assertTrue(keyValueStore.getBoolean(KEY_LOGIN_WITH_BIOMETRIC_ENABLED))
      assertFalse(keyValueStore.getBoolean(KEY_IS_ROOT_CHECKED))
      keyValueStore.observeBoolean(KEY_HAS_SEEN_ONBOARDING).test {
        assertTrue(awaitItem())
      }
    }

  @Test
  fun validateString() =
    runTest {
      // Given
      val preferenceKey1 = stringPreferencesKey("test1")
      val preferenceKey2 = stringPreferencesKey("test2")
      val keyValueStore = DataStoreKeyValueStore(dataStore = context.dataStore)

      // When
      keyValueStore.setString(preferenceKey1, "123")
      keyValueStore.setString(preferenceKey2, "123")
      keyValueStore.removeString(preferenceKey2)

      // Then
      assertEquals("123", keyValueStore.getString(preferenceKey1))
      assertNull(keyValueStore.getString(preferenceKey2))
      keyValueStore.observeString(preferenceKey1).test {
        assertEquals("123", awaitItem())
      }
    }

  @Test
  fun validateLong() =
    runTest {
      // Given
      val preferenceKey1 = longPreferencesKey("test1")
      val preferenceKey2 = longPreferencesKey("test2")
      val keyValueStore = DataStoreKeyValueStore(dataStore = context.dataStore)

      // When
      keyValueStore.setLong(preferenceKey1, 1L)
      keyValueStore.setLong(preferenceKey2, 2L)
      keyValueStore.removeLong(preferenceKey2)

      // Then
      assertEquals(1L, keyValueStore.getLong(preferenceKey1))
      assertNull(keyValueStore.getLong(preferenceKey2))
    }

  @Test
  fun validateClear() =
    runTest {
      // Given
      val preferenceKey = booleanPreferencesKey("test")
      val keyValueStore = DataStoreKeyValueStore(dataStore = context.dataStore)
      keyValueStore.setBoolean(preferenceKey, true)

      // When
      keyValueStore.clear()

      // Then
      assertEquals(false, keyValueStore.getBoolean(preferenceKey))
    }
}
