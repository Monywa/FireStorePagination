package com.ms.firestorepagination

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.ms.firestorepagination.model.User
import com.ms.firestorepagination.repository.RepositoryImpl

class AddActivity : AppCompatActivity() {

    lateinit var viewModel: UserViewModel

    val edName by lazy {
        findViewById<TextView>(R.id.ed_name)
    }
    val edAge by lazy {
        findViewById<TextView>(R.id.ed_age)
    }

    val btnSubmit by lazy {
        findViewById<Button>(R.id.btn_submit)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        val repository= RepositoryImpl(FireStore.getInstance())
        val factory=UserViewModelFactory(repository)

        viewModel= ViewModelProvider(this,factory)[UserViewModel::class.java]

       btnSubmit.setOnClickListener {
           val isNameAndAgeEmpty= edName.text.isEmpty() && edAge.text.isEmpty()
           if(!isNameAndAgeEmpty){
               val name=edName.text.toString()
               val age=edAge.text.toString().toInt()
               for(i in 0..10){
                   val user= User(name,age+i)
                   viewModel.addUser(user)
               }
               finish()
           }
       }


    }
}