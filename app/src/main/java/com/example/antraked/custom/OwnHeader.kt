package com.example.antraked.custom

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun OwnHeader(
    heading: String,
    content: String,
    picture: ImageVector,
    failure: Boolean = false,
    loading: Boolean = false,
    success: Boolean = false,
) {
    Divider(color = MaterialTheme.colorScheme.primary, thickness = 2.dp)
    Row(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.onPrimary)
            .padding(
                start = 16.dp,
                top = 16.dp,
                end = 8.dp,
                bottom = 16.dp
            )
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .height(88.dp)
                .weight(1f)
        ) {
            Text(
                text = heading,
                style = TextStyle(
                    fontSize = 40.sp,
                    fontWeight = FontWeight.W600,
                ),
            )
            Text(
                text = content,
                style = TextStyle(
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 20.sp,
                )
            )
            Box(modifier = Modifier.height(8.dp))
        }
        Box(
            modifier = Modifier
                .size(88.dp)
        ) {
            when {
                loading -> CircularProgressIndicator(
                    strokeWidth = 8.dp,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(all = 8.dp))
                failure -> Icon(
                    imageVector = Icons.Outlined.Info,
                    contentDescription = null,
                    tint = Color.Red,
                    modifier = Modifier.fillMaxSize())
                success -> Icon(
                    imageVector = Icons.Outlined.CheckCircle,
                    contentDescription = null,
                    tint = Color.Green,
                    modifier = Modifier.fillMaxSize())
                else -> Icon(
                    imageVector = picture,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize())
            }
        }
    }
    Divider(color = MaterialTheme.colorScheme.primary, thickness = 2.dp)
}