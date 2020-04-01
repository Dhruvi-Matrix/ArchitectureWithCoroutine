package com.example.architecturewithcoroutine.view

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.architecturewithcoroutine.data.models.Post
import com.example.architecturewithcoroutine.data.network.ResponseStatus

class MainPresenter(
    private val viewModel: MainActivityViewModel,
    private val mainActivity: MainActivity
) {

    var postList:MutableLiveData<List<Post>> = MutableLiveData()

    fun getPosts(){

        viewModel.fetchPosts()
        viewModel.observableMovieList.observe(mainActivity, Observer {
            if(it.data!!.isNotEmpty()){
                when(it.status){
                    ResponseStatus.Status.RUNNING -> {
                        Log.d("TAG","RUNNING")
                    }
                    ResponseStatus.Status.SUCCESS ->{
                        Log.d("TAG","SUCCESS")
                        postList.value=it.data

                    }
                    ResponseStatus.Status.FAILED -> {
                        Log.d("TAG","FAILED")
                    }
                }
            }
        })

    }
}