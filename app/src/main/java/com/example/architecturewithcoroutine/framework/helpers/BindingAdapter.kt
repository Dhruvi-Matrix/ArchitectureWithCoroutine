package com.example.architecturewithcoroutine.framework.helpers

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.architecturewithcoroutine.data.models.Post
import com.example.architecturewithcoroutine.view.PostAdapter
import java.util.*

class BindingAdapter {

    companion object {

        @JvmStatic
        @BindingAdapter("postList")
        fun setPostList(recyclerView: RecyclerView, postList: List<Post>?) {
            if (postList != null) {
                (Objects.requireNonNull(recyclerView.adapter) as PostAdapter).setList(postList)
            }
        }

    }
}