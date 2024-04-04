package com.google.android.gms.example.jetpackcomposedemo

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.google.android.gms.example.jetpackcomposedemo.ui.NoticeTextBox
import com.google.android.gms.example.jetpackcomposedemo.ui.TextButton
import com.google.android.gms.example.jetpackcomposedemo.ui.theme.ColorStateError
import com.google.android.gms.example.jetpackcomposedemo.ui.theme.ColorStateLoaded
import com.google.android.gms.example.jetpackcomposedemo.ui.theme.ColorStateUnloaded
import com.google.android.gms.example.jetpackcomposedemo.ui.theme.JetpackComposeDemoTheme
import com.google.android.ump.ConsentRequestParameters

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    val consentManager = ConsentManager(this)
    val consentRequestParameters = ConsentRequestParameters.Builder().build()

    setContent {
      JetpackComposeDemoTheme {
        // A surface container using the 'background' color from the theme
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
          MainActivityComposable(consentManager = consentManager)
        }
      }
    }
    consentManager.initialize(consentRequestParameters)
  }

  @Composable
  fun MainActivityComposable(consentManager: ConsentManager) {
    val context = LocalContext.current
    var messageText by remember { mutableStateOf("Google Mobile Ads is Uninitialized.") }
    var messageColor by remember { mutableStateOf(ColorStateUnloaded) }
    var isInitialized by remember { mutableStateOf(false) }

    // Observe changes in consentState.
    consentManager.onConsentStateChanged = { newState ->
      when (newState) {
        ConsentManager.ConsentState.UNINITIALIZED -> {
          messageText = "Google Mobile ads Uninitialized"
          messageColor = ColorStateUnloaded
        }
        ConsentManager.ConsentState.CONSENT_REQUIRED -> {
          messageText = "Google Mobile ads required consent"
          messageColor = ColorStateUnloaded
        }
        ConsentManager.ConsentState.CONSENT_OBTAINED -> {
          messageText = "Google Mobile ads obtained consent"
          messageColor = ColorStateUnloaded
        }
        ConsentManager.ConsentState.ERROR -> {
          messageText = "Google Mobile ads consent error"
          messageColor = ColorStateError
        }
        ConsentManager.ConsentState.INITIALIZED -> {
          messageText = "Google Mobile ads initialized"
          messageColor = ColorStateLoaded
          isInitialized = true
        }
      }
    }

    // Render the main activity.
    Column(modifier = Modifier.fillMaxSize()) {
      Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        // Render title and Notice block.
        Text(text = "Google Mobile Ads Sample", style = MaterialTheme.typography.bodyLarge)
        NoticeTextBox(messageColor, messageText)

        // Render navigation buttons to admob sample activities.
        TextButton("Fixed Size Banner", enabled = isInitialized) {
          val intent = Intent(context, BannerActivity::class.java)
          context.startActivity(intent)
        }
        TextButton("Inline Adaptive Banner", enabled = isInitialized) {
          val intent = Intent(context, InlineAdaptiveBannerActivity::class.java)
          context.startActivity(intent)
        }
        TextButton("Collapsible Banner", enabled = isInitialized) {
          val intent = Intent(context, CollapsibleBannerActivity::class.java)
          context.startActivity(intent)
        }
      }
    }
  }
}
