package com.example.architecturewithcoroutine.view

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.architecturewithcoroutine.data.models.Post
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject

class MainActivityViewModel : ViewModel(),KoinComponent {

    private val repository : MainActivityRepository by inject()
    private val job = SupervisorJob()
    private val coroutineContext = Dispatchers.IO + job


    val observableMovieList: MutableLiveData<List<Post>> = MutableLiveData()

    fun fetchPosts(){
        viewModelScope.launch(coroutineContext) {
            val  response = repository.getPosts()
            Log.d("response is" , response.data.toString())
            observableMovieList.postValue(response.data)
        }

    }
}