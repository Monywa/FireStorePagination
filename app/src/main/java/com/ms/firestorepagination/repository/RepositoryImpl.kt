package com.ms.firestorepagination.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.toObject
import com.ms.firestorepagination.model.User
import com.ms.firestorepagination.util.Constants
import com.ms.firestorepagination.util.Constants.Constants.USERS_COLLECTION
import kotlinx.coroutines.tasks.await

class RepositoryImpl(
    private val db:FirebaseFirestore
): Repository {
    private val userCollectionRef = db.collection(USERS_COLLECTION)
    override suspend fun addUser(user: User) {
        userCollectionRef.add(user).await()
    }

    override fun getUsers(): LiveData<List<User>> {
        val liveList = MutableLiveData<List<User>>()

        val querySnapshot = userCollectionRef

//        querySnapshot.add { querySnapshot ->
//            querySnapshot?.let {
//                val list = mutableListOf<User>()
//                for (document in querySnapshot.documents) {
//                    val user = document.toObject<User>()
//                    list.add(user!!)
//                }
//                liveList.value = list
//            }
//        }
//        return liveList
//    }
        querySnapshot.addSnapshotListener{snapshot,e->
            if (e != null) {
                return@addSnapshotListener
            }

           snapshot?.let {
               val list = mutableListOf<User>()
                for (document in it.documents) {
                    val user = document.toObject<User>()
                    list.add(user!!)
                }
                liveList.value = list
            }
        }
        return liveList
           }

    override fun provideQueryProductsByName(): Query {
            return db.collection(Constants.Constants.USERS_COLLECTION)
                .orderBy(Constants.Constants.AGE_PROPERTY, Query.Direction.ASCENDING)
                .limit(Constants.Constants.PAGE_SIZE.toLong())
        }
}