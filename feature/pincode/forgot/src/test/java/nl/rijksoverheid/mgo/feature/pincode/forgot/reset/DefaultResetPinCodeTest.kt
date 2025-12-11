package nl.rijksoverheid.mgo.feature.pincode.forgot.reset

import kotlinx.coroutines.test.runTest
import nl.rijksoverheid.mgo.component.organization.MgoOrganization
import nl.rijksoverheid.mgo.component.organization.TEST_MGO_ORGANIZATION
import nl.rijksoverheid.mgo.data.localisation.OrganizationRepository
import nl.rijksoverheid.mgo.framework.storage.bytearray.MemoryMgoByteArrayStorage
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KEY_LOGIN_WITH_BIOMETRIC_ENABLED
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KEY_PIN_CODE
import nl.rijksoverheid.mgo.framework.storage.keyvalue.TestKeyValueStore
import okhttp3.OkHttpClient
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Test

internal class DefaultResetPinCodeTest {
  @Test
  fun `Given specific app state, When calling use case, Then remove organizations, reset pin code and biometric flag `() =
    runTest {
      // Given: pin code 123 exists
      val keyValueStore = TestKeyValueStore()
      keyValueStore.setString(KEY_PIN_CODE, "123")

      // Given: organization is stored
      val organizationRepository = OrganizationRepository(okHttpClient = OkHttpClient(), baseUrl = "", mgoByteArrayStorage = MemoryMgoByteArrayStorage())
      organizationRepository.save(TEST_MGO_ORGANIZATION)

      // Given: use case
      val resetPinCode =
        DefaultResetPinCode(
          organizationRepository = organizationRepository,
          secureKeyValueStore = keyValueStore,
          keyValueStore = keyValueStore,
        )

      // When: Calling use case
      resetPinCode.invoke()

      // Then: Organizations are empty
      assertEquals(listOf<MgoOrganization>(), organizationRepository.get())

      // Then: Pin code is removed
      assertNull(keyValueStore.getString(KEY_PIN_CODE))

      // Then: Biometric flag is false
      assertFalse(keyValueStore.getBoolean(KEY_LOGIN_WITH_BIOMETRIC_ENABLED))
    }
}
