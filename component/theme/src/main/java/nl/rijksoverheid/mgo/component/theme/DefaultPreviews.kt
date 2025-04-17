@file:Suppress("ktlint:standard:filename")

package nl.rijksoverheid.mgo.component.theme

import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewFontScale
import androidx.compose.ui.tooling.preview.PreviewLightDark

/**
 * Creates snapshots for:
 * - Phone portrait light mode
 * - Phone portrait dark mode
 * - Phone landscape
 * - Unfolded foldable
 * - Tablet landscape
 */
@Retention(AnnotationRetention.BINARY)
@Target(
  AnnotationTarget.ANNOTATION_CLASS,
  AnnotationTarget.FUNCTION,
)
@PreviewLightDark
@PreviewFontScale
@Preview(
  name = "Phone - Landscape",
  device = "spec:width = 411dp, height = 891dp, orientation = landscape, dpi = 420",
)
@Preview(name = "Unfolded Foldable", device = Devices.FOLDABLE)
@Preview(name = "Tablet", device = Devices.TABLET)
annotation class DefaultPreviews
