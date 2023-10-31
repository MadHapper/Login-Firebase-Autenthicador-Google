package com.example.logingoogle

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.logingoogle.domain.LoginViewModel
import com.example.logingoogle.ui.screen.Login
import com.example.logingoogle.ui.theme.LoginGoogleTheme
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.stevdzasan.onetap.OneTapSignInWithGoogle
import com.stevdzasan.onetap.rememberOneTapSignInState


class MainActivity : ComponentActivity() {

    companion object {
        const val RC_SIGN_IN = 100
    }

    private lateinit var mAuth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    private val viewModel: LoginViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mAuth = FirebaseAuth.getInstance()
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build();

        googleSignInClient = GoogleSignIn.getClient(this, gso)


        setContent {
            LoginGoogleTheme {
                //Login(viewModel)
                ButtonGoogleAuth { singIn() }
            }
        }
    }

    private fun singIn() {
        val singInIntent = googleSignInClient.signInIntent
        startActivityForResult(singInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val exception = task.exception
            if (task.isSuccessful) {
                try {
                    val account = task.getResult(ApiException::class.java)!!
                    fireBaseAuthWithGoogle(account.idToken!!)

                } catch (e: Exception) {
                    Log.d("Login", "Correcto")
                }
            } else {
                Log.d("Login", "Error")
            }
        }
    }

    private fun fireBaseAuthWithGoogle(idToken: String){
        val credential= GoogleAuthProvider.getCredential(idToken,null)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this){task->
                if (task.isSuccessful){
                    //correcto
                    Toast.makeText(this,"login Correcto",Toast.LENGTH_LONG).show()
                }else{
                    //incorrecto
                    Toast.makeText(this,"login Incorrecto",Toast.LENGTH_LONG).show()
                }
            }
    }

}

@Composable
fun ButtonGoogleAuth(signInClicked: () -> Unit) {
    Card(
        modifier = Modifier.size(50.dp).clickable{signInClicked()},
        shape = CircleShape,

    ) {
        Image(
            painter = painterResource(id = R.drawable.google1),
            contentDescription = "google",
        )
    }
}





