package com.example.architecturewithcoroutine.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.architecturewithcoroutine.data.DataManager
import com.example.architecturewithcoroutine.data.database.PostDatabase
import com.example.architecturewithcoroutine.data.models.Post
import com.example.architecturewithcoroutine.data.network.ResponseHandler
import com.example.architecturewithcoroutine.data.network.ResponseStatus
import com.example.architecturewithcoroutine.framework.ApiInterface
import kotlinx.coroutines.*
import org.koin.core.KoinComponent
import org.koin.core.inject


class MainActivityRepository:KoinComponent {

    private val apiInterface: ApiInterface by inject()
    private val responseHandler: ResponseHandler by inject()
    private val postDatabase : PostDatabase by inject()
    private val job = SupervisorJob()
    private val coroutineContext = Dispatchers.IO + job


    suspend fun getPosts(): ResponseStatus<List<Post>> {
        return responseHandler.handleSuccess(apiInterface.getPost())
    }

    suspend fun getPost(): LiveData<ResponseStatus<List<Post>>> {
        return object : DataManager<ResponseStatus<List<Post>>, List<Post>>() {
            override fun loadFromDatabase(): LiveData<List<Post>> {
                return postDatabase.movieDao().getAllData()
            }

            override fun loadFromNetwork(): LiveData<ResponseStatus<List<Post>>>? {
                val postListData=MutableLiveData<ResponseStatus<List<Post>>>()
                CoroutineScope(coroutineContext).launch {
                    val apiData= apiInterface.getPost()
                    postListData.value= ResponseStatus.success(apiData)
                }
                return postListData

            }

            override fun shouldFetchData(): Boolean {
                return true
            }

            override fun saveDataToDatabase(data: List<Post>) {
                CoroutineScope(coroutineContext).launch {
                    withContext(Dispatchers.IO) {
                        postDatabase.movieDao().insertData(data)
                    }
                }
            }

            override fun clearPreviousData() {
                postDatabase.movieDao().removeAllPost()
            }

            override fun processResponse(response: ResponseStatus<List<Post>>): List<Post>? {
                return response.data
            }

        }.toLiveData()
    }
}