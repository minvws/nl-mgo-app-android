package nl.rijksoverheid.mgo.framework.util

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent

/**
 * Launches the url in Chrome Custom Tabs (https://developer.chrome.com/docs/android/custom-tabs).
 * If it fails, will fallback to browser.
 */
fun Context.launchBrowser(url: String) {
    try {
        CustomTabsIntent.Builder().build().also { intent ->
            intent.launchUrl(this, Uri.parse(url))
        }
    } catch (exception: ActivityNotFoundException) {
        // if chrome app is disabled or not there, try an alternative
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }
}
