package nl.rijksoverheid.mgo.component.collapsablecard

import nl.rijksoverheid.mgo.component.results.ResultsScreenCardsPreview
import nl.rijksoverheid.mgo.component.results.ResultsScreenErrorPreview
import nl.rijksoverheid.mgo.component.results.ResultsScreenLoadingPreview
import nl.rijksoverheid.mgo.framework.snapshots.SnapshotTestRule
import org.junit.Rule
import org.junit.Test

internal class ResultsScreenTest {
    @get:Rule
    val snapshotTestRule = SnapshotTestRule()

    @Test
    fun loading() {
        snapshotTestRule.snapshots {
            ResultsScreenLoadingPreview()
        }
    }

    @Test
    fun cards() {
        snapshotTestRule.snapshots {
            ResultsScreenCardsPreview()
        }
    }

    @Test
    fun error() {
        snapshotTestRule.snapshots {
            ResultsScreenErrorPreview()
        }
    }
}
