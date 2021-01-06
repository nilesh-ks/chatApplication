package com.internshala.chatapplication.activities

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.provider.Settings
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.internshala.chatapplication.R
import com.internshala.chatapplication.utils.ConnectionManager

lateinit var inputUsername: EditText
lateinit var inputPassword: TextInputEditText
lateinit var btnLogin: Button
lateinit var progressBar: ProgressBar
lateinit var checkBox: CheckBox
lateinit var forgot_password:TextView
lateinit var auth: FirebaseAuth
lateinit var user: FirebaseUser
lateinit var layoutLogin:ConstraintLayout
lateinit var sharedPreferences: SharedPreferences

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        sharedPreferences = getSharedPreferences(getString(R.string.preferences), MODE_PRIVATE)


        inputUsername = findViewById(R.id.username)
        inputPassword = findViewById(R.id.password)
        btnLogin = findViewById(R.id.buttonLogin)
        progressBar = findViewById(R.id.progressBar)
        checkBox=findViewById(R.id.checkbox_logged_in)
        forgot_password=findViewById(R.id.forgot_password)
        layoutLogin=findViewById(R.id.layoutLogin)

        layoutLogin.setOnClickListener{
            hideKeyBoard()
        }


        val isLoggedIn: Boolean= sharedPreferences.getBoolean("isLoggedIn",false)
        if (isLoggedIn){
            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(intent)
        }

        auth = FirebaseAuth.getInstance()

        btnLogin.setOnClickListener {
            if (!isValidInput()){
                return@setOnClickListener
            }
            if(ConnectionManager().checkConnectivity(this)){
                signIn()
            }
            else{
                val dialog = AlertDialog.Builder(this)
                dialog.setTitle("Connection error")
                dialog.setMessage("No internet found")
                dialog.setPositiveButton("Open Internet Settings") { dialogInterface, i ->
                    val intent = Intent(Settings.ACTION_WIFI_SETTINGS)
                    startActivity(intent)
                }
                dialog.setNegativeButton("Exit") { x, y ->
                    finishAffinity()
                }
                dialog.setNeutralButton("Cancel"){ dialogInterface, i ->
                    dialogInterface.cancel()
                }
                dialog.create()
                dialog.show()
            }
        }
        forgot_password.setOnClickListener {
            progressBar.visibility=View.VISIBLE
            val intent=Intent(this,ResetPasswordActivity::class.java)
            startActivity(intent)
//            val resetPasswordFragment=ResetPasswordFragment()
//            val fragmentTransaction:FragmentTransaction=supportFragmentManager.beginTransaction()
//            fragmentTransaction.replace(R.id.layoutLogin,resetPasswordFragment).commit()
        }
    }

    override fun onStart() {
        super.onStart()


    }

    private fun signIn() {
        val userEmail: String = inputUsername.text.toString()
        val userPassword: String = inputPassword.text.toString()

        progressBar.visibility=View.VISIBLE
        auth.signInWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener { loginTask ->
            if (loginTask.isSuccessful) {
                Toast.makeText(this, "Authentication Successful", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                user = auth.currentUser!!
                if (checkBox.isChecked){
                    sharedPreferences.edit().putBoolean("isLoggedIn", true).commit()
                }

                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Authentication failed!", Toast.LENGTH_SHORT).show()
            }
            progressBar.visibility=View.INVISIBLE
        }
    }
    fun hideKeyBoard(){
        val view=this.currentFocus
        if (view!=null){
            val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    private fun isValidInput(): Boolean {
        val email:String= inputUsername.text.toString()
        val password:String= inputPassword.text.toString()
        var valid = true
        if (TextUtils.isEmpty(email.trim())) {
            inputUsername.error = "Required"
            valid = false
        }

        if (TextUtils.isEmpty(password.trim())) {
            inputPassword.error = "Required"
            valid = false
        }
        return valid
    }


    override fun onBackPressed() {
        finish()
        super.onBackPressed()
    }
}