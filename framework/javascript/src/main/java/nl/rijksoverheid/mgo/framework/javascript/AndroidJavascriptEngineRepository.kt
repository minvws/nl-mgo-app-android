package nl.rijksoverheid.mgo.framework.javascript

import android.content.Context
import com.whl.quickjs.android.QuickJSLoader
import com.whl.quickjs.wrapper.QuickJSContext
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.ExecutorCoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.nio.charset.StandardCharsets
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class AndroidJavascriptEngineRepository
  @Inject
  constructor(
    @ApplicationContext private val context: Context,
  ) : JavascriptEngineRepository {
    @OptIn(ExperimentalCoroutinesApi::class, DelicateCoroutinesApi::class)
    override val quickJsDispatcher: ExecutorCoroutineDispatcher = newSingleThreadContext("QuickJsThread")

    @Suppress("ktlint:standard:backing-property-naming")
    private var _quickJs: MutableStateFlow<QuickJSContext?> = MutableStateFlow(null)

    override suspend fun create() =
      withContext(quickJsDispatcher) {
        // Init QuickJS for Android
        QuickJSLoader.init()

        // Create Quick JS
        val quickJs = QuickJSContext.create()

        // Make Quick JS statically available
        _quickJs.tryEmit(quickJs)

        Timber.d("Quick JS initialized")
      }

    /**
     * Loads a JavaScript file from the assets folder into the engine,
     * making its functions available for execution.
     *
     * Must be called after [create] has been invoked.
     *
     * @param fileName The name of the JavaScript file to load, relative to the assets root.
     */
    override suspend fun load(fileName: String) =
      withContext(quickJsDispatcher) {
        // Get javascript file as string
        val jsCode =
          context.assets
            .open(fileName)
            .bufferedReader(StandardCharsets.UTF_8)
            .use { it.readText() }

        // Load the javascript file into Quick JS
        val quickJs = get()
        quickJs.evaluate(jsCode)

        Timber.d("Javascript file loaded: $fileName")
      }

    override suspend fun get(): QuickJSContext = withContext(quickJsDispatcher) { _quickJs.filterNotNull().first() }
  }
