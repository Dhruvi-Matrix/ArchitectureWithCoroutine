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



    var postListLiveData:LiveData<ResponseStatus<List<Post>>> = repository.getPosts()
}