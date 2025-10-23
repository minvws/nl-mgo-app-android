package nl.rijksoverheid.mgo.feature.settings.about.opensource

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.mikepenz.aboutlibraries.ui.compose.rememberLibraries
import nl.rijksoverheid.mgo.component.mgo.MgoCard
import nl.rijksoverheid.mgo.component.mgo.MgoTopAppBar
import nl.rijksoverheid.mgo.component.theme.DefaultPreviews
import nl.rijksoverheid.mgo.component.theme.LabelsSecondary
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.framework.util.launchBrowser
import java.io.InputStream
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

@Composable
fun SettingsAboutOpenSourceScreen(onNavigateBack: () -> Unit) {
  val context = LocalContext.current
  val libs by rememberLibraries {
    context.getAboutLibrariesJson()
  }
  val libraries =
    libs?.libraries?.map { lib ->
      OpenSourceLibrary(
        name = lib.name,
        description = lib.description,
        website = lib.website,
      )
    } ?: listOf()
  SettingsAboutOpenSourceScreenContent(
    libraries = libraries,
    onClickBack = onNavigateBack,
  )
}

@Composable
fun SettingsAboutOpenSourceScreenContent(
  libraries: List<OpenSourceLibrary>,
  onClickBack: () -> Unit,
) {
  val context = LocalContext.current
  Scaffold(
    topBar = {
      MgoTopAppBar(
        title = stringResource(CopyR.string.settings_about_this_app_open_source),
        onNavigateBack = onClickBack,
      )
    },
    content = { contentPadding ->
      LazyColumn(modifier = Modifier.padding(contentPadding), contentPadding = PaddingValues(16.dp)) {
        items(libraries.size) { position ->
          val library = libraries[position]
          val website = library.website
          SettingsAboutOpenSourceListItem(
            modifier =
              Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
                .then(
                  if (website != null) {
                    Modifier.clickable { context.launchBrowser(website) }
                  } else {
                    Modifier
                  },
                ),
            library = library,
          )
        }
      }
    },
  )
}

@Composable
private fun SettingsAboutOpenSourceListItem(
  library: OpenSourceLibrary,
  modifier: Modifier = Modifier,
) {
  MgoCard(modifier = modifier) {
    Column(modifier = Modifier.padding(16.dp)) {
      Text(
        text = library.name,
        style = MaterialTheme.typography.bodyMedium,
      )
      val description = library.description
      if (description != null) {
        Text(
          modifier = Modifier.padding(top = 4.dp),
          text = description,
          style = MaterialTheme.typography.bodySmall,
          color = MaterialTheme.colorScheme.LabelsSecondary(),
        )
      }
    }
  }
}

private fun Context.getAboutLibrariesJson(): String {
  val inputStream: InputStream = resources.openRawResource(R.raw.aboutlibraries)
  return inputStream.bufferedReader().use { it.readText() }
}

@DefaultPreviews
@Composable
internal fun SettingsAboutOpenSourceScreenPreview() {
  MgoTheme {
    SettingsAboutOpenSourceScreenContent(
      libraries =
        listOf(
          OpenSourceLibrary(name = "Library #1", description = "This is library #1", website = null),
          OpenSourceLibrary(name = "Library #2", description = "This is library #2", website = null),
          OpenSourceLibrary(name = "Library #3", description = "This is library #3", website = null),
        ),
      onClickBack = {},
    )
  }
}
