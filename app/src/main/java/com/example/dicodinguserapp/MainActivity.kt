package com.example.dicodinguserapp

import android.content.res.TypedArray
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: UserAdapter
    private lateinit var dataName: Array<String>
    private lateinit var dataUsername: Array<String>
    private lateinit var dataAvatar: TypedArray

    private var users = arrayListOf<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listView: ListView = findViewById(R.id.user_list_view)
        adapter = UserAdapter(this)
        listView.adapter = adapter

        prepare()

    }

    private fun prepare(){
        dataName = resources.getStringArray(R.array.name)
        dataUsername = resources.getStringArray(R.array.username)
        dataAvatar = resources.obtainTypedArray(R.array.avatar)
    }

    private fun addItem(){
        for (position in dataName.indices){
            val user = User(
                    dataUsername[position],
                    dataName[position],
                    dataAvatar.getResourceId(position, -1)

                    )
        }
    }



}