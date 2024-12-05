package nl.rijksoverheid.mgo.component.theme.composable

import nl.rijksoverheid.mgo.framework.snapshots.SnapshotTestRule
import org.junit.Rule
import org.junit.Test

internal class MgoScaffoldSnapshotTest {
    @get:Rule
    val snapshotTestRule = SnapshotTestRule()

    @Test
    fun withAppBarAndBackButton() {
        snapshotTestRule.snapshots {
            MgoScaffoldWithAppBarAndBackButton()
        }
    }

    @Test
    fun withAppBar() {
        snapshotTestRule.snapshots {
            MgoScaffoldWithAppBar()
        }
    }

    @Test
    fun withoutAppBar() {
        snapshotTestRule.snapshots {
            MgoScaffoldWithoutAppBar()
        }
    }

    @Test
    fun withPrimaryButton() {
        snapshotTestRule.snapshots {
            MgoScaffoldWithPrimaryButton()
        }
    }

    @Test
    fun withPrimaryButtonScrollable() {
        snapshotTestRule.snapshots {
            MgoScaffoldWithPrimaryButtonScrollable()
        }
    }

    @Test
    fun withPrimaryAndSecondaryButton() {
        snapshotTestRule.snapshots {
            MgoScaffoldWithPrimaryButton()
        }
    }

    @Test
    fun withPrimaryAndSecondaryButtonScrollable() {
        snapshotTestRule.snapshots {
            MgoScaffoldWithPrimaryButtonScrollable()
        }
    }
}
