package com.example.orders.Views.Activities

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.orders.Utilities.DatabaseHelper
import com.example.orders.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val databaseHelper = DatabaseHelper(this@SignUpActivity)

        binding.button.setOnClickListener { v ->
            val email = binding.logintext.text.toString()
            val password = binding.passwordtext.text.toString()
            if (password == binding.secpasswordtext.text.toString()){
                if (validateData(email, password)) {
                    val checkUserEmail: Boolean = databaseHelper.checkLogin(email)
                    if (!checkUserEmail) {
                        val insert: Boolean = databaseHelper.insertData(email, password)
                        if (insert) {
                            startActivity(Intent(this@SignUpActivity, LoginActivity::class.java))
                        } else {
                            showToast("Ошибка регистрации")
                        }
                    } else {
                        showToast("Пользователь уже зарегестрирован!")
                    }
                } else if (email == "" || password == "") {
                    showToast("Поля не заполнены!")
                } else {
                    validateData(email, password)
                }
            }else{
                showToast("Пароли не совпадают!")
            }

        }
        binding.reg.setOnClickListener { v ->
            startActivity(
                Intent(
                    applicationContext,
                    LoginActivity::class.java
                )
            )
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
        Toast.makeText(this@SignUpActivity, message, Toast.LENGTH_SHORT).show()
    }
}