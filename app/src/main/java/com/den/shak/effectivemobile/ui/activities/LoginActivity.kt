package com.den.shak.effectivemobile.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.den.shak.effectivemobile.databinding.ActivityLoginBinding
import com.den.shak.effectivemobile.ui.fragments.LoginFragment

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(binding.fragmentLoginContainer.id, LoginFragment())
                .commit()
        }
    }
}