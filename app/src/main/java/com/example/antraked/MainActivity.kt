package com.example.antraked

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.antraked.ui.theme.AnTrakedTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AnTrakedTheme {
                val vmModel: PairingScreenViewModel by viewModels()
                PairingScreen(vmModel)
            }
        }
    }
}