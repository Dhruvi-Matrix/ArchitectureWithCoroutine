package com.example.architecturewithcoroutine.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.architecturewithcoroutine.R
import com.example.architecturewithcoroutine.data.models.Post
import kotlinx.android.synthetic.main.post_item.view.*

class PostAdapter:RecyclerView.Adapter<PostAdapter.RowViewHolder>() {

    private var postList:List<Post>? = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowViewHolder {
        val itemView= LayoutInflater.from(parent.context).inflate(R.layout.post_item,parent,false)
        return RowViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return postList!!.size
    }

    override fun onBindViewHolder(holder: RowViewHolder, position: Int) {
        if(postList!!.isNotEmpty()){
            val rowPos = holder.adapterPosition
            val post = postList?.get(rowPos)
            holder.itemView.apply {

                post_title_tv.text=post?.title
                post_body_tv.text=post?.body
            }
        }
    }

    fun setList(it: List<Post>?) {
        this.postList=it
    }

    inner class RowViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView)
}
