package com.example.hearthstonetrueapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import com.example.hearthstonetrueapp.bdd.MyCardsRepository
import com.example.hearthstonetrueapp.dataClass.TokenRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash)

        MyCardsRepository.initDatabase(context = this, scope = CoroutineScope(SupervisorJob()))

        TokenRepository.tokenLiveData.observe(this,{
            accessToken = it.access_token
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        } )

        TokenRepository.getToken()
    }
}