package nl.rijksoverheid.mgo.feature.pincode.forgot

import app.cash.turbine.test
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KEY_PIN_CODE
import nl.rijksoverheid.mgo.framework.storage.keyvalue.TestKeyValueStore
import org.junit.Assert.assertEquals
import org.junit.Test
import kotlinx.coroutines.test.runTest

internal class PinCodeForgotScreenViewModelTest {
    @Test
    fun `Given something stored in store, When calling createAccount, Then clear store and navigate to pin code create`() =
        runTest {
            // Given
            val keyValueStore = TestKeyValueStore()
            keyValueStore.setString(KEY_PIN_CODE, "123")

            val viewModel = PinCodeForgotScreenViewModel(keyValueStore)

            viewModel.navigateToPinCodeCreate.test {
                // When
                viewModel.createNewAccount()

                // Then
                assertEquals(null, keyValueStore.getString(KEY_PIN_CODE))
                assertEquals(Unit, awaitItem())
            }
        }
}
