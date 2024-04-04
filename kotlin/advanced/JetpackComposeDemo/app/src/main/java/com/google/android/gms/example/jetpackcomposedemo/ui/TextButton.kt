package com.google.android.gms.example.jetpackcomposedemo.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * A composable function to create a standard button with text.
 *
 * @param name The text to be displayed on the button.
 * @param enabled Controls whether the button is enabled or disabled (defaults to true).
 * @param onClick The lambda function to be executed when the button is clicked.
 */
@Composable
fun TextButton(name: String, enabled: Boolean = true, onClick: () -> Unit) {
  Button(onClick = { onClick() }, enabled = enabled, modifier = Modifier.fillMaxWidth()) {
    Text(name)
  }
}
