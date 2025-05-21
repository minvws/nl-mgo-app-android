package nl.rijksoverheid.mgo.feature.pincode.deleted

import nl.rijksoverheid.mgo.framework.snapshots.SnapshotTestRule
import org.junit.Rule
import org.junit.Test

internal class PinCodeDeletedScreenSnapshotTest {
  @get:Rule
  val snapshotTestRule = SnapshotTestRule()

  @Test
  fun preview() {
    snapshotTestRule.snapshots {
      PinCodeDeletedScreenPreview()
    }
  }
}
