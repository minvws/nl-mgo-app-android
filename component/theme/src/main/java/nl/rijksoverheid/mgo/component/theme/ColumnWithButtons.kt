package nl.rijksoverheid.mgo.component.theme

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import nl.rijksoverheid.mgo.component.theme.composable.MgoButton
import nl.rijksoverheid.mgo.component.theme.composable.MgoButtonTheme

const val TEST_TAG_COLUMN_WITH_BUTTON_PRIMARY_BUTTON = "COLUMN_WITH_BUTTON_PRIMARY_BUTTON"
const val TEST_TAG_COLUMN_WITH_BUTTON_SECONDARY_BUTTON = "COLUMN_WITH_BUTTON_SECONDARY_BUTTON"
internal const val TEST_TAG_COLUMN_WITH_BUTTON_ELEVATION = "COLUMN_WITH_BUTTON_ELEVATION"
internal const val TEST_TAG_COLUMN_WITH_BUTTON_SCROLLABLE_COLUMN = "COLUMN_WITH_BUTTON_SCROLLABLE_COLUMN"

/**
 * A column with a button fixed to the bottom. This button will automatically add elevation when the column is scrollable (as per design).
 */
@Composable
fun ColumnWithButtons(
    buttonText: String,
    onButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
    secondaryButtonText: String? = null,
    onSecondaryButtonClick: (() -> Unit)? = null,
    contentPadding: PaddingValues = PaddingValues(),
    buttonPadding: PaddingValues = PaddingValues(),
    columnContent: @Composable ColumnScope.() -> Unit,
) {
    val scrollState = rememberScrollState()
    val canScrollForward = if (LocalInspectionMode.current) false else scrollState.canScrollForward
    ColumnWithButtonsContent(
        modifier = modifier,
        scrollState = scrollState,
        canScrollForward = canScrollForward,
        buttonText = buttonText,
        onButtonClick = { onButtonClick() },
        secondaryButtonText = secondaryButtonText,
        onSecondaryButtonClick = onSecondaryButtonClick,
        contentPadding = contentPadding,
        columnContent = columnContent,
        buttonPadding = buttonPadding,
    )
}

@Composable
internal fun ColumnWithButtonsContent(
    scrollState: ScrollState,
    canScrollForward: Boolean,
    buttonText: String,
    onButtonClick: () -> Unit,
    contentPadding: PaddingValues,
    buttonPadding: PaddingValues,
    modifier: Modifier = Modifier,
    secondaryButtonText: String? = null,
    onSecondaryButtonClick: (() -> Unit)? = null,
    columnContent: @Composable ColumnScope.() -> Unit,
) {
    Column(modifier = modifier.fillMaxHeight()) {
        Column(
            modifier =
                Modifier
                    .weight(1f)
                    .verticalScroll(scrollState)
                    .testTag(TEST_TAG_COLUMN_WITH_BUTTON_SCROLLABLE_COLUMN)
                    .padding(contentPadding),
            content = columnContent,
        )
        if (canScrollForward) {
            Box(
                modifier =
                    Modifier.fillMaxWidth().height(2.dp).shadow(elevation = 1.dp, spotColor = Color.Gray)
                        .testTag(TEST_TAG_COLUMN_WITH_BUTTON_ELEVATION),
            )
        }
        val background = if (canScrollForward) MaterialTheme.colorScheme.surface else Color.Transparent

        Column(modifier = Modifier.fillMaxWidth().background(background).padding(buttonPadding)) {
            if (secondaryButtonText != null && onSecondaryButtonClick != null) {
                MgoButton(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .testTag(TEST_TAG_COLUMN_WITH_BUTTON_SECONDARY_BUTTON)
                            .padding(bottom = 16.dp),
                    buttonText = secondaryButtonText,
                    onClick = onSecondaryButtonClick,
                    buttonTheme = MgoButtonTheme.SECONDARY_DEFAULT,
                )
            }
            MgoButton(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .testTag(TEST_TAG_COLUMN_WITH_BUTTON_PRIMARY_BUTTON),
                buttonText = buttonText,
                onClick = onButtonClick,
            )
        }
    }
}

@PreviewLightDark
@Composable
internal fun NotScrollingPreview() {
    MgoTheme {
        ColumnWithButtonsContent(
            buttonText = "Lorem ipsum",
            onButtonClick = {},
            canScrollForward = false,
            contentPadding = PaddingValues(16.dp),
            buttonPadding = PaddingValues(16.dp),
            scrollState = rememberScrollState(),
        ) {
            PreviewTextNotScrolling()
        }
    }
}

@DefaultPreviews
@Composable
internal fun ScrollingPreview() {
    MgoTheme {
        ColumnWithButtonsContent(
            buttonText = "Lorem ipsum",
            onButtonClick = {},
            canScrollForward = true,
            contentPadding = PaddingValues(16.dp),
            buttonPadding = PaddingValues(16.dp),
            scrollState = rememberScrollState(),
        ) {
            PreviewTextScrolling()
        }
    }
}

@PreviewLightDark
@Composable
internal fun TwoButtonsPreview() {
    MgoTheme {
        ColumnWithButtonsContent(
            buttonText = "Lorem ipsum",
            onButtonClick = {},
            secondaryButtonText = "Lorem ipsum 2",
            onSecondaryButtonClick = {},
            canScrollForward = true,
            contentPadding = PaddingValues(16.dp),
            buttonPadding = PaddingValues(16.dp),
            scrollState = rememberScrollState(),
        ) {
            PreviewTextScrolling()
        }
    }
}

@Composable
internal fun PreviewTextNotScrolling() {
    Text(
        text =
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt " +
                "ut labore et dolore magna aliqua.",
    )
}

@Composable
internal fun PreviewTextScrolling() {
    Text(
        text =
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et " +
                "dolore magna aliqua. Facilisi morbi tempus iaculis urna id volutpat. Iaculis at erat pellentesque adipiscing. " +
                "Odio ut sem nulla pharetra diam. Dictum non consectetur a erat nam at lectus urna duis. In massa tempor nec " +
                "feugiat nisl pretium fusce id velit. Egestas sed tempus urna et pharetra pharetra. Luctus accumsan tortor " +
                "posuere ac ut consequat. Vel pretium lectus quam id. Amet cursus sit amet dictum sit amet. Rutrum tellus " +
                "pellentesque eu tincidunt tortor. Venenatis urna cursus eget nunc scelerisque viverra mauris in aliquam. " +
                "Eu lobortis elementum nibh tellus molestie nunc. Nibh tellus molestie nunc non blandit massa. Interdum " +
                "consectetur libero id faucibus nisl. Quis risus sed vulputate odio ut enim blandit volutpat maecenas. " +
                "Mauris vitae ultricies leo integer malesuada nunc vel risus commodo. Feugiat in ante metus dictum. I" +
                "n massa tempor nec feugiat nisl pretium fusce id. Proin sed libero enim sed. Ac tortor dignissim " +
                "convallis aenean et tortor at risus. Hac habitasse platea dictumst quisque sagittis purus. Eget " +
                "dolor morbi non arcu. Purus non enim praesent elementum facilisis leo vel. Vestibulum lorem sed " +
                "risus ultricies tristique nulla aliquet enim tortor. Morbi enim nunc faucibus a. Scelerisque viverra " +
                "mauris in aliquam sem fringilla ut morbi tincidunt. Purus semper eget duis at tellus at. Curabitur " +
                "vitae nunc sed velit dignissim sodales ut. Pretium viverra suspendisse potenti nullam ac. Id semper " +
                "risus in hendrerit gravida rutrum. Morbi leo urna molestie at. Imperdiet sed euismod nisi porta. Vitae " +
                "tortor condimentum lacinia quis vel eros. Orci ac auctor augue mauris augue. Vel elit scelerisque mauris " +
                "pellentesque pulvinar pellentesque. Commodo viverra maecenas accumsan lacus. Et odio pellentesque diam " +
                "volutpat commodo. Enim facilisis gravida neque convallis a cras. Diam vel quam elementum pulvinar etiam " +
                "non quam. Enim praesent elementum facilisis leo vel. Cursus turpis massa tincidunt dui ut ornare lectus " +
                "sit amet. Orci sagittis eu volutpat odio facilisis mauris. Nunc mi ipsum faucibus vitae. Sit amet est " +
                "placerat in egestas erat. Orci dapibus ultrices in iaculis nunc sed. Nibh praesent tristique magna sit " +
                "amet purus gravida quis. At augue eget arcu dictum varius duis at consectetur lorem. Senectus et netus " +
                "et malesuada fames ac turpis. Eu scelerisque felis imperdiet proin fermentum leo vel orci. Ut porttitor " +
                "leo a diam sollicitudin. Id ornare arcu odio ut sem nulla pharetra. A pellentesque sit amet porttitor eget." +
                " Pulvinar elementum integer enim neque. Et netus et malesuada fames ac turpis egestas. Malesuada bibendum " +
                "arcu vitae elementum. Montes nascetur ridiculus mus mauris vitae ultricies. Auctor augue mauris augue neque " +
                "gravida in fermentum et sollicitudin. Vestibulum rhoncus est pellentesque elit ullamcorper dignissim cras. " +
                "Bibendum at varius vel pharetra vel turpis. Ultrices mi tempus imperdiet nulla malesuada pellentesque. " +
                "Scelerisque fermentum dui faucibus in ornare quam viverra orci. Vulputate ut pharetra sit amet aliquam id. " +
                "Metus vulputate eu scelerisque felis. Mollis nunc sed id semper risus in hendrerit gravida rutrum. Tortor " +
                "id aliquet lectus proin nibh nisl. Turpis cursus in hac habitasse platea. Massa tincidunt nunc pulvinar sapien.",
    )
}
