package nl.rijksoverheid.mgo.framework.util

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.annotation.StringRes
import androidx.core.content.FileProvider
import java.io.File

@SuppressLint("DiscouragedApi")
@StringRes
fun Context.getStringResourceByName(aString: String): Int {
    return resources.getIdentifier(aString, "string", packageName)
}

fun Context.shareFile(
    file: File,
    contentType: String,
) {
    val attachmentUri: Uri =
        FileProvider.getUriForFile(
            this,
            "${this.packageName}.fileprovider",
            file,
        )

    val shareIntent =
        Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(attachmentUri, contentType)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }

    this.startActivity(
        Intent.createChooser(shareIntent, "Open File"),
    )
}
