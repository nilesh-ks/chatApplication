package com.internshala.chatapplication.activities

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.internshala.chatapplication.R
import com.internshala.chatapplication.utils.ConnectionManager

lateinit var emailForResetPassword: EditText
lateinit var btnSendEmail: Button
lateinit var btnGotoLogin:Button
class ResetPasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_passwoed)
        title="Reset Password"

        emailForResetPassword = findViewById(R.id.emailForResetPassword)
        btnSendEmail = findViewById(R.id.btnSendEmail)
        btnGotoLogin=findViewById(R.id.btnGotoLogin)
        progressBar=findViewById(R.id.progressBar)
        progressBar.visibility=View.GONE

//        val email = emailForResetPassword.text.toString().trim()
        btnSendEmail.setOnClickListener {
            progressBar.visibility=View.VISIBLE
            if (emailForResetPassword.text.toString().isEmpty()) {
                emailForResetPassword.error = "Required"
                progressBar.visibility=View.GONE
                return@setOnClickListener
            }
            if (ConnectionManager().checkConnectivity(this)) {

                btnSendEmail.isPressed = true
                auth.sendPasswordResetEmail(emailForResetPassword.text.toString()).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        btnSendEmail.text = "Resend Email"
                        Toast.makeText(this, "Email Sent", Toast.LENGTH_SHORT).show()
                        btnGotoLogin.visibility=View.VISIBLE
                        btnSendEmail.isPressed = false
                        progressBar.visibility=View.GONE
                    } else {
                        btnSendEmail.isPressed=false
                        Toast.makeText(this, task.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                //connect to internet
                progressBar.visibility=View.GONE
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
        btnGotoLogin.setOnClickListener {
            onBackPressed()
            finish()
        }
    }
}