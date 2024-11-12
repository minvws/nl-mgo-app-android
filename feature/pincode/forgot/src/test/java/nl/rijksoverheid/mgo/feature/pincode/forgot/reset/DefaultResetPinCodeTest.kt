package nl.rijksoverheid.mgo.feature.pincode.forgot.reset

import nl.rijksoverheid.mgo.framework.storage.keyvalue.KEY_PIN_CODE
import nl.rijksoverheid.mgo.framework.storage.keyvalue.TestFileStore
import nl.rijksoverheid.mgo.framework.storage.keyvalue.TestKeyValueStore
import org.junit.Assert.assertNull
import org.junit.Test
import kotlinx.coroutines.test.runTest

internal class DefaultResetPinCodeTest {
    @Test
    fun `Given file and pin code exists, When calling use case, Then reset pin codes and flags`() =
        runTest {
            // Given
            val fileStore = TestFileStore()
            val testData = "Hello World"
            fileStore.saveFile(value = testData, clazz = String::class, name = "file.json")

            val keyValueStore = TestKeyValueStore()
            keyValueStore.setString(KEY_PIN_CODE, "123")

            val resetPinCode =
                DefaultResetPinCode(
                    fileStore = fileStore,
                    secureKeyValueStore = keyValueStore,
                    keyValueStore = keyValueStore,
                )

            // When
            resetPinCode.invoke()

            // Then
            assertNull(fileStore.getFile(clazz = String::class, name = "file.json"))
            assertNull(keyValueStore.getString(KEY_PIN_CODE))
        }
}
