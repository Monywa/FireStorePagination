package com.ms.firestorepagination

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.ms.firestorepagination.util.Constants.Constants.NAME_PROPERTY
import com.ms.firestorepagination.util.Constants.Constants.PAGE_SIZE
import com.ms.firestorepagination.util.Constants.Constants.USERS_COLLECTION

object FireStore{
    fun getInstance():FirebaseFirestore{
        return FirebaseFirestore.getInstance()
    }


}