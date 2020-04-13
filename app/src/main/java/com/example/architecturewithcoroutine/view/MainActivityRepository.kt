package com.example.architecturewithcoroutine.view

import androidx.lifecycle.LiveData
import com.example.architecturewithcoroutine.data.database.PostDatabase
import com.example.architecturewithcoroutine.data.models.Post
import com.example.architecturewithcoroutine.data.network.ResponseHandler
import com.example.architecturewithcoroutine.data.network.ResponseStatus
import com.example.architecturewithcoroutine.data.resultLiveData
import com.example.architecturewithcoroutine.framework.ApiInterface
import kotlinx.coroutines.*
import org.koin.core.KoinComponent
import org.koin.core.inject


class MainActivityRepository : KoinComponent {

    private val apiInterface: ApiInterface by inject()
    private val responseHandler: ResponseHandler by inject()
    private val postDatabase: PostDatabase by inject()
    private val job = SupervisorJob()


    fun getPosts():LiveData<ResponseStatus<List<Post>>>{
        return resultLiveData(
              databaseQuery = { postDatabase.movieDao().getAllData() },
              networkCall = { ResponseStatus.success(apiInterface.getPost()) },
              saveCallResult = { postDatabase.movieDao().insertData(it) })
    }
}