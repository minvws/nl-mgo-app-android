@file:Suppress("ktlint:standard:filename")

package nl.rijksoverheid.mgo.component.theme

import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview

/**
 * Same as @PreviewScreenSizes but without showing System UI and Desktop.
 */
@Retention(AnnotationRetention.BINARY)
@Target(
    AnnotationTarget.ANNOTATION_CLASS,
    AnnotationTarget.FUNCTION,
)
@Preview(name = "Phone", device = Devices.PHONE)
@Preview(
    name = "Phone - Landscape",
    device = "spec:width = 411dp, height = 891dp, orientation = landscape, dpi = 420",
)
@Preview(name = "Unfolded Foldable", device = Devices.FOLDABLE)
@Preview(name = "Tablet", device = Devices.TABLET)
annotation class PreviewDifferentSizes
