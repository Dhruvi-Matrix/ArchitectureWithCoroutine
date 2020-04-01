package com.example.architecturewithcoroutine.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.architecturewithcoroutine.data.models.Post
import com.example.architecturewithcoroutine.databinding.PostItemBinding
import kotlinx.android.synthetic.main.post_item.view.*

class PostAdapter(private val layoutInflater: LayoutInflater):RecyclerView.Adapter<PostAdapter.RowViewHolder>() {

    private var postList:List<Post>? = listOf()
    private lateinit var itemBinding: PostItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowViewHolder {
        itemBinding=PostItemBinding.inflate(layoutInflater,parent,false)
        return RowViewHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return postList!!.size
    }

    override fun onBindViewHolder(holder: RowViewHolder, position: Int) {
        if(postList!!.isNotEmpty()){
            val rowPos = holder.adapterPosition
            val post = postList?.get(rowPos)
            holder.itemView.apply {

              /*  post_title_tv.text=post?.title
                post_body_tv.text=post?.body*/
            }
        }
    }

    fun setList(it: List<Post>?) {
        this.postList=it
    }

    inner class RowViewHolder(itemBinding: PostItemBinding) :RecyclerView.ViewHolder(itemBinding.root)
}
