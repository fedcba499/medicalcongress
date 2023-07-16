package com.advay.medicalcongress


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val myWebView: WebView = findViewById(R.id.webview)
        myWebView.webViewClient = WebViewClient()
        Log.i("Main FIle ", "check how logcat works")

        myWebView.settings.javaScriptEnabled = true
        myWebView.settings.domStorageEnabled = true


        myWebView.loadUrl("https://www.google.co.in/")

        val notify_btn = findViewById<FloatingActionButton>(R.id.notificationBtn)

        notify_btn.setOnClickListener {
            val intent = Intent(this, navigation::class.java)
            startActivity(intent)
        }


        startService(Intent(this, MyFirebaseMessagingService::class.java))

    }


}