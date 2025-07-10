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
 * This method allows dynamic access to string resources using their names.
 * Note: The use of `getIdentifier` is discouraged due to potential performance issues.
 *
 * @param aString The name of the string resource.
 * @return The resource ID of the string, or 0 if not found.
 */
@SuppressLint("DiscouragedApi") // Suppresses warning about getIdentifier usage
@StringRes
fun Context.getStringResourceByName(aString: String): Int =
  resources.getIdentifier(aString, "string", packageName)

/**
 * Opens a file using a compatible external application.
 *
 * This method generates a content URI using FileProvider and launches an intent
 * to view the file with an appropriate app installed on the device.
 *
 * @param file The file to open.
 * @param contentType The MIME type of the file (e.g., "application/pdf", "image/png").
 */
fun Context.openFileWithOtherApp(
  file: File,
  contentType: String,
) {
  // Generate a content URI for the file
  val attachmentUri: Uri =
    FileProvider.getUriForFile(
      this,
      "${this.packageName}.fileprovider", // Must match the provider authority in AndroidManifest.xml
      file,
    )

  // Create an intent to view the file
  val viewIntent =
    Intent(Intent.ACTION_VIEW).apply {
      setDataAndType(attachmentUri, contentType)
      addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION) // Grant temporary read access to the file
    }

  // Launch chooser to allow the user to select an appropriate app
  this.startActivity(Intent.createChooser(viewIntent, null))
}

/**
 * Shares a file with another application.
 *
 * This method creates a content URI using FileProvider and shares the file
 * using an ACTION_SEND intent.
 *
 * @param file The file to share.
 * @param contentType The MIME type of the file (e.g., "application/pdf", "image/png").
 */
fun Context.sendFileToOtherApp(
  file: File,
  contentType: String,
) {
  // Generate a content URI for the file
  val attachmentUri: Uri =
    FileProvider.getUriForFile(
      this,
      "${this.packageName}.fileprovider", // Must match the provider authority in AndroidManifest.xml
      file,
    )

  // Create an intent to send the file
  val sendIntent =
    Intent(Intent.ACTION_SEND).apply {
      type = contentType
      putExtra(Intent.EXTRA_STREAM, attachmentUri)
      addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION) // Grant temporary read access to the receiving app
    }

  // Launch chooser to allow the user to select an app to share with
  this.startActivity(Intent.createChooser(sendIntent, null))
}
