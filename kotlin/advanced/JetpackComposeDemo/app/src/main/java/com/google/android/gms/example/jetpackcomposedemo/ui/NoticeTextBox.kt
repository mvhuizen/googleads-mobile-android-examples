package com.google.android.gms.example.jetpackcomposedemo.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

/**
 * A composable function to display a notice box with a customizable background color.
 *
 * @param bgColor The background color for the box.
 * @param message The text message to be displayed within the box.
 */
@Composable
fun NoticeTextBox(bgColor: Color, message: String) {
  Box(modifier = Modifier.fillMaxSize().background(bgColor), contentAlignment = Alignment.Center) {
    Text(text = message, style = MaterialTheme.typography.bodyLarge, color = Color.White)
  }
}
