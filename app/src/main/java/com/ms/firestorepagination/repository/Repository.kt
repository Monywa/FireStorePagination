package com.ms.firestorepagination.repository

import androidx.lifecycle.LiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.ms.firestorepagination.model.User
import com.ms.firestorepagination.util.Constants
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun addUser(user: User)
    fun getUsers(): LiveData<List<User>>
    fun provideQueryProductsByName(): Query

}