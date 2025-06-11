package nl.rijksoverheid.mgo.component.pdfViewer

import nl.rijksoverheid.mgo.framework.snapshots.SnapshotTestRule
import org.junit.Rule
import org.junit.Test

internal class PdfViewerBottomSheetSnapshotTest {
  @get:Rule
  val snapshotTestRule = SnapshotTestRule()

  @Test
  fun loading() {
    snapshotTestRule.snapshots {
      PdfViewerLoadingPreview()
    }
  }
}
