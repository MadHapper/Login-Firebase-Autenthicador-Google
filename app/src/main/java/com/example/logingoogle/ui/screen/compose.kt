package com.example.logingoogle.ui.screen

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.example.logingoogle.MainActivity
import com.example.logingoogle.R
import com.example.logingoogle.domain.LoginViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import com.stevdzasan.onetap.OneTapSignInWithGoogle
import com.stevdzasan.onetap.rememberOneTapSignInState


@Composable
fun Login(viewModel: LoginViewModel) {
    val user = viewModel.user.value
    val password = viewModel.password.value
    val showError = viewModel.showError.value
    val showLogin = viewModel.showLogin.value
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(30.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        ImangeUser(borderWidth = 10.dp)
        Spacer()
        EditTextUser(user = user, onTextChanged = viewModel::onUserTextChanged)
        Spacer()
        EditTextPassword(password = password, onTextChanged = viewModel::onPasswordTextChanged)
        Spacer()
        if (showError) {
            Text(
                text = "Usuario o contraseña incorrecta",
                color = Color.Red,
                style = TextStyle(fontSize = 20.sp)
            )
        }
        if (showLogin) {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
        Spacer()
        ButtonLogin(onLoginClick = viewModel::onLoginClick)
        Spacer()
        ButtonGoogle()
        //ButtonGoogle1()

    }
}

@Composable
fun EditTextUser(user: String, onTextChanged: (String) -> Unit) {
    TextField(
        value = user,
        onValueChange = onTextChanged,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White),
        placeholder = { Text(text = "Usuario") },
        maxLines = 1,
        singleLine = true,
        textStyle = TextStyle.Default.copy(fontSize = 20.sp)
    )
}

@Composable
fun EditTextPassword(password: String, onTextChanged: (String) -> Unit) {
    TextField(
        value = password,
        onValueChange = onTextChanged,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White),
        placeholder = { Text(text = "Contraseña") },
        maxLines = 1,
        singleLine = true,
        textStyle = TextStyle.Default.copy(fontSize = 20.sp),
        visualTransformation = PasswordVisualTransformation()
    )
}

@Composable
fun ButtonLogin(onLoginClick: () -> Unit) {
    Button(onClick = onLoginClick) {
        Text(text = "Iniciar Sesión", style = TextStyle(fontSize = 20.sp))
    }
}

@Composable
fun Spacer() {
    Spacer(modifier = Modifier.height(15.dp))
}

@Composable
fun ImangeUser(borderWidth: Dp) {
    Card(
        modifier = Modifier.size(250.dp),
        shape = CircleShape,
    ) {
        Image(
            painter = rememberImagePainter(
                data = "https://rickandmortyapi.com/api/character/avatar/19.jpeg"
            ),
            contentDescription = "user"
        )
    }
}

@Composable
fun ButtonGoogle() {
    val state = rememberOneTapSignInState()

    OneTapSignInWithGoogle(
        state = state,
        clientId = "312789708971-rs7cutk9fm3htrl6imn67g8cspo9m0ru.apps.googleusercontent.com",
        onTokenIdReceived = { tokenId ->
            Log.d("LOG", tokenId)
        },
        onDialogDismissed = { message ->
            Log.d("LOG", message)
        }
    )
    Card(
        modifier = Modifier.size(50.dp),
        shape = CircleShape,
    ) {
        Image(
            painter = painterResource(id = R.drawable.google1),
            contentDescription = "google",
            modifier = Modifier.clickable(onClick = { state.open() })
        )
    }
}







