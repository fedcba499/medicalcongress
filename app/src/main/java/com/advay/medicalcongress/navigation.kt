package com.advay.medicalcongress

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import com.google.android.material.floatingactionbutton.FloatingActionButton


class navigation : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)

        val myWebViewNavig: WebView = findViewById(R.id.webviewNavig)
        myWebViewNavig.loadUrl("https://www.bing.com/")

        val home_btn = findViewById<FloatingActionButton>(R.id.homeBtn)

        home_btn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }
}