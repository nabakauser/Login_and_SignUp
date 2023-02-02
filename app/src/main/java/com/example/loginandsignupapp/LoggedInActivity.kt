package com.example.loginandsignupapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class LoggedInActivity : AppCompatActivity() {

    private var uiTvEnteredName: TextView? = null
    private var uiTvEnteredEmail: TextView? = null
    private var uiTvEnteredPassword: TextView? = null
    private var uiButtonLogout:Button? = null
    private val sharedPreferences: MySharedPreferences by lazy {
        MySharedPreferences(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logged_in)
        setUserUI()
        displayInputFields()
        setUpListeners()
    }

    private fun setUserUI() {
        uiTvEnteredName = findViewById(R.id.uiTvEnteredName)
        uiTvEnteredEmail = findViewById(R.id.uiTvEnteredEmail)
        uiTvEnteredPassword = findViewById(R.id.uiTvEnteredPassword)
        uiButtonLogout = findViewById(R.id.uiButtonLogout)
    }

    //extension function
    private fun String.replaceSpaceAndHyphen(): String {
        var replacedString = ""
        for (i in this.indices) {
            replacedString += if (this[i] == ' ') '-' else if (this[i] == '-') ' ' else this[i]
        }
        return replacedString
    }

    private fun displayInputFields() {
        uiTvEnteredName?.text = sharedPreferences.getUserName()?.replaceSpaceAndHyphen()
        uiTvEnteredEmail?.text = sharedPreferences.getUserEmail()
        uiTvEnteredPassword?.text = sharedPreferences.getUserPassword()
    }

    private fun setUpListeners() {
        uiButtonLogout?.setOnClickListener {
            actionSubmit()
        }
    }

    private fun actionSubmit() {
        sharedPreferences.clearData()
        navigateToNextScreen()
        finish()
    }

    private fun navigateToNextScreen() {
        val intent = Intent(this@LoggedInActivity, MainActivity::class.java)
        startActivity(intent)
    }
}
