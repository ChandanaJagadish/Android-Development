package com.example.gramasuvidha

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.gramasuvidha.ui.list.ProjectListActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Redirect straight to ProjectListActivity
        startActivity(Intent(this, ProjectListActivity::class.java))
        finish()
    }
}