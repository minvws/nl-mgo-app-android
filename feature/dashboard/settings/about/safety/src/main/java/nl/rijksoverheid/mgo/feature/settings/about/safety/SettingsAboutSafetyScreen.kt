package nl.rijksoverheid.mgo.feature.settings.about.safety

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Password
import androidx.compose.material.icons.outlined.SystemUpdate
import androidx.compose.material.icons.outlined.VerifiedUser
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material.icons.outlined.WifiPassword
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import nl.rijksoverheid.mgo.component.mgo.MgoCard
import nl.rijksoverheid.mgo.component.mgo.MgoTopAppBar
import nl.rijksoverheid.mgo.component.theme.DefaultPreviews
import nl.rijksoverheid.mgo.component.theme.LabelsPrimary
import nl.rijksoverheid.mgo.component.theme.LabelsSecondary
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.SymbolsPrimary
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

/**
 * Composable that shows a screen to inform the user how to safely use apps.
 *
 * @param onNavigateBack Called when requested to navigate back.
 */
@Composable
fun SettingsAboutSafetyScreen(onNavigateBack: () -> Unit) {
  val safetyItems =
    remember {
      listOf(
        SafetyItem(
          icon = Icons.Outlined.Password,
          heading = CopyR.string.settings_about_this_app_safety_security_phone_heading,
          subHeading = CopyR.string.settings_about_this_app_safety_security_phone_subheading,
        ),
        SafetyItem(
          icon = Icons.Outlined.VisibilityOff,
          heading = CopyR.string.settings_about_this_app_safety_phone_yourself_heading,
          subHeading = CopyR.string.settings_about_this_app_safety_phone_yourself_subheading,
        ),
        SafetyItem(
          icon = Icons.Outlined.SystemUpdate,
          heading = CopyR.string.settings_about_this_app_safety_install_updates_heading,
          subHeading = CopyR.string.settings_about_this_app_safety_install_updates_subheading,
        ),
        SafetyItem(
          icon = Icons.Outlined.Lock,
          heading = CopyR.string.settings_about_this_app_safety_safe_apps_heading,
          subHeading = CopyR.string.settings_about_this_app_safety_safe_apps_subheading,
        ),
        SafetyItem(
          icon = Icons.Outlined.WifiPassword,
          heading = CopyR.string.settings_about_this_app_safety_public_wifi_heading,
          subHeading = CopyR.string.settings_about_this_app_safety_public_wifi_subheading,
        ),
        SafetyItem(
          icon = Icons.Outlined.VerifiedUser,
          heading = CopyR.string.settings_about_this_app_safety_permissions_heading,
          subHeading = CopyR.string.settings_about_this_app_safety_permissions_subheading,
        ),
      )
    }

  Scaffold(
    topBar = {
      MgoTopAppBar(
        title = stringResource(CopyR.string.settings_about_this_app_safety),
        onNavigateBack = onNavigateBack,
      )
    },
    content = { contentPadding ->
      LazyColumn(modifier = Modifier.padding(contentPadding), contentPadding = PaddingValues(16.dp)) {
        item {
          Text(
            modifier =
              Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, bottom = 16.dp),
            text = stringResource(id = CopyR.string.settings_about_this_app_safety_subheading),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.LabelsPrimary(),
          )
        }

        items(safetyItems.size) { position ->
          val safetyItem = safetyItems[position]
          SettingsAboutSafetyListItemCard(
            modifier = Modifier.padding(bottom = 8.dp),
            item = safetyItem,
          )
        }
      }
    },
  )
}

@Composable
private fun SettingsAboutSafetyListItemCard(
  item: SafetyItem,
  modifier: Modifier = Modifier,
) {
  MgoCard(modifier = modifier) {
    Row(modifier = Modifier.padding(16.dp)) {
      Icon(imageVector = item.icon, contentDescription = null, tint = MaterialTheme.colorScheme.SymbolsPrimary())
      Column(modifier = Modifier.padding(start = 8.dp)) {
        Text(
          text = stringResource(item.heading),
          style = MaterialTheme.typography.bodyMedium,
        )
        Text(
          modifier = Modifier.padding(top = 4.dp),
          text = stringResource(item.subHeading),
          style = MaterialTheme.typography.bodySmall,
          color = MaterialTheme.colorScheme.LabelsSecondary(),
        )
      }
    }
  }
}

@DefaultPreviews
@Composable
internal fun SettingsAboutSafetyScreenPreview() {
  MgoTheme {
    SettingsAboutSafetyScreen(
      onNavigateBack = {},
    )
  }
}
