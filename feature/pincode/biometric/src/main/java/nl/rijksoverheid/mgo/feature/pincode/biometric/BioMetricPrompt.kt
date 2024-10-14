package nl.rijksoverheid.mgo.feature.pincode.biometric

import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

internal object BioMetricPrompt {
    suspend operator fun invoke(fragmentActivity: FragmentActivity): Boolean =
        suspendCoroutine { suspendCoroutine ->
            val executor = ContextCompat.getMainExecutor(fragmentActivity)
            val prompt =
                BiometricPrompt(
                    fragmentActivity,
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
                    .setNegativeButtonText(fragmentActivity.getString(CopyR.string.common_cancel))
                    .build()

            prompt.authenticate(info)
        }
}
