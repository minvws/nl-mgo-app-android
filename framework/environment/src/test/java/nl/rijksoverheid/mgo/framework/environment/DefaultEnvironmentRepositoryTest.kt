package nl.rijksoverheid.mgo.framework.environment

import org.junit.Assert.assertEquals
import org.junit.Test

internal class DefaultEnvironmentRepositoryTest {
  @Test
  fun testTstAppFlavor() {
    // Given
    val appFlavor = "tst"

    // When
    val repository = DefaultEnvironmentRepository(appFlavor = appFlavor, versionCode = 1, deeplinkHost = "mgo")

    // Then
    val environment = Environment.Tst(versionCode = 1, deeplinkHost = "mgo")
    assertEquals(environment, repository.getEnvironment())
  }

  @Test
  fun testAccAppFlavor() {
    // Given
    val appFlavor = "acc"

    // When
    val repository = DefaultEnvironmentRepository(appFlavor = appFlavor, versionCode = 1, deeplinkHost = "mgo")

    // Then
    val environment = Environment.Acc(versionCode = 1, deeplinkHost = "mgo")
    assertEquals(environment, repository.getEnvironment())
  }

  @Test
  fun testProdAppFlavor() {
    // Given
    val appFlavor = "prod"

    // When
    val repository = DefaultEnvironmentRepository(appFlavor = appFlavor, versionCode = 1, deeplinkHost = "mgo")

    // Then
    val environment = Environment.Prod(versionCode = 1, deeplinkHost = "mgo")
    assertEquals(environment, repository.getEnvironment())
  }

  @Test
  fun testDemoAppFlavor() {
    // Given
    val appFlavor = "demo"

    // When
    val repository = DefaultEnvironmentRepository(appFlavor = appFlavor, versionCode = 1, deeplinkHost = "mgo")

    // Then
    val environment = Environment.Demo(versionCode = 1, deeplinkHost = "mgo")
    assertEquals(environment, repository.getEnvironment())
  }

  @Test
  fun testBlaAppFlavor() {
    // Given
    val appFlavor = "bla"

    // When
    val repository = DefaultEnvironmentRepository(appFlavor = appFlavor, versionCode = 1, deeplinkHost = "mgo")

    // Then
    val environment = Environment.Tst(versionCode = 1, deeplinkHost = "mgo")
    assertEquals(environment, repository.getEnvironment())
  }
}
