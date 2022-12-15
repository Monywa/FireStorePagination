package com.ms.firestorepagination

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ms.firestorepagination.model.User

class ItemPagingAdapter:PagingDataAdapter<User,ItemPagingAdapter.ViewHolder>(diffCallBack) {
    inner class ViewHolder(val view: View):RecyclerView.ViewHolder(view) {
        val name=view.findViewById<TextView>(R.id.tv_name)
        val age=view.findViewById<TextView>(R.id.tv_age)
    }

    companion object{
        private val diffCallBack=object:DiffUtil.ItemCallback<User>(){
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem==newItem
            }

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem==newItem
            }
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.list_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current=getItem(position)?: return

        holder.name.text=current.name.toString()
        holder.age.text=current.age.toString()
    }


}