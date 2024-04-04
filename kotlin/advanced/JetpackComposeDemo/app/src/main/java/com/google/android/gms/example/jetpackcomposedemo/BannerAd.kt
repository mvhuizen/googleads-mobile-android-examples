package com.google.android.gms.example.jetpackcomposedemo

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError

/**
 * A composable function to display a banner ad.
 *
 * @param bannerState The BannerState object containing ad configuration.
 * @param modifier The modifier to apply to the banner ad.
 */
@Composable
fun BannerAd(bannerState: BannerState, modifier: Modifier) {
  // Remember the adView so we can dispose of it later.
  var adView by remember { mutableStateOf<AdView?>(null) }
  // Do not load a banner when in preview mode.
  val isPreview = LocalInspectionMode.current

  AndroidView(
    modifier = modifier.fillMaxWidth(),
    factory = { context ->
      AdView(context).apply {
        // Make sure we only run this code block once and in non-preview mode.
        if (adView != null || isPreview) {
          return@apply
        }

        adView = this
        this.adUnitId = bannerState.adUnitId
        this.setAdSize(bannerState.adSize)
        this.adListener =
          object : AdListener() {
            override fun onAdLoaded() {
              bannerState.onAdLoaded?.invoke()
            }

            override fun onAdFailedToLoad(error: LoadAdError) {
              bannerState.onAdFailedToLoad?.invoke(error)
            }

            override fun onAdImpression() {
              bannerState.onAdImpression?.invoke()
            }

            override fun onAdClosed() {
              bannerState.onAdClosed?.invoke()
            }

            override fun onAdClicked() {
              bannerState.onAdClicked?.invoke()
            }

            override fun onAdOpened() {
              bannerState.onAdClicked?.invoke()
            }

            override fun onAdSwipeGestureClicked() {
              bannerState.onAdSwipeGestureClicked?.invoke()
            }
          }
        this.loadAd(bannerState.adRequest)
      }
    },
  )
  // Clean up the AdView after use.
  DisposableEffect(Unit) { onDispose { adView?.destroy() } }
}
