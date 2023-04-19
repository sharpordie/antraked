package com.example.antraked

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.view.View
import android.webkit.WebView
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PairingScreenViewModel(application: Application) : AndroidViewModel(application) {
    data class UiState(
        val failure: Boolean = false,
        val loading: Boolean = true,
        val success: Boolean = false,
    )

    private val _context by lazy { getApplication<Application>().applicationContext }
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()
    private lateinit var _process: Job

    var secret1 by mutableStateOf("")
    var secret2 by mutableStateOf("")
    var pairing by mutableStateOf("")

    init {
        _uiState.update { it.copy(loading = !it.loading) }
    }

    suspend fun onPairClicked() = viewModelScope.launch {
        if (_uiState.value.loading) {
            _uiState.update { it.copy(loading = !it.loading) }
            _process.cancel()
        } else {
            _process = launch {
                _uiState.update { it.copy(loading = !it.loading) }
                val success = setPairForTrakt(_context, listOf(secret1, secret2), pairing)
                _uiState.update { it.copy(success = success) }
                _uiState.update { it.copy(failure = !success) }
                _uiState.update { it.copy(loading = !it.loading) }
            }
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private suspend fun setPairForTrakt(context: Context, private: List<String>, pairing: String): Boolean {
        if (pairing.length != 8) return false
        val webView = WebView(context)
        webView.visibility = View.GONE
        webView.settings.javaScriptEnabled = true
        webView.clearCache(true)
        webView.loadUrl("https://trakt.tv/auth/signin")
        delay(6_000L)
        var payload = "document.querySelector('#user_login').value = '${private[0]}';"
        webView.evaluateJavascript(payload, null)
        delay(3_000L)
        payload = "document.querySelector('#user_password').value = '${private[1]}';"
        webView.evaluateJavascript(payload, null)
        delay(3_000L)
        payload = "document.querySelector('#new_user > div.form-actions > input').click();"
        webView.evaluateJavascript(payload, null)
        delay(12_000L)
        if (webView.url != "https://trakt.tv/dashboard") return false
        webView.loadUrl("https://trakt.tv/activate")
        delay(3_000L)
        payload = "document.querySelector('#code').value = '$pairing';"
        webView.evaluateJavascript(payload, null)
        delay(6_000L)
        payload = "document.querySelector('#auth-form-wrapper > form > div.form-actions > input').click();"
        webView.evaluateJavascript(payload, null)
        delay(12_000L)
        if (webView.url != "https://trakt.tv/activate/authorize") return false
        payload = """
          const element = '#auth-form-wrapper > div.form-signin.less-top > div > form:nth-child(1) > input.btn.btn-success.btn-lg.btn-block';
          document.querySelector(element).click();
        """
        webView.evaluateJavascript(payload, null)
        delay(6_000L)
        webView.destroy()
        delay(2_000L)
        return true
    }
}