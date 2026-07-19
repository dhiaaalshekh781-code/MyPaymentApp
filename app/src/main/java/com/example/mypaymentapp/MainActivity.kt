package com.example.mypaymentapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // إنشاء نص بسيط يظهر عند فتح التطبيق
        val textView = TextView(this)
        textView.text = "مرحباً بك في تطبيق MyPaymentApp!"
        textView.textSize = 24f
        textView.gravity = android.view.Gravity.CENTER
        
        setContentView(textView)
    }
}
