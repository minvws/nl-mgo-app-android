import org.jetbrains.kotlin.org.apache.commons.compress.archivers.tar.TarArchiveEntry
import org.jetbrains.kotlin.org.apache.commons.compress.archivers.tar.TarArchiveInputStream
import org.jetbrains.kotlin.org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.zip.ZipInputStream

fun unzip(zipFile: File, targetDir: File) {
    ZipInputStream(zipFile.inputStream()).use { zipInputStream ->
        var entry = zipInputStream.nextEntry
        while (entry != null) {
            val file = File(targetDir, entry.name)
            if (entry.isDirectory) {
                file.mkdirs()
            } else {
                file.parentFile?.mkdirs()
                FileOutputStream(file).use { outputStream ->
                    zipInputStream.copyTo(outputStream)
                }
            }
            zipInputStream.closeEntry()
            entry = zipInputStream.nextEntry
        }
    }
}

@Suppress("DEPRECATION")
fun extractTarGz(tarGzFile: File, targetDir: File) {
    GzipCompressorInputStream(FileInputStream(tarGzFile)).use { gis ->
        TarArchiveInputStream(gis).use { tarInput ->
            var entry: TarArchiveEntry? = tarInput.nextTarEntry
            while (entry != null) {
                val filePath = "$targetDir/${entry.name}"
                if (entry.isDirectory) {
                    File(filePath).mkdirs()
                } else {
                    FileOutputStream(filePath).use { fos -> tarInput.copyTo(fos) }
                }
                entry = tarInput.nextTarEntry
            }
        }
    }
}
