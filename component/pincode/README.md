# Component - Pincode

This module contains UI components that build up a screen to input a pin code. When including this module, two functions will be exposed:

- **PinCodeWithKeyboard**: Use to input a pin code. Does not use the system keyboard, but has a custom in screen one.
- **FragmentActivity.showBiometricPrompt**: Helper method that shows a system dialog that handles biometric login.