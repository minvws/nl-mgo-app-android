package nl.rijksoverheid.mgo.data.uiSchema

import android.content.Context
import com.eclipsesource.v8.V8
import com.eclipsesource.v8.V8Array
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.BufferedReader
import java.io.InputStreamReader
import javax.inject.Inject

internal class DefaultUiSchemaMapper @Inject constructor(@ApplicationContext private val context: Context): UiSchemaMapper {

    override fun getUiSchema(fhirBundleJson: String): Result<UISchema> {
        // Javascript code
        val reader1 = BufferedReader(InputStreamReader(context.assets.open("mgo-fhir-data.js")))
        val jsCode = reader1.use { it.readText() }

        // Create javascript runtime
        val runtime = V8.createV8Runtime()
        try {
            // Parse javascript
            runtime.executeVoidScript(jsCode)

            // Get bundle resources
            val getBundleResourceJsonParameters = V8Array(runtime)
            getBundleResourceJsonParameters.push(fhirBundleJson)
            val bundleResources = runtime.executeStringFunction("getBundleResourcesJson", getBundleResourceJsonParameters)
            getBundleResourceJsonParameters.release()

            return Result.failure(IllegalStateException("Success!"))
        } catch (e: Exception) {
            return Result.failure(IllegalStateException("Error: ${e.message}"))
        } finally {
            runtime.release()
        }
    }
}
