package nl.rijksoverheid.mgo.framework.util

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent

/**
 * Opens a URL in a web browser using Chrome Custom Tabs if available,
 * falling back to the default browser if necessary.
 *
 * This function attempts to launch the given URL using Chrome Custom Tabs for
 * an optimized browsing experience. If Chrome is not available or disabled,
 * it falls back to a standard web browser intent.
 *
 * @param url The web address to open.
 */
fun Context.launchBrowser(url: String) {
  try {
    // Attempt to open the URL using Chrome Custom Tabs
    CustomTabsIntent.Builder().build().also { intent ->
      intent.launchUrl(this, Uri.parse(url))
    }
  } catch (exception: ActivityNotFoundException) {
    try {
      // If Chrome is unavailable, fall back to a regular web browser
      startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    } catch (e: Exception) {
      // Do nothing if this fails
    }
  }
}
