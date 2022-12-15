package com.ms.firestorepagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.ms.firestorepagination.model.User
import kotlinx.coroutines.tasks.await

class FireStorePagingSource(
    private val queryUserByName: Query
): PagingSource<QuerySnapshot, User>() {
    override fun getRefreshKey(state: PagingState<QuerySnapshot, User>): QuerySnapshot? {
        return null
    }

    override suspend fun load(params: LoadParams<QuerySnapshot>): LoadResult<QuerySnapshot, User> {
        return try{
            val currentPage=params.key?:queryUserByName.get().await()
            val lastVisibleUser=currentPage.documents[currentPage.size() - 1]
            val nextPage=queryUserByName.startAfter(lastVisibleUser).get().await()
            LoadResult.Page(
                data=currentPage.toObjects(User::class.java),
                prevKey = null,
                nextKey = nextPage
            )
        }catch (e:Exception){
            LoadResult.Error(e)
        }
    }
}