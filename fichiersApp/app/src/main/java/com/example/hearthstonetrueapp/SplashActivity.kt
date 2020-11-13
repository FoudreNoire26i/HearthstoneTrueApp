package com.example.hearthstonetrueapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hearthstonetrueapp.dataClass.TokenRepository

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        TokenRepository.tokenLiveData.observe(this,{
            accessToken = it.access_token
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        } )
        TokenRepository.getToken()
    }
}