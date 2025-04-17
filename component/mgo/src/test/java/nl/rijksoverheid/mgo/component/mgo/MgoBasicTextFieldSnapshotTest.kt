package nl.rijksoverheid.mgo.component.mgo

import nl.rijksoverheid.mgo.framework.snapshots.SnapshotTestRule
import org.junit.Rule
import org.junit.Test

internal class MgoBasicTextFieldSnapshotTest {
  @get:Rule
  val snapshotTestRule = SnapshotTestRule()

  @Test
  fun empty() {
    snapshotTestRule.snapshots {
      MgoBasicTextFieldEmptyPreview()
    }
  }

  @Test
  fun focussed() {
    snapshotTestRule.snapshots {
      MgoBasicTextFieldFocussedPreview()
    }
  }

  @Test
  fun filled() {
    snapshotTestRule.snapshots {
      MgoBasicTextFieldFilledPreview()
    }
  }

  @Test
  fun error() {
    snapshotTestRule.snapshots {
      MgoBasicTextFieldErrorPreview()
    }
  }
}
