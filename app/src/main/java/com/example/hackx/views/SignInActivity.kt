package com.example.hackx.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import com.example.hackx.R
import com.example.hackx.extensions.Extensions.toast
import com.example.hackx.utils.FirebaseUtils.firebaseAuth
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignInActivity : AppCompatActivity() {
    lateinit var signInEmail: String
    lateinit var signInPassword: String
    lateinit var signInInputsArray: Array<EditText>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        signInInputsArray = arrayOf(etSignInEmail, etSignInPassword)
        btnCreateAccount2.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }

        btnSignIn.setOnClickListener {
            signInUser()
        }
    }

    private fun notEmpty(): Boolean = signInEmail.isNotEmpty() && signInPassword.isNotEmpty()

    private fun signInUser() {
        signInEmail = etSignInEmail.text.toString().trim()
        signInPassword = etSignInPassword.text.toString().trim()
        if (notEmpty()) {
            firebaseAuth.signInWithEmailAndPassword(signInEmail, signInPassword)
                .addOnCompleteListener { signIn ->
                    if (signIn.isSuccessful) {
                        var i:Intent=getIntent()
                        var s1=i.getStringExtra("profile").toString()
                        if(s1=="boss")
                            startActivity(Intent(this, BossHomeActivity::class.java))
                        else
                            startActivity(Intent(this, EmployeeHomeActivity::class.java))
                        toast("signed in successfully")
                        finish()
                    } else {
                        toast("sign in failed")
                    }
                }
        } else {
            signInInputsArray.forEach { input ->
                if (input.text.toString().trim().isEmpty()) {
                    input.error = "${input.hint} is required"
                }
            }
        }
    }
}