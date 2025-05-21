package nl.rijksoverheid.mgo.framework.util

import android.content.Context
import android.os.Build
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityManager

/**
 * Will announce a string through accessibility, if accessibility is enabled.
 * @param announce The string to announce.
 */
fun Context.accessibilityAnnounce(announce: String) {
  val accessibilityManager = getSystemService(Context.ACCESSIBILITY_SERVICE) as AccessibilityManager
  if (accessibilityManager.isEnabled) {
    val event =
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        AccessibilityEvent(AccessibilityEvent.TYPE_ANNOUNCEMENT)
      } else {
        AccessibilityEvent.obtain(AccessibilityEvent.TYPE_ANNOUNCEMENT)
      }
    event.text.add(announce)
    accessibilityManager.sendAccessibilityEvent(event)
  }
}
