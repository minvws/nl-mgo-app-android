package nl.rijksoverheid.mgo.framework.test

import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain

@OptIn(ExperimentalCoroutinesApi::class)
class TestCoroutineRule(
    private val testDispatcher: TestDispatcher = UnconfinedTestDispatcher(),
) : TestRule {
    override fun apply(
        base: Statement?,
        description: Description?,
    ): Statement {
        return object : Statement() {
            override fun evaluate() {
                try {
                    Dispatchers.setMain(testDispatcher)
                    base?.evaluate() // Execute the test method
                } finally {
                    Dispatchers.resetMain()
                }
            }
        }
    }
}
