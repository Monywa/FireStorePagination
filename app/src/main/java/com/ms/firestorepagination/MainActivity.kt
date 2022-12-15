package com.ms.firestorepagination

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.ms.firestorepagination.repository.RepositoryImpl
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    val rcUserList by lazy {
        findViewById<RecyclerView>(R.id.rv_user_list)
    }

    val btnNext by lazy {
        findViewById<FloatingActionButton>(R.id.btn_next)
    }

    val progressBar by lazy {
        findViewById<ProgressBar>(R.id.pagination_progress_bar)
    }
//    lateinit var itemAdapter: ItemAdapter
    lateinit var pageAdapter:ItemPagingAdapter

    lateinit var viewModel: UserViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val repository = RepositoryImpl(FireStore.getInstance())
        val factory = UserViewModelFactory(repository)

        viewModel = ViewModelProvider(this, factory)[UserViewModel::class.java]

        setUpRecyclerView()
        getPaginatedUsers()
        setProgressBarAccordingToLoadState()



        btnNext.setOnClickListener {
            val intent = Intent(applicationContext, AddActivity::class.java)
            startActivity(intent)
        }
    }
//    private fun setupRecyclerView() {
//        itemAdapter = ItemAdapter()
//        rcUserList.apply {
//            adapter = itemAdapter
//        }
//    }

    private fun setUpRecyclerView(){
        pageAdapter= ItemPagingAdapter()
        rcUserList.adapter=pageAdapter
    }

    fun getPaginatedUsers(){
        lifecycleScope.launch{
            viewModel.flow.collectLatest {
                pageAdapter.submitData(it)
            }
        }
    }

    private fun setProgressBarAccordingToLoadState() {
        lifecycleScope.launch {
            pageAdapter.loadStateFlow.collectLatest {
                progressBar.isVisible = it.append is LoadState.Loading
            }
        }
    }


//    fun getUsers(){
//        viewModel.getAllUser().observe(this, Observer {
//            itemAdapter.differ.submitList(it)
//            Log.d("activity", it.toString())
//        })
//    }



}