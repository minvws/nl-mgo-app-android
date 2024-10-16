package nl.rijksoverheid.mgo.component.pincode

import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

suspend fun FragmentActivity.showBiometricPrompt(): Boolean =
    suspendCoroutine { suspendCoroutine ->
        val executor = ContextCompat.getMainExecutor(this)
        val prompt =
            BiometricPrompt(
                this,
                executor,
                object : BiometricPrompt.AuthenticationCallback() {
                    override fun onAuthenticationError(
                        errorCode: Int,
                        errString: CharSequence,
                    ) {
                        suspendCoroutine.resume(false)
                        super.onAuthenticationError(errorCode, errString)
                    }

                    override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                        suspendCoroutine.resume(true)
                        super.onAuthenticationSucceeded(result)
                    }

                    override fun onAuthenticationFailed() {
                        suspendCoroutine.resume(false)
                        super.onAuthenticationFailed()
                    }
                },
            )

        val info =
            BiometricPrompt.PromptInfo.Builder()
                .setTitle("Title")
                .setSubtitle("Subtitle")
                .setNegativeButtonText(getString(CopyR.string.common_cancel))
                .build()

        prompt.authenticate(info)
    }
