package com.example.architecturewithcoroutine.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.test.core.app.ActivityScenario.launch
import com.example.architecturewithcoroutine.data.DataManager
import com.example.architecturewithcoroutine.data.database.PostDatabase
import com.example.architecturewithcoroutine.data.models.Post
import com.example.architecturewithcoroutine.data.network.ResponseHandler
import com.example.architecturewithcoroutine.data.network.ResponseStatus
import com.example.architecturewithcoroutine.framework.ApiInterface
import com.example.architecturewithcoroutine.framework.helpers.ContextProviders
import kotlinx.coroutines.*
import org.koin.core.KoinComponent
import org.koin.core.inject


class MainActivityRepository : KoinComponent {

    private val apiInterface: ApiInterface by inject()
    private val responseHandler: ResponseHandler by inject()
    private val postDatabase: PostDatabase by inject()
    private val job = SupervisorJob()



    fun getPost(): LiveData<ResponseStatus<List<Post>>> {
        return object : DataManager<ResponseStatus<List<Post>>, List<Post>>(ContextProviders.getInstance()) {
            override fun loadFromDatabase(): LiveData<List<Post>> {
                return postDatabase.movieDao().getAllData()
            }

            override fun loadFromNetwork(): LiveData<ResponseStatus<List<Post>>>? = runBlocking  {

                val postListData = MutableLiveData<ResponseStatus<List<Post>>>()

                val apiData = apiInterface.getPost()
                postListData.postValue(ResponseStatus.success(apiData))

                return@runBlocking postListData
            }

            override fun shouldFetchData(sourceDatabase: LiveData<List<Post>>): Boolean {
                return true
            }

            override fun saveDataToDatabase(data: List<Post>) = runBlocking {

                withContext(Dispatchers.IO) {
                    postDatabase.movieDao().insertData(data)
                }

            }

            override fun clearPreviousData() {

            }

            override fun processResponse(response: ResponseStatus<List<Post>>): List<Post>? {
                return response.data
            }

        }.toLiveData()
    }
}