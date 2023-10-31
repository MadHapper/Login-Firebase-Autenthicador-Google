package com.example.logingoogle.domain

import android.app.Activity
import android.content.Intent
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task


class LoginViewModel : ViewModel() {
    private val isLoading = MutableLiveData(false)

    private val _user = mutableStateOf("")
    val user: State<String> get() = _user

    private val _password = mutableStateOf("")
    val password: State<String> get() = _password

    private val _showError = mutableStateOf(false)
    val showError: State<Boolean> get() = _showError

    private val _showLogin = mutableStateOf(false)
    val showLogin: State<Boolean> get() = _showLogin


    fun onUserTextChanged(newUser: String) {
        _user.value = newUser
    }

    fun onPasswordTextChanged(newPassword: String) {
        _password.value = newPassword
    }

    fun onLoginClick() {
        val isValidCredentials = user.value == "asd" && password.value == "asd"
        _showError.value = !isValidCredentials
        _showLogin.value = isValidCredentials
    }

}