package com.example.activitylifecycle

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ContactDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.contact_detail)
        findViewById<TextView>(R.id.textViewName).text =  intent.getStringExtra("Name")
        findViewById<TextView>(R.id.textViewID).text = intent.getStringExtra("ID")
        findViewById<TextView>(R.id.textViewDetail).text = intent.getStringExtra("Details")
    }
}