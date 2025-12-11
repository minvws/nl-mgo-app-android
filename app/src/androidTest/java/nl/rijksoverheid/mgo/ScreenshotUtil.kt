package nl.rijksoverheid.mgo

import androidx.test.core.app.takeScreenshot
import androidx.test.core.graphics.writeToTestStorage

internal fun takeScreenshot(name: String) {
  val screenshot = takeScreenshot()
  screenshot.writeToTestStorage(name)
}
