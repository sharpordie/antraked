package com.example.antraked

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.antraked.custom.OwnHeader
import com.example.antraked.custom.OwnRubric
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PairingScreen(vmModel: PairingScreenViewModel) {
    val uiState by vmModel.uiState.collectAsState()
    val scoping = rememberCoroutineScope()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "ANTRAKED",
                        style = MaterialTheme.typography.titleMedium
                    )
                },
                actions = {
                    TextButton(
                        onClick = {
                            scoping.launch {
                                vmModel.onPairClicked()
                            }
                        }
                    ) {
                        Text(
                            text = if (uiState.loading) "STOP" else "PAIR",
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                OwnHeader(
                    heading = "PAIRING",
                    content = when {
                        uiState.loading -> "Has started"
                        uiState.failure -> "Has failed"
                        uiState.success -> "Has succeed"
                        else -> "Enter pairing information"
                    },
                    picture = Icons.Outlined.Settings,
                    failure = uiState.failure,
                    loading = uiState.loading,
                    success = uiState.success,
                )
                if (!uiState.loading) {
                    LazyColumn(
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 24.dp),
                        verticalArrangement = Arrangement.spacedBy(24.dp),
                    ) {
                        item {
                            OwnRubric(heading = "USERNAME")
                            OutlinedTextField(
                                value = vmModel.secret1,
                                onValueChange = { content -> vmModel.secret1 = content },
                                placeholder = { Text(text = "Empty") },
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                        item {
                            OwnRubric(heading = "PASSWORD")
                            OutlinedTextField(
                                value = vmModel.secret2,
                                onValueChange = { content -> vmModel.secret2 = content },
                                placeholder = { Text(text = "Empty") },
                                visualTransformation = PasswordVisualTransformation(),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                        item {
                            OwnRubric(heading = "PAIR")
                            OutlinedTextField(
                                value = vmModel.pairing,
                                onValueChange = { content -> vmModel.pairing = content },
                                placeholder = { Text(text = "Empty") },
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            }
        }
    )
}