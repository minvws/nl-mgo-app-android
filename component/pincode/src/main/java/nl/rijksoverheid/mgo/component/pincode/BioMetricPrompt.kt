package nl.rijksoverheid.mgo.component.pincode

import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

/**
 * Shows a system dialog that handles biometric login.
 * @param onSuccess Called when the biometric login is successful.
 * @param onFailed Called when the biometric login failed.
 */
fun FragmentActivity.showBiometricPrompt(
  onSuccess: () -> Unit,
  onFailed: () -> Unit = {},
) {
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
          onFailed()
          super.onAuthenticationError(errorCode, errString)
        }

        override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
          onSuccess()
          super.onAuthenticationSucceeded(result)
        }

        override fun onAuthenticationFailed() {
          onFailed()
          super.onAuthenticationFailed()
        }
      },
    )

  val info =
    BiometricPrompt.PromptInfo.Builder()
      .setTitle(getString(CopyR.string.biometric_prompt_heading))
      .setSubtitle(getString(CopyR.string.biometric_prompt_subheading))
      .setNegativeButtonText(getString(CopyR.string.common_cancel))
      .build()

  prompt.authenticate(info)
}
