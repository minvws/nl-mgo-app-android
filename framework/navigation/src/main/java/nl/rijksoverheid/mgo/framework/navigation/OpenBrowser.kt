package nl.rijksoverheid.mgo.framework.navigation

import android.content.Context
import android.content.Intent
import android.net.Uri

/**
 * Launches the url in Chrome Custom Tabs (https://developer.chrome.com/docs/android/custom-tabs).
 * If it fails, will fallback to browser.
 */
fun String.launchBrowser(context: Context) {
    context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(this)))
}
