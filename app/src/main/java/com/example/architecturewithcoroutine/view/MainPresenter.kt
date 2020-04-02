package com.example.architecturewithcoroutine.view

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.architecturewithcoroutine.data.models.Post
import com.example.architecturewithcoroutine.data.network.ResponseStatus

class MainPresenter(private val viewModel: MainActivityViewModel, private val mainActivity: MainActivity) {
    //request model
    private  var postListTrigger: MutableLiveData<Boolean>

    //response model
    private  var postList: MutableLiveData<List<Post>>

    var isLoading: MutableLiveData<Boolean> = MutableLiveData()


    init{
        isLoading.value=false
        postListTrigger=viewModel.getPostListTrigger()
        postList = viewModel.getPostList()
    }

    fun getPosts(){
        viewModel.observablePostList.observe(mainActivity,Observer<ResponseStatus<List<Post>>> {this.consumePostResponse(it)})
    }

    private fun consumePostResponse(it: ResponseStatus<List<Post>>) {
        when(it.status){
            ResponseStatus.Status.RUNNING -> {
                isLoading.value=true
                Log.d("TAG","RUNNING")
            }
            ResponseStatus.Status.SUCCESS ->{
                isLoading.value=false
                Log.d("TAG","SUCCESS")
                postList.value=it.data

            }
            ResponseStatus.Status.FAILED -> {
                isLoading.value=true
                Log.d("TAG","FAILED")
            }
        }
    }

    fun setPostListTrigger(postListRequest: Boolean) {
        postListTrigger.value=postListRequest
    }

    fun getPostList(): MutableLiveData<List<Post>> {
        return postList
    }

}