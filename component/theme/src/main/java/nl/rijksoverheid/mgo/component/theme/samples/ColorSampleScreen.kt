@file:OptIn(ExperimentalLayoutApi::class)

package nl.rijksoverheid.mgo.component.theme.samples

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import nl.rijksoverheid.mgo.component.theme.Black
import nl.rijksoverheid.mgo.component.theme.Brown100
import nl.rijksoverheid.mgo.component.theme.Brown200
import nl.rijksoverheid.mgo.component.theme.Brown300
import nl.rijksoverheid.mgo.component.theme.Brown400
import nl.rijksoverheid.mgo.component.theme.Brown50
import nl.rijksoverheid.mgo.component.theme.Brown500
import nl.rijksoverheid.mgo.component.theme.CoolGray100
import nl.rijksoverheid.mgo.component.theme.CoolGray200
import nl.rijksoverheid.mgo.component.theme.CoolGray300
import nl.rijksoverheid.mgo.component.theme.CoolGray400
import nl.rijksoverheid.mgo.component.theme.CoolGray50
import nl.rijksoverheid.mgo.component.theme.CoolGray500
import nl.rijksoverheid.mgo.component.theme.CoolGray600
import nl.rijksoverheid.mgo.component.theme.CoolGray700
import nl.rijksoverheid.mgo.component.theme.CoolGray800
import nl.rijksoverheid.mgo.component.theme.CoolGray900
import nl.rijksoverheid.mgo.component.theme.DarkBlue100
import nl.rijksoverheid.mgo.component.theme.DarkBlue200
import nl.rijksoverheid.mgo.component.theme.DarkBlue300
import nl.rijksoverheid.mgo.component.theme.DarkBlue400
import nl.rijksoverheid.mgo.component.theme.DarkBlue50
import nl.rijksoverheid.mgo.component.theme.DarkBlue500
import nl.rijksoverheid.mgo.component.theme.DarkBlue600
import nl.rijksoverheid.mgo.component.theme.DarkBlue700
import nl.rijksoverheid.mgo.component.theme.DarkBrown100
import nl.rijksoverheid.mgo.component.theme.DarkBrown200
import nl.rijksoverheid.mgo.component.theme.DarkBrown300
import nl.rijksoverheid.mgo.component.theme.DarkBrown400
import nl.rijksoverheid.mgo.component.theme.DarkBrown50
import nl.rijksoverheid.mgo.component.theme.DarkBrown500
import nl.rijksoverheid.mgo.component.theme.DarkGreen100
import nl.rijksoverheid.mgo.component.theme.DarkGreen200
import nl.rijksoverheid.mgo.component.theme.DarkGreen300
import nl.rijksoverheid.mgo.component.theme.DarkGreen400
import nl.rijksoverheid.mgo.component.theme.DarkGreen50
import nl.rijksoverheid.mgo.component.theme.DarkGreen500
import nl.rijksoverheid.mgo.component.theme.DarkYellow100
import nl.rijksoverheid.mgo.component.theme.DarkYellow200
import nl.rijksoverheid.mgo.component.theme.DarkYellow300
import nl.rijksoverheid.mgo.component.theme.DarkYellow400
import nl.rijksoverheid.mgo.component.theme.DarkYellow50
import nl.rijksoverheid.mgo.component.theme.DarkYellow500
import nl.rijksoverheid.mgo.component.theme.DarkYellow600
import nl.rijksoverheid.mgo.component.theme.DarkYellow700
import nl.rijksoverheid.mgo.component.theme.DarkYellow800
import nl.rijksoverheid.mgo.component.theme.Gray100
import nl.rijksoverheid.mgo.component.theme.Gray200
import nl.rijksoverheid.mgo.component.theme.Gray300
import nl.rijksoverheid.mgo.component.theme.Gray400
import nl.rijksoverheid.mgo.component.theme.Gray50
import nl.rijksoverheid.mgo.component.theme.Gray500
import nl.rijksoverheid.mgo.component.theme.Gray600
import nl.rijksoverheid.mgo.component.theme.Gray700
import nl.rijksoverheid.mgo.component.theme.Gray800
import nl.rijksoverheid.mgo.component.theme.Gray900
import nl.rijksoverheid.mgo.component.theme.Gray950
import nl.rijksoverheid.mgo.component.theme.Green100
import nl.rijksoverheid.mgo.component.theme.Green200
import nl.rijksoverheid.mgo.component.theme.Green300
import nl.rijksoverheid.mgo.component.theme.Green400
import nl.rijksoverheid.mgo.component.theme.Green50
import nl.rijksoverheid.mgo.component.theme.Green500
import nl.rijksoverheid.mgo.component.theme.LabelsPrimary
import nl.rijksoverheid.mgo.component.theme.LightBlue100
import nl.rijksoverheid.mgo.component.theme.LightBlue200
import nl.rijksoverheid.mgo.component.theme.LightBlue300
import nl.rijksoverheid.mgo.component.theme.LightBlue400
import nl.rijksoverheid.mgo.component.theme.LightBlue50
import nl.rijksoverheid.mgo.component.theme.LightBlue500
import nl.rijksoverheid.mgo.component.theme.LightBlue600
import nl.rijksoverheid.mgo.component.theme.LightBlue700
import nl.rijksoverheid.mgo.component.theme.LightBlue800
import nl.rijksoverheid.mgo.component.theme.LogoBlue100
import nl.rijksoverheid.mgo.component.theme.LogoBlue200
import nl.rijksoverheid.mgo.component.theme.LogoBlue300
import nl.rijksoverheid.mgo.component.theme.LogoBlue400
import nl.rijksoverheid.mgo.component.theme.LogoBlue50
import nl.rijksoverheid.mgo.component.theme.LogoBlue500
import nl.rijksoverheid.mgo.component.theme.LogoBlue600
import nl.rijksoverheid.mgo.component.theme.LogoBlue700
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.Mint100
import nl.rijksoverheid.mgo.component.theme.Mint200
import nl.rijksoverheid.mgo.component.theme.Mint300
import nl.rijksoverheid.mgo.component.theme.Mint400
import nl.rijksoverheid.mgo.component.theme.Mint50
import nl.rijksoverheid.mgo.component.theme.Mint500
import nl.rijksoverheid.mgo.component.theme.Mint600
import nl.rijksoverheid.mgo.component.theme.Mint700
import nl.rijksoverheid.mgo.component.theme.Mint800
import nl.rijksoverheid.mgo.component.theme.Moss100
import nl.rijksoverheid.mgo.component.theme.Moss200
import nl.rijksoverheid.mgo.component.theme.Moss300
import nl.rijksoverheid.mgo.component.theme.Moss400
import nl.rijksoverheid.mgo.component.theme.Moss50
import nl.rijksoverheid.mgo.component.theme.Moss500
import nl.rijksoverheid.mgo.component.theme.Orange100
import nl.rijksoverheid.mgo.component.theme.Orange200
import nl.rijksoverheid.mgo.component.theme.Orange300
import nl.rijksoverheid.mgo.component.theme.Orange400
import nl.rijksoverheid.mgo.component.theme.Orange50
import nl.rijksoverheid.mgo.component.theme.Orange500
import nl.rijksoverheid.mgo.component.theme.Pink100
import nl.rijksoverheid.mgo.component.theme.Pink200
import nl.rijksoverheid.mgo.component.theme.Pink300
import nl.rijksoverheid.mgo.component.theme.Pink400
import nl.rijksoverheid.mgo.component.theme.Pink50
import nl.rijksoverheid.mgo.component.theme.Pink500
import nl.rijksoverheid.mgo.component.theme.Pink600
import nl.rijksoverheid.mgo.component.theme.Pink700
import nl.rijksoverheid.mgo.component.theme.Purple100
import nl.rijksoverheid.mgo.component.theme.Purple200
import nl.rijksoverheid.mgo.component.theme.Purple300
import nl.rijksoverheid.mgo.component.theme.Purple400
import nl.rijksoverheid.mgo.component.theme.Purple50
import nl.rijksoverheid.mgo.component.theme.Purple500
import nl.rijksoverheid.mgo.component.theme.Red100
import nl.rijksoverheid.mgo.component.theme.Red200
import nl.rijksoverheid.mgo.component.theme.Red300
import nl.rijksoverheid.mgo.component.theme.Red400
import nl.rijksoverheid.mgo.component.theme.Red50
import nl.rijksoverheid.mgo.component.theme.Red500
import nl.rijksoverheid.mgo.component.theme.Ruby100
import nl.rijksoverheid.mgo.component.theme.Ruby200
import nl.rijksoverheid.mgo.component.theme.Ruby300
import nl.rijksoverheid.mgo.component.theme.Ruby400
import nl.rijksoverheid.mgo.component.theme.Ruby50
import nl.rijksoverheid.mgo.component.theme.Ruby500
import nl.rijksoverheid.mgo.component.theme.SkyBlue100
import nl.rijksoverheid.mgo.component.theme.SkyBlue200
import nl.rijksoverheid.mgo.component.theme.SkyBlue300
import nl.rijksoverheid.mgo.component.theme.SkyBlue400
import nl.rijksoverheid.mgo.component.theme.SkyBlue50
import nl.rijksoverheid.mgo.component.theme.SkyBlue500
import nl.rijksoverheid.mgo.component.theme.Violet100
import nl.rijksoverheid.mgo.component.theme.Violet200
import nl.rijksoverheid.mgo.component.theme.Violet300
import nl.rijksoverheid.mgo.component.theme.Violet400
import nl.rijksoverheid.mgo.component.theme.Violet50
import nl.rijksoverheid.mgo.component.theme.Violet500
import nl.rijksoverheid.mgo.component.theme.White
import nl.rijksoverheid.mgo.component.theme.Yellow100
import nl.rijksoverheid.mgo.component.theme.Yellow200
import nl.rijksoverheid.mgo.component.theme.Yellow300
import nl.rijksoverheid.mgo.component.theme.Yellow400
import nl.rijksoverheid.mgo.component.theme.Yellow50
import nl.rijksoverheid.mgo.component.theme.Yellow500
import nl.rijksoverheid.mgo.component.theme.Yellow600
import nl.rijksoverheid.mgo.component.theme.Yellow700
import nl.rijksoverheid.mgo.component.theme.Yellow800

@Composable
private fun BasicColors() {
  Column {
    Text(text = "Basic", style = MaterialTheme.typography.headlineLarge)
    FlowRow(modifier = Modifier.padding(vertical = 16.dp)) {
      ColorSample(color = White, text = "White")
      ColorSample(color = Black, text = "Black")
    }
  }
}

@Composable
private fun GrayColors() {
  Column {
    Text(text = "Gray", style = MaterialTheme.typography.headlineLarge)
    FlowRow(modifier = Modifier.padding(top = 16.dp)) {
      ColorSample(color = Gray50, text = "Gray50")
      ColorSample(color = Gray100, text = "Gray100")
      ColorSample(color = Gray200, text = "Gray200")
      ColorSample(color = Gray300, text = "Gray300")
      ColorSample(color = Gray400, text = "Gray400")
      ColorSample(color = Gray500, text = "Gray500")
      ColorSample(color = Gray600, text = "Gray600")
      ColorSample(color = Gray700, text = "Gray700")
      ColorSample(color = Gray800, text = "Gray800")
      ColorSample(color = Gray900, text = "Gray900")
      ColorSample(color = Gray950, text = "Gray950")
    }
  }
}

@Composable
private fun CoolGrayColors() {
  Column {
    Text(text = "Cool Gray", style = MaterialTheme.typography.headlineLarge)
    FlowRow(modifier = Modifier.padding(top = 16.dp)) {
      ColorSample(color = CoolGray50, text = "CoolGray50")
      ColorSample(color = CoolGray100, text = "CoolGray100")
      ColorSample(color = CoolGray200, text = "CoolGray200")
      ColorSample(color = CoolGray300, text = "CoolGray300")
      ColorSample(color = CoolGray400, text = "CoolGray400")
      ColorSample(color = CoolGray500, text = "CoolGray500")
      ColorSample(color = CoolGray600, text = "CoolGray600")
      ColorSample(color = CoolGray700, text = "CoolGray700")
      ColorSample(color = CoolGray800, text = "CoolGray800")
      ColorSample(color = CoolGray900, text = "CoolGray900")
    }
  }
}

@Composable
private fun LogoBlueColors() {
  Column {
    Text(text = "Logo Blue", style = MaterialTheme.typography.headlineLarge)
    FlowRow(modifier = Modifier.padding(top = 16.dp)) {
      ColorSample(color = LogoBlue50, text = "LogoBlue50")
      ColorSample(color = LogoBlue100, text = "LogoBlue100")
      ColorSample(color = LogoBlue200, text = "LogoBlue200")
      ColorSample(color = LogoBlue300, text = "LogoBlue300")
      ColorSample(color = LogoBlue400, text = "LogoBlue400")
      ColorSample(color = LogoBlue500, text = "LogoBlue500")
      ColorSample(color = LogoBlue600, text = "LogoBlue600")
      ColorSample(color = LogoBlue700, text = "LogoBlue700")
    }
  }
}

@Composable
private fun SkyBlueColors() {
  Column {
    Text(text = "Sky Blue", style = MaterialTheme.typography.headlineLarge)
    FlowRow(modifier = Modifier.padding(top = 16.dp)) {
      ColorSample(color = SkyBlue50, text = "SkyBlue50")
      ColorSample(color = SkyBlue100, text = "SkyBlue100")
      ColorSample(color = SkyBlue200, text = "SkyBlue200")
      ColorSample(color = SkyBlue300, text = "SkyBlue300")
      ColorSample(color = SkyBlue400, text = "SkyBlue400")
      ColorSample(color = SkyBlue500, text = "SkyBlue500")
    }
  }
}

@Composable
private fun DarkBlueColors() {
  Column {
    Text(text = "Dark Blue", style = MaterialTheme.typography.headlineLarge)
    FlowRow(modifier = Modifier.padding(top = 16.dp)) {
      ColorSample(color = DarkBlue50, text = "DarkBlue50")
      ColorSample(color = DarkBlue100, text = "DarkBlue100")
      ColorSample(color = DarkBlue200, text = "DarkBlue200")
      ColorSample(color = DarkBlue300, text = "DarkBlue300")
      ColorSample(color = DarkBlue400, text = "DarkBlue400")
      ColorSample(color = DarkBlue500, text = "DarkBlue500")
      ColorSample(color = DarkBlue600, text = "DarkBlue600")
      ColorSample(color = DarkBlue700, text = "DarkBlue700")
    }
  }
}

@Composable
private fun LightBlueColors() {
  Column {
    Text(text = "Light Blue", style = MaterialTheme.typography.headlineLarge)
    FlowRow(modifier = Modifier.padding(top = 16.dp)) {
      ColorSample(color = LightBlue50, text = "LightBlue50")
      ColorSample(color = LightBlue100, text = "LightBlue100")
      ColorSample(color = LightBlue200, text = "LightBlue200")
      ColorSample(color = LightBlue300, text = "LightBlue300")
      ColorSample(color = LightBlue400, text = "LightBlue400")
      ColorSample(color = LightBlue500, text = "LightBlue500")
      ColorSample(color = LightBlue600, text = "LightBlue600")
      ColorSample(color = LightBlue700, text = "LightBlue700")
      ColorSample(color = LightBlue800, text = "LightBlue800")
    }
  }
}

@Composable
private fun GreenColors() {
  Column {
    Text(text = "Green", style = MaterialTheme.typography.headlineLarge)
    FlowRow(modifier = Modifier.padding(top = 16.dp)) {
      ColorSample(color = Green50, text = "Green50")
      ColorSample(color = Green100, text = "Green100")
      ColorSample(color = Green200, text = "Green200")
      ColorSample(color = Green300, text = "Green300")
      ColorSample(color = Green400, text = "Green400")
      ColorSample(color = Green500, text = "Green500")
    }
  }
}

@Composable
private fun DarkGreenColors() {
  Column {
    Text(text = "Dark Green", style = MaterialTheme.typography.headlineLarge)
    FlowRow(modifier = Modifier.padding(top = 16.dp)) {
      ColorSample(color = DarkGreen50, text = "DarkGreen50")
      ColorSample(color = DarkGreen100, text = "DarkGreen100")
      ColorSample(color = DarkGreen200, text = "DarkGreen200")
      ColorSample(color = DarkGreen300, text = "DarkGreen300")
      ColorSample(color = DarkGreen400, text = "DarkGreen400")
      ColorSample(color = DarkGreen500, text = "DarkGreen500")
    }
  }
}

@Composable
private fun MintColors() {
  Column {
    Text(text = "Mint", style = MaterialTheme.typography.headlineLarge)
    FlowRow(modifier = Modifier.padding(top = 16.dp)) {
      ColorSample(color = Mint50, text = "Mint50")
      ColorSample(color = Mint100, text = "Mint100")
      ColorSample(color = Mint200, text = "Mint200")
      ColorSample(color = Mint300, text = "Mint300")
      ColorSample(color = Mint400, text = "Mint400")
      ColorSample(color = Mint500, text = "Mint500")
      ColorSample(color = Mint600, text = "Mint600")
      ColorSample(color = Mint700, text = "Mint700")
      ColorSample(color = Mint800, text = "Mint800")
    }
  }
}

@Composable
private fun MossColors() {
  Column {
    Text(text = "Moss", style = MaterialTheme.typography.headlineLarge)
    FlowRow(modifier = Modifier.padding(top = 16.dp)) {
      ColorSample(color = Moss50, text = "Moss50")
      ColorSample(color = Moss100, text = "Moss100")
      ColorSample(color = Moss200, text = "Moss200")
      ColorSample(color = Moss300, text = "Moss300")
      ColorSample(color = Moss400, text = "Moss400")
      ColorSample(color = Moss500, text = "Moss500")
    }
  }
}

@Composable
private fun YellowColors() {
  Column {
    Text(text = "Yellow", style = MaterialTheme.typography.headlineLarge)
    FlowRow(modifier = Modifier.padding(top = 16.dp)) {
      ColorSample(color = Yellow50, text = "Yellow50")
      ColorSample(color = Yellow100, text = "Yellow100")
      ColorSample(color = Yellow200, text = "Yellow200")
      ColorSample(color = Yellow300, text = "Yellow300")
      ColorSample(color = Yellow400, text = "Yellow400")
      ColorSample(color = Yellow500, text = "Yellow500")
      ColorSample(color = Yellow600, text = "Yellow600")
      ColorSample(color = Yellow700, text = "Yellow700")
      ColorSample(color = Yellow800, text = "Yellow800")
    }
  }
}

@Composable
private fun DarkYellowColors() {
  Column {
    Text(text = "Dark Yellow", style = MaterialTheme.typography.headlineLarge)
    FlowRow(modifier = Modifier.padding(top = 16.dp)) {
      ColorSample(color = DarkYellow50, text = "DarkYellow50")
      ColorSample(color = DarkYellow100, text = "DarkYellow100")
      ColorSample(color = DarkYellow200, text = "DarkYellow200")
      ColorSample(color = DarkYellow300, text = "DarkYellow300")
      ColorSample(color = DarkYellow400, text = "DarkYellow400")
      ColorSample(color = DarkYellow500, text = "DarkYellow500")
      ColorSample(color = DarkYellow600, text = "DarkYellow600")
      ColorSample(color = DarkYellow700, text = "DarkYellow700")
      ColorSample(color = DarkYellow800, text = "DarkYellow800")
    }
  }
}

@Composable
private fun OrangeColors() {
  Column {
    Text(text = "Orange", style = MaterialTheme.typography.headlineLarge)
    FlowRow(modifier = Modifier.padding(top = 16.dp)) {
      ColorSample(color = Orange50, text = "Orange50")
      ColorSample(color = Orange100, text = "Orange100")
      ColorSample(color = Orange200, text = "Orange200")
      ColorSample(color = Orange300, text = "Orange300")
      ColorSample(color = Orange400, text = "Orange400")
      ColorSample(color = Orange500, text = "Orange500")
    }
  }
}

@Composable
private fun RedColors() {
  Column {
    Text(text = "Red", style = MaterialTheme.typography.headlineLarge)
    FlowRow(modifier = Modifier.padding(top = 16.dp)) {
      ColorSample(color = Red50, text = "Red50")
      ColorSample(color = Red100, text = "Red100")
      ColorSample(color = Red200, text = "Red200")
      ColorSample(color = Red300, text = "Red300")
      ColorSample(color = Red400, text = "Red400")
      ColorSample(color = Red500, text = "Red500")
    }
  }
}

@Composable
private fun RubyColors() {
  Column {
    Text(text = "Ruby", style = MaterialTheme.typography.headlineLarge)
    FlowRow(modifier = Modifier.padding(top = 16.dp)) {
      ColorSample(color = Ruby50, text = "Ruby50")
      ColorSample(color = Ruby100, text = "Ruby100")
      ColorSample(color = Ruby200, text = "Ruby200")
      ColorSample(color = Ruby300, text = "Ruby300")
      ColorSample(color = Ruby400, text = "Ruby400")
      ColorSample(color = Ruby500, text = "Ruby500")
    }
  }
}

@Composable
private fun VioletColors() {
  Column {
    Text(text = "Violet", style = MaterialTheme.typography.headlineLarge)
    FlowRow(modifier = Modifier.padding(top = 16.dp)) {
      ColorSample(color = Violet50, text = "Violet50")
      ColorSample(color = Violet100, text = "Violet100")
      ColorSample(color = Violet200, text = "Violet200")
      ColorSample(color = Violet300, text = "Violet300")
      ColorSample(color = Violet400, text = "Violet400")
      ColorSample(color = Violet500, text = "Violet500")
    }
  }
}

@Composable
private fun PinkColors() {
  Column {
    Text(text = "Pink", style = MaterialTheme.typography.headlineLarge)
    FlowRow(modifier = Modifier.padding(top = 16.dp)) {
      ColorSample(color = Pink50, text = "Pink50")
      ColorSample(color = Pink100, text = "Pink100")
      ColorSample(color = Pink200, text = "Pink200")
      ColorSample(color = Pink300, text = "Pink300")
      ColorSample(color = Pink400, text = "Pink400")
      ColorSample(color = Pink500, text = "Pink500")
      ColorSample(color = Pink600, text = "Pink600")
      ColorSample(color = Pink700, text = "Pink700")
    }
  }
}

@Composable
private fun PurpleColors() {
  Column {
    Text(text = "Purple", style = MaterialTheme.typography.headlineLarge)
    FlowRow(modifier = Modifier.padding(top = 16.dp)) {
      ColorSample(color = Purple50, text = "Purple50")
      ColorSample(color = Purple100, text = "Purple100")
      ColorSample(color = Purple200, text = "Purple200")
      ColorSample(color = Purple300, text = "Purple300")
      ColorSample(color = Purple400, text = "Purple400")
      ColorSample(color = Purple500, text = "Purple500")
    }
  }
}

@Composable
private fun BrownColors() {
  Column {
    Text(text = "Purple", style = MaterialTheme.typography.headlineLarge)
    FlowRow(modifier = Modifier.padding(top = 16.dp)) {
      ColorSample(color = Brown50, text = "Brown50")
      ColorSample(color = Brown100, text = "Brown100")
      ColorSample(color = Brown200, text = "Brown200")
      ColorSample(color = Brown300, text = "Brown300")
      ColorSample(color = Brown400, text = "Brown400")
      ColorSample(color = Brown500, text = "Brown500")
    }
  }
}

@Composable
private fun DarkBrownColors() {
  Column {
    Text(text = "Dark Brown", style = MaterialTheme.typography.headlineLarge)
    FlowRow(modifier = Modifier.padding(top = 16.dp)) {
      ColorSample(color = DarkBrown50, text = "DarkBrown50")
      ColorSample(color = DarkBrown100, text = "DarkBrown100")
      ColorSample(color = DarkBrown200, text = "DarkBrown200")
      ColorSample(color = DarkBrown300, text = "DarkBrown300")
      ColorSample(color = DarkBrown400, text = "DarkBrown400")
      ColorSample(color = DarkBrown500, text = "DarkBrown500")
    }
  }
}

@Composable
private fun ColorSample(
  color: Color,
  text: String,
) {
  Box(
    modifier =
      Modifier
        .fillMaxWidth()
        .background(color)
        .border(0.1.dp, Color(0xFFE6E6E6))
        .padding(2.dp),
  ) {
    Text(
      modifier = Modifier.padding(8.dp),
      text = text,
      style = MaterialTheme.typography.bodyMedium.copy(fontSize = 10.sp),
      color = color.getTextColor(),
      fontWeight = FontWeight.Bold,
      textAlign = TextAlign.Center,
    )
  }
}

@Composable
private fun Color.getTextColor(): Color =
  if (isSystemInDarkTheme()) {
    if (luminance() <= 0.5f) {
      MaterialTheme.colorScheme.LabelsPrimary()
    } else {
      MaterialTheme.colorScheme
        .LabelsPrimary(!isSystemInDarkTheme())
    }
  } else {
    if (luminance() >= 0.5f) {
      MaterialTheme.colorScheme.LabelsPrimary()
    } else {
      MaterialTheme.colorScheme
        .LabelsPrimary(!isSystemInDarkTheme())
    }
  }

@PreviewLightDark
@Composable
internal fun BasicColorsPreview() {
  MgoTheme {
    Box(modifier = Modifier.padding(16.dp)) {
      BasicColors()
    }
  }
}

@PreviewLightDark
@Composable
internal fun GrayColorsPreview() {
  MgoTheme {
    Box(modifier = Modifier.padding(16.dp)) {
      GrayColors()
    }
  }
}

@PreviewLightDark
@Composable
internal fun CoolGrayColorsPreview() {
  MgoTheme {
    Box(modifier = Modifier.padding(16.dp)) {
      CoolGrayColors()
    }
  }
}

@PreviewLightDark
@Composable
internal fun LogoBlueColorsPreview() {
  MgoTheme {
    Box(modifier = Modifier.padding(16.dp)) {
      LogoBlueColors()
    }
  }
}

@PreviewLightDark
@Composable
internal fun SkyBlueColorsPreview() {
  MgoTheme {
    Box(modifier = Modifier.padding(16.dp)) {
      SkyBlueColors()
    }
  }
}

@PreviewLightDark
@Composable
internal fun DarkBlueColorsPreview() {
  MgoTheme {
    Box(modifier = Modifier.padding(16.dp)) {
      DarkBlueColors()
    }
  }
}

@PreviewLightDark
@Composable
internal fun LightBlueColorsPreview() {
  MgoTheme {
    Box(modifier = Modifier.padding(16.dp)) {
      LightBlueColors()
    }
  }
}

@PreviewLightDark
@Composable
internal fun GreenColorsPreview() {
  MgoTheme {
    Box(modifier = Modifier.padding(16.dp)) {
      GreenColors()
    }
  }
}

@PreviewLightDark
@Composable
internal fun DarkGreenColorsPreview() {
  MgoTheme {
    Box(modifier = Modifier.padding(16.dp)) {
      DarkGreenColors()
    }
  }
}

@PreviewLightDark
@Composable
internal fun MintColorsPreview() {
  MgoTheme {
    Box(modifier = Modifier.padding(16.dp)) {
      MintColors()
    }
  }
}

@PreviewLightDark
@Composable
internal fun MossColorsPreview() {
  MgoTheme {
    Box(modifier = Modifier.padding(16.dp)) {
      MossColors()
    }
  }
}

@PreviewLightDark
@Composable
internal fun YellowColorsPreview() {
  MgoTheme {
    Box(modifier = Modifier.padding(16.dp)) {
      YellowColors()
    }
  }
}

@PreviewLightDark
@Composable
internal fun DarkYellowColorsPreview() {
  MgoTheme {
    Box(modifier = Modifier.padding(16.dp)) {
      DarkYellowColors()
    }
  }
}

@PreviewLightDark
@Composable
internal fun OrangeColorsPreview() {
  MgoTheme {
    Box(modifier = Modifier.padding(16.dp)) {
      OrangeColors()
    }
  }
}

@PreviewLightDark
@Composable
internal fun RedColorsPreview() {
  MgoTheme {
    Box(modifier = Modifier.padding(16.dp)) {
      RedColors()
    }
  }
}

@PreviewLightDark
@Composable
internal fun RubyColorsPreview() {
  MgoTheme {
    Box(modifier = Modifier.padding(16.dp)) {
      RubyColors()
    }
  }
}

@PreviewLightDark
@Composable
internal fun VioletColorsPreview() {
  MgoTheme {
    Box(modifier = Modifier.padding(16.dp)) {
      VioletColors()
    }
  }
}

@PreviewLightDark
@Composable
internal fun PinkColorsPreview() {
  MgoTheme {
    Box(modifier = Modifier.padding(16.dp)) {
      PinkColors()
    }
  }
}

@PreviewLightDark
@Composable
internal fun PurpleColorsPreview() {
  MgoTheme {
    Box(modifier = Modifier.padding(16.dp)) {
      PurpleColors()
    }
  }
}

@PreviewLightDark
@Composable
internal fun BrownColorsPreview() {
  MgoTheme {
    Box(modifier = Modifier.padding(16.dp)) {
      BrownColors()
    }
  }
}

@PreviewLightDark
@Composable
internal fun DarkBrownColorsPreview() {
  MgoTheme {
    Box(modifier = Modifier.padding(16.dp)) {
      DarkBrownColors()
    }
  }
}
