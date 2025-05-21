package nl.rijksoverheid.mgo.feature.digid

import app.cash.turbine.test
import app.cash.turbine.turbineScope
import kotlinx.coroutines.test.runTest
import nl.rijksoverheid.mgo.data.digid.TestDigidRepository
import nl.rijksoverheid.mgo.framework.test.rules.MainDispatcherRule
import nl.rijksoverheid.mgo.framework.util.base64.TestBase64Util
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

internal class DigidLoginScreenViewModelTest {
  @get:Rule
  val mainDispatcherRule = MainDispatcherRule()

  private val digidRepository = TestDigidRepository()
  private val base64Util = TestBase64Util()
  private val viewModel =
    DigidLoginScreenViewModel(
      digidRepository = digidRepository,
      base64Util = base64Util,
    )

  @Test
  fun testLoginSuccess() =
    runTest {
      turbineScope {
        val navigateToUrlFlow = viewModel.navigateToUrl.testIn(backgroundScope)

        // Given: login success
        digidRepository.setLoginResult(Result.success("https://www.google.com"))

        // When: Calling login
        viewModel.login()

        // Then: View state is updated
        viewModel.viewState.test {
          assertEquals(DigidLoginScreenViewState(loading = false), awaitItem())
        }

        // Then: Navigate to url
        assertEquals("https://www.google.com", navigateToUrlFlow.awaitItem())
      }
    }

  @Test
  fun testLoginFailure() =
    runTest {
      turbineScope {
        val navigateToUrlFlow = viewModel.navigateToUrl.testIn(backgroundScope)

        // Given: login success
        digidRepository.setLoginResult(Result.failure(IllegalStateException("Something went wrong")))

        // When: Calling login
        viewModel.login()

        // Then: View state is updated
        viewModel.viewState.test {
          assertEquals(DigidLoginScreenViewState(loading = false), awaitItem())
        }

        // Then: Do not navigate to url
        navigateToUrlFlow.expectNoEvents()
      }
    }

  @Test
  fun testHandleDeeplinkSuccess() =
    runTest {
      turbineScope {
        val loginFinishedFlow = viewModel.loginFinished.testIn(backgroundScope)

        // Given: valid uri string
        val uriString = "mgo://app?userinfo=user"

        // When: Calling handleDeeplink
        viewModel.handleDeeplink(uriString)

        // Then: Login is finished
        assertEquals(Unit, loginFinishedFlow.awaitItem())
      }
    }

  @Test
  fun testHandleDeeplinkFailure() =
    runTest {
      turbineScope {
        val loginFinishedFlow = viewModel.loginFinished.testIn(backgroundScope)

        // Given: valid uri string
        val uriString = null

        // When: Calling handleDeeplink
        viewModel.handleDeeplink(uriString)

        // Then: Login is not finished
        loginFinishedFlow.expectNoEvents()
      }
    }
}
