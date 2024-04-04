package com.google.android.gms.example.jetpackcomposedemo

import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.LoadAdError

/**
 * Represents the state of a banner advertisement, including its configuration.
 *
 * @param adUnitId The ID of the ad unit to load the banner into.
 * @param adRequest The AdRequest object used to configure ad targeting and loading behavior.
 * @param adSize The desired size of the banner ad (default is AdSize.BANNER).
 * @param onAdClicked Function invoked when the ad is clicked.
 * @param onAdImpression Function invoked when an ad impression is recorded.
 * @param onAdFailedToLoad Function invoked when the ad fails to load, includes the LoadAdError.
 * @param onAdLoaded Function invoked when the ad is successfully loaded.
 * @param onAdOpened Function invoked when the ad is opened (e.g., expands to a fullscreen).
 * @param onAdClosed Function invoked when the ad is closed.
 * @param onAdSwipeGestureClicked Function invoked when user performs a swipe gesture on the ad.
 */
data class BannerState(
  val adUnitId: String,
  val adRequest: AdRequest,
  val adSize: AdSize = AdSize.BANNER,
  val onAdClicked: (() -> Unit)? = null,
  val onAdImpression: (() -> Unit)? = null,
  val onAdFailedToLoad: ((LoadAdError) -> Unit)? = null,
  val onAdLoaded: (() -> Unit)? = null,
  val onAdOpened: (() -> Unit)? = null,
  val onAdClosed: (() -> Unit)? = null,
  val onAdSwipeGestureClicked: (() -> Unit)? = null,
)
