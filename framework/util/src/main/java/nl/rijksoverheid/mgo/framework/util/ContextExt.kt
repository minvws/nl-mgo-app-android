package nl.rijksoverheid.mgo.framework.util

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.annotation.StringRes
import androidx.core.content.FileProvider
import java.io.File

/**
 * Retrieves the resource ID of a string resource by its name.
 *
 * This function allows you to get a string resource dynamically using its name.
 * Note: The use of `getIdentifier` is discouraged as it can impact performance.
 *
 * @param aString The name of the string resource.
 * @return The resource ID of the string, or 0 if not found.
 */
@SuppressLint("DiscouragedApi") // Suppresses the warning for using getIdentifier
@StringRes
fun Context.getStringResourceByName(aString: String): Int {
  return resources.getIdentifier(aString, "string", packageName)
}

/**
 * Shares a file using an appropriate application.
 *
 * This function creates a content URI using FileProvider and launches an intent
 * to share or open the file using an appropriate app that supports the given content type.
 *
 * @param file The file to be shared.
 * @param contentType The MIME type of the file (e.g., "application/pdf", "image/png").
 */
fun Context.shareFile(
  file: File,
  contentType: String,
) {
  // Generate a content URI for the file using FileProvider
  val attachmentUri: Uri =
    FileProvider.getUriForFile(
      this,
      "${this.packageName}.fileprovider", // Matches the provider authority in AndroidManifest.xml
      file,
    )

  // Create an intent to view or share the file
  val shareIntent =
    Intent(Intent.ACTION_VIEW).apply {
      setDataAndType(attachmentUri, contentType) // Set the file URI and MIME type
      addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION) // Grant temporary read permission to the receiving app
    }

  // Start an activity to open the file using a compatible app
  this.startActivity(
    Intent.createChooser(shareIntent, "Open File"), // Show a chooser dialog to select an app
  )
}
