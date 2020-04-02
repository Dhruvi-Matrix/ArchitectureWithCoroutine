package com.example.architecturewithcoroutine.view

import android.util.Log
import androidx.lifecycle.*
import com.example.architecturewithcoroutine.data.models.Post
import com.example.architecturewithcoroutine.data.network.ResponseStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject

class MainActivityViewModel : ViewModel(),KoinComponent {

    private val repository : MainActivityRepository by inject()
    private val job = SupervisorJob()
    private val coroutineContext = Dispatchers.IO + job


    private var postList: MutableLiveData<List<Post>> = MutableLiveData()

    private var postListTrigger: MutableLiveData<Boolean> = MutableLiveData()

    internal
    val observablePostList: LiveData<ResponseStatus<List<Post>>>
        get() = Transformations.switchMap(postListTrigger) { repository.getPost() }

    fun getPostListTrigger(): MutableLiveData<Boolean> {
        return postListTrigger
    }

    fun getPostList(): MutableLiveData<List<Post>> {
        return postList
    }
}