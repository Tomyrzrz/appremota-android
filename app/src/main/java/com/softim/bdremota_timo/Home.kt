package com.softim.bdremota_timo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.softim.bdremota_timo.databinding.ActivityHomeBinding
import com.softim.bdremota_timo.databinding.ActivityMainBinding

class Home : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogout.setOnClickListener {
            Firebase.auth.signOut()
            enviarAMain()
        }

    }
    override fun onStart() {
        super.onStart()
        val currentUser = Firebase.auth.currentUser
        if(currentUser == null)
            enviarAMain()
    }

    private fun enviarAMain() {
        val main = Intent(this, MainActivity::class.java)
        startActivity(main)
        this.finish()
    }
}