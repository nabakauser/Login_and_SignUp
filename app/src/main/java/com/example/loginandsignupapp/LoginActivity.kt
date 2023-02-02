package com.example.loginandsignupapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class LoginActivity : AppCompatActivity() {

    private var uiEtUserName: EditText?= null
    private var uiEtUserPassword: EditText?= null
    private var uiTvSignUpPrompt: TextView?= null
    private var uiButtonSubmit: Button?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setUpUi()
        setUpOnClickListeners()
        navigateToSignUpPage()
    }

    private fun setUpUi() {
        uiEtUserName = findViewById(R.id.uiEtUserName)
        uiEtUserPassword= findViewById(R.id.uiEtPassword)
        uiTvSignUpPrompt = findViewById(R.id.uiTvSignUpPrompt)
        uiButtonSubmit = findViewById(R.id.uiButtonSubmit)

    }

    private fun validateName(): Boolean {
        return if (uiEtUserName?.text.toString().isEmpty()) {
            uiEtUserName?.error = getString(R.string.userNameErrorMessage)
            uiEtUserName?.requestFocus()
            false
        } else {
            uiEtUserName?.error = null
            true
        }
    }

    private fun validatePassword(): Boolean {
        val passwordToString: String = uiEtUserPassword?.text.toString()
        if(passwordToString.isEmpty()) {
            uiEtUserPassword?.error = getString(R.string.passwordErrorMessage)
            uiEtUserPassword?.requestFocus()
            return false
        }
        return if(passwordToString.isNotEmpty()) {
            if(passwordToString.length <4) {
                uiEtUserPassword?.error = getString(R.string.passwordStrengthErrorMessage)
                uiEtUserPassword?.requestFocus()
                false
            } else {
                uiEtUserPassword?.error = null
                true
            }
        } else {
            uiEtUserPassword?.error = getString(R.string.passwordStrengthErrorMessage)
            false
        }
    }

    private fun submitAction() {
        if(validateName() && validatePassword()) {
            navigateToSignUpPage()
        }
    }

    private fun setUpOnClickListeners() {
        uiButtonSubmit?.setOnClickListener {
            submitAction()
        }
    }

    private fun navigateToSignUpPage() {
        uiTvSignUpPrompt?.setOnClickListener {
            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }
}