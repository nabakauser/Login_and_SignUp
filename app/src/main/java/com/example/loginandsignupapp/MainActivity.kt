package com.example.loginandsignupapp

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var uiEtUserName:EditText? = null
    private var uiEtUserEmail:EditText? = null
    private var uiEtUserPassword:EditText? = null
    private var uiEtConfirmPassword: EditText? = null
    private var uiButtonSubmit: Button? = null
    private var uiTvLoginPrompt: TextView?= null

    private val sharedPreferences: MySharedPreferences by lazy {
        MySharedPreferences(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpUi()
        navigateToLoginPage()
        checkPreviousFieldSavedInDb()
        setUpOnClickListeners()
    }

    private fun checkPreviousFieldSavedInDb() {
        if (sharedPreferences.getUserName() != null) {
            navigateToNextScreen()
            finish()
        }
    }

    private fun setUpUi() {
        uiEtUserName = findViewById(R.id.uiEtUserName)
        uiEtUserEmail = findViewById(R.id.uiEtUserEmail)
        uiEtUserPassword= findViewById(R.id.uiEtPassword)
        uiEtConfirmPassword= findViewById(R.id.uiEtConfirmPassword)
        uiButtonSubmit = findViewById(R.id.uiButtonSubmit)
        uiTvLoginPrompt = findViewById(R.id.uiTvSignUpPrompt)

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

    private fun validateEmail(): Boolean {
        val emailToString: String = uiEtUserEmail?.text.toString()
        return if (emailToString.isEmpty()) {
            uiEtUserEmail?.error = getString(R.string.emailErrorMessage)
            uiEtUserEmail?.requestFocus()
            false
        } else if ((emailToString.isNotEmpty()) && (!Patterns.EMAIL_ADDRESS.matcher(emailToString)
                .matches())) {
            uiEtUserEmail?.error = getString(R.string.emailFormatErrorMessage)
            false
        } else {
            uiEtUserEmail?.error = null
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
            if(passwordToString.length <7) {
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

    private fun validateConfirmPassword(): Boolean {
        val passwordToString: String = uiEtUserPassword?.text.toString()
        val confirmPasswordToString: String = uiEtConfirmPassword?.text.toString()
        return if(confirmPasswordToString != passwordToString) {
            uiEtConfirmPassword?.error = getString(R.string.confirmPasswordErrorMessage)
            uiEtConfirmPassword?.requestFocus()
            false
        } else {
            uiEtConfirmPassword?.error = null
            true
        }
    }

    private fun submitAction() {
        if(validateName() && validateEmail() && validatePassword() && validateConfirmPassword()) {
            saveInputFieldsToDb()
            navigateToNextScreen()
        }
    }

    private fun saveInputFieldsToDb() {

        val userName: String = uiEtUserName?.text.toString().trim()
        val userEmail: String = uiEtUserEmail?.text.toString().trim()
        val userPassword: String = uiEtUserPassword?.text.toString().trim()

        sharedPreferences.setUserName(userName)
        sharedPreferences.setUserEmail(userEmail)
        sharedPreferences.setUserPassword(userPassword)
    }

    private fun navigateToNextScreen() {
        val intent = Intent(this@MainActivity, LoggedInActivity::class.java)
        startActivity(intent)
    }

    private fun setUpOnClickListeners() {
        uiButtonSubmit?.setOnClickListener {
            submitAction()
        }
    }

    private fun navigateToLoginPage() {
        uiTvLoginPrompt?.setOnClickListener {
            val intent = Intent(this@MainActivity, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}