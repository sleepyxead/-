package com.example.orders.Views.Activities

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.orders.MainActivity
import com.example.orders.Utilities.DatabaseHelper
import com.example.orders.Utilities.PreferenceManager
import com.example.orders.databinding.ActivityLoginBinding


class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val databaseHelper = DatabaseHelper(this@LoginActivity)
        val manager = PreferenceManager(this@LoginActivity)

        if (manager.getBoolean("skip")){
            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
            finish()
        }

        binding.button.setOnClickListener { v ->
            val email = binding.logintext.text.toString()
            val password = binding.passwordtext.text.toString()
            if (validateData(email,password)) {
                val checkCredentials = databaseHelper.checkLoginPassword(email, password)
                if (checkCredentials) {
                    manager.putString("Email", email)
                    manager.putString("Password", password)
                    manager.putBoolean("skip",true)
                    startActivity(Intent(applicationContext, MainActivity::class.java))
                    finish()
                } else {
                    if (email==manager.getString("Email")&&password==manager.getString("Password")){
                        manager.putString("Email", email)
                        manager.putString("Password", password)
                        manager.putBoolean("skip",true)
                        startActivity(Intent(applicationContext, MainActivity::class.java))
                    }else{
                        showToast("Аккаунта не существует!")
                    }
                }
            }else if(email == "" || password == ""){
                showToast("Поля не заполнены!")
            }
            else {
               validateData(email, password)
            }
        }
        binding.reg.setOnClickListener { v ->
            startActivity(Intent(this@LoginActivity, SignUpActivity::class.java))
        }
    }
    private fun validateData(email: String, password: String): Boolean {
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            showToast("Почта введена не корректно!")
            return false
        }
        if (password.length < 2) {
            showToast("Слишком короткий пароль")
            return false
        }
        return true
    }
    private fun showToast(message: String) {
        Toast.makeText(this@LoginActivity, message, Toast.LENGTH_SHORT).show()
    }
}