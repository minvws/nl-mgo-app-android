package nl.rijksoverheid.mgo.component.theme.composable.debugerror

import nl.rijksoverheid.mgo.framework.environment.AppFlavor
import nl.rijksoverheid.mgo.framework.environment.AppInfo
import org.junit.Assert.assertEquals
import org.junit.Test

internal class MgoDebugErrorButtonViewModelTest {
    @Test
    fun `Given production app flavor, When calling showButton, Then return false`() {
        // Given
        val appInfo = AppInfo(versionCode = 1, appFlavor = AppFlavor.PROD)

        // When
        val viewModel = MgoDebugErrorButtonViewModel(appInfo = appInfo)
        val showButton = viewModel.showButton

        // Then
        assertEquals(false, showButton)
    }

    @Test
    fun `Given not production app flavor, When calling showButton, Then return true`() {
        // Given
        val appInfo = AppInfo(versionCode = 1, appFlavor = AppFlavor.ACC)

        // When
        val viewModel = MgoDebugErrorButtonViewModel(appInfo = appInfo)
        val showButton = viewModel.showButton

        // Then
        assertEquals(true, showButton)
    }
}
