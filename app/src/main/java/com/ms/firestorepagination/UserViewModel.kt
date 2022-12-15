package com.ms.firestorepagination

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.cachedIn
import com.ms.firestorepagination.model.User
import com.ms.firestorepagination.repository.Repository
import com.ms.firestorepagination.util.Constants.Constants.PAGE_SIZE
import kotlinx.coroutines.launch

class UserViewModel(
    private val repository: Repository
):ViewModel() {

    fun getAllUser()=repository.getUsers()

    fun addUser(user: User)=viewModelScope.launch {
        repository.addUser(user)
    }

    val flow=Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE
            )
    ){
        FireStorePagingSource(repository.provideQueryProductsByName())
    }.flow.cachedIn(viewModelScope)


}

class UserViewModelFactory(private val repository: Repository):ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
         return UserViewModel(repository) as T
    }
}