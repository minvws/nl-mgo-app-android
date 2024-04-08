package nl.rijksoverheid.mgo.component.theme.samples

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.heightIn
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.android.ide.common.rendering.api.SessionParams
import nl.rijksoverheid.mgo.framework.snapshots.SnapshotDevices
import nl.rijksoverheid.mgo.framework.snapshots.SnapshotTestRule
import org.junit.Rule
import org.junit.Test

internal class ColorSampleScreenSnapshotTest {
    @get:Rule
    val snapshotTestRule =
        SnapshotTestRule(renderingMode = SessionParams.RenderingMode.V_SCROLL)

    @Test
    fun launchView() {
        snapshotTestRule.snapshots(SnapshotDevices.PhoneLightDarkPortrait) {
            Box(Modifier.heightIn(max = 1500.dp)) {
                ColorSampleScreenPreview()
            }
        }
    }
}
