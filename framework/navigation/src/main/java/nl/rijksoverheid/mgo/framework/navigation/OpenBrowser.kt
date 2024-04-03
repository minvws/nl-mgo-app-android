package nl.rijksoverheid.mgo.framework.navigation

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent

/**
 * Launches the url in Chrome Custom Tabs (https://developer.chrome.com/docs/android/custom-tabs).
 * If it fails, will fallback to browser.
 */
fun String.launchBrowser(context: Context) {
    try {
        CustomTabsIntent.Builder().build().also { intent ->
            intent.launchUrl(context, Uri.parse(this))
        }
    } catch (exception: ActivityNotFoundException) {
        // if chrome app is disabled or not there, try an alternative
        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(this)))
    }
}
