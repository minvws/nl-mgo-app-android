package nl.rijksoverheid.mgo.framework.util.file

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ReadLocalFileFromAssets
  @Inject
  constructor(
    @ApplicationContext private val context: Context,
  ) : ReadLocalFile {
    override fun invoke(fileName: String): String =
      context.assets
        .open(fileName)
        .bufferedReader()
        .use { it.readText() }
  }
