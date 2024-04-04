package com.google.android.gms.example.jetpackcomposedemo.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * A composable function to provide a basic activity layout structure, including a top app bar and a
 * scrollable column for content.
 *
 * @param title The title to be displayed in the top app bar.
 * @param backClick The lambda function to be executed when the back button in the top app bar is
 *   clicked.
 * @param content The composable content to be displayed within the scrollable column.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScrollingScreen(
  title: String,
  backClick: () -> Unit,
  content: @Composable() ColumnScope.() -> Unit,
) {
  Column(modifier = Modifier.fillMaxSize()) {
    TopAppBar(
      title = { Text(title) },
      navigationIcon = { IconButton(onClick = backClick) { Icon(Icons.Filled.ArrowBack, "Back") } },
    )
    Column(modifier = Modifier.verticalScroll(rememberScrollState()), content = content)
  }
}
