package com.google.android.gms.example.jetpackcomposedemo

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.example.jetpackcomposedemo.ui.NoticeTextBox
import com.google.android.gms.example.jetpackcomposedemo.ui.ScrollingScreen
import com.google.android.gms.example.jetpackcomposedemo.ui.theme.ColorStateError
import com.google.android.gms.example.jetpackcomposedemo.ui.theme.ColorStateLoaded
import com.google.android.gms.example.jetpackcomposedemo.ui.theme.ColorStateUnloaded
import com.google.android.gms.example.jetpackcomposedemo.ui.theme.JetpackComposeDemoTheme

class InlineAdaptiveBannerActivity : ComponentActivity() {

  companion object {
    // Test AdUnityID for demonstrative purposes.
    // https://developers.google.com/admob/android/test-ads
    const val ADUNIT_ID = "ca-app-pub-3940256099942544/9214589741"
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      JetpackComposeDemoTheme {
        // A surface container using the 'background' color from the theme
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
          InlineAdaptiveBannerScreen(ADUNIT_ID)
        }
      }
    }
  }
}

@Preview
@Composable
fun InlineAdaptiveBannerScreenPreview() {
  JetpackComposeDemoTheme {
    // A surface container using the 'background' color from the theme
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
      InlineAdaptiveBannerScreen(InlineAdaptiveBannerActivity.ADUNIT_ID)
    }
  }
}

@Composable
fun InlineAdaptiveBannerScreen(adUnitID: String) {
  // Cache the mutable state for our notification bar.
  val context = LocalContext.current
  var messageText by remember { mutableStateOf("Banner is not loaded.") }
  var messageColor by remember { mutableStateOf(ColorStateUnloaded) }

  val bannerState =
    BannerState(
      adUnitId = adUnitID,
      adSize = AdSize.getInlineAdaptiveBannerAdSize(300, 120),
      adRequest = AdRequest.Builder().build(),
      onAdLoaded = {
        messageColor = ColorStateLoaded
        messageText = "Banner is loaded."
      },
      onAdFailedToLoad = { error: LoadAdError ->
        messageColor = ColorStateError
        messageText = "Banner is failed to load with error: " + error.message
      },
    )

  ScrollingScreen(
    title = "Inline Adaptive Banner",
    backClick = {
      val intent = Intent(context, MainActivity::class.java)
      context.startActivity(intent)
    },
    content = {
      NoticeTextBox(messageColor, messageText)
      Text(text = "Example of a inline adaptive banner ad view.")
      BannerAd(bannerState, modifier = Modifier)
    },
  )
}
