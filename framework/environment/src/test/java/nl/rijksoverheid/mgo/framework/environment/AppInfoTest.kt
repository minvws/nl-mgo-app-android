package nl.rijksoverheid.mgo.framework.environment

import org.junit.Assert.assertEquals
import org.junit.Test

class AppInfoTest {
    @Test
    fun `Given prod app flavor, When calling isProductionBuild, Then return true`() {
        // Given
        val flavor = AppFlavor.PROD

        // When
        val appInfo = AppInfo(versionCode = 1, appFlavor = flavor)

        // Then
        assertEquals(true, appInfo.isProductionBuild())
    }

    @Test
    fun `Given acc app flavor, When calling isProductionBuild, Then return false`() {
        // Given
        val flavor = AppFlavor.ACC

        // When
        val appInfo = AppInfo(versionCode = 1, appFlavor = flavor)

        // Then
        assertEquals(false, appInfo.isProductionBuild())
    }

    @Test
    fun `Given prod app flavor, When calling getPrivacyUrl, Then return privacy url`() {
        // Given
        val flavor = AppFlavor.PROD

        // When
        val appInfo = AppInfo(versionCode = 1, appFlavor = flavor)

        // Then
        assertEquals("https://web.test.mgo.irealisatie.nl/privacy", appInfo.getPrivacyUrl())
    }

    @Test
    fun `Given acc app flavor, When calling getPrivacyUrl, Then return privacy url`() {
        // Given
        val flavor = AppFlavor.ACC

        // When
        val appInfo = AppInfo(versionCode = 1, appFlavor = flavor)

        // Then
        assertEquals("https://web.test.mgo.irealisatie.nl/privacy", appInfo.getPrivacyUrl())
    }

    @Test
    fun `Given test app flavor, When calling getPrivacyUrl, Then return privacy url`() {
        // Given
        val flavor = AppFlavor.TEST

        // When
        val appInfo = AppInfo(versionCode = 1, appFlavor = flavor)

        // Then
        assertEquals("https://web.test.mgo.irealisatie.nl/privacy", appInfo.getPrivacyUrl())
    }
}
