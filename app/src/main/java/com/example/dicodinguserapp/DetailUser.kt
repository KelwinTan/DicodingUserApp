package com.example.dicodinguserapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_detail_user.*

class DetailUser : AppCompatActivity() {
    companion object {
        const val DETAIL_USER = "detail_user"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_user)

        val txt_detail_name: TextView = findViewById(R.id.txt_detail_name)

        val user = intent.getParcelableExtra<User>(DETAIL_USER) as User
        txt_detail_name.text = user.name
    }
}