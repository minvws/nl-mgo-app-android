package nl.rijksoverheid.mgo.framework.environment

import org.junit.Assert.assertEquals
import org.junit.Test

internal class DefaultEnvironmentRepositoryTest {
    @Test
    fun `Given tst app flavor, When calling getEnvironment, Return correct environment`() {
        // Given
        val appFlavor = "tst"

        // When
        val repository = DefaultEnvironmentRepository(appFlavor = appFlavor, versionCode = 1)

        // Then
        val environment = Environment.Tst(versionCode = 1)
        assertEquals(environment, repository.getEnvironment())
    }

    @Test
    fun `Given acc app flavor, When calling getEnvironment, Return correct environment`() {
        // Given
        val appFlavor = "acc"

        // When
        val repository = DefaultEnvironmentRepository(appFlavor = appFlavor, versionCode = 1)

        // Then
        val environment = Environment.Acc(versionCode = 1)
        assertEquals(environment, repository.getEnvironment())
    }

    @Test
    fun `Given prod app flavor, When calling getEnvironment, Return correct environment`() {
        // Given
        val appFlavor = "prod"

        // When
        val repository = DefaultEnvironmentRepository(appFlavor = appFlavor, versionCode = 1)

        // Then
        val environment = Environment.Prod(versionCode = 1)
        assertEquals(environment, repository.getEnvironment())
    }

    @Test
    fun `Given bla app flavor, When calling getEnvironment, Return correct environment`() {
        // Given
        val appFlavor = "bla"

        // When
        val repository = DefaultEnvironmentRepository(appFlavor = appFlavor, versionCode = 1)

        // Then
        val environment = Environment.Tst(versionCode = 1)
        assertEquals(environment, repository.getEnvironment())
    }
}
