package com.google.android.gms.example.jetpackcomposedemo

import android.app.Activity
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.google.android.gms.ads.MobileAds
import com.google.android.ump.*
import java.util.concurrent.atomic.AtomicBoolean

/**
 * This class manages the process of obtaining user consent for Google Mobile Ads, including:
 * - Requesting consent information
 * - Displaying the consent form if necessary
 * - Initializing the Google Mobile Ads SDK based on the user's consent
 */
class ConsentManager(private val activity: Activity) {

  companion object {
    const val TAG = "GoogleMobileAdsSample"
  }

  private var isMobileAdsInitializeCalled = AtomicBoolean(false)

  /** Represents current initialization states for the Google Mobile Ads SDK. */
  var consentState = mutableStateOf(ConsentState.UNINITIALIZED)

  /** Lambda invoked when states for the Google Mobile Ads SDK changes. */
  var onConsentStateChanged: ((ConsentState) -> Unit)? = null

  /** Represents potentially initialization states for the Google Mobile Ads SDK. */
  enum class ConsentState {
    UNINITIALIZED, // Initial start state
    CONSENT_REQUIRED, // User consent needs to be obtained
    CONSENT_OBTAINED, // User has granted consent
    ERROR, // An error occurred during the consent process
    INITIALIZED // Google Mobile Ads SDK initialized successfully
  }

  /**
   * Initiates the consent process and potentially initializes the Google Mobile Ads SDK.
   *
   * @param consentRequestParameters Parameters for the consent request form.
   */
  fun initialize(consentRequestParameters: ConsentRequestParameters) {
    if (isMobileAdsInitializeCalled.getAndSet(true)) return

    val consentInformation = UserMessagingPlatform.getConsentInformation(activity)

    consentInformation.requestConsentInfoUpdate(
      activity,
      consentRequestParameters,
      {
        // Success callback.
        showConsentFormIfRequired { error ->
          if (error != null) {
            Log.w(TAG, "Consent form error: ${error.errorCode} - ${error.message}")
            consentState.value = ConsentState.ERROR
            onConsentStateChanged?.invoke(consentState.value)
          } else {
            consentState.value = ConsentState.CONSENT_OBTAINED
            onConsentStateChanged?.invoke(consentState.value)
            if (consentInformation.canRequestAds()) {
              initializeMobileAdsSdk()
            }
          }
        }
      },
      {
        // Failure callback.
        Log.w(TAG, "Consent info update error: ${it.errorCode} - ${it.message}")
        consentState.value = ConsentState.ERROR
        onConsentStateChanged?.invoke(consentState.value)
      },
    )
  }

  private fun showConsentFormIfRequired(onFormResult: (FormError?) -> Unit) {
    UserMessagingPlatform.loadAndShowConsentFormIfRequired(activity, onFormResult)
  }

  private fun initializeMobileAdsSdk() {
    MobileAds.initialize(activity) {
      Log.d(TAG, "Mobile Ads SDK initialized")
      consentState.value = ConsentState.INITIALIZED
      onConsentStateChanged?.invoke(consentState.value)
    }
  }
}
