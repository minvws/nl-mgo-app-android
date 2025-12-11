import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File

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
