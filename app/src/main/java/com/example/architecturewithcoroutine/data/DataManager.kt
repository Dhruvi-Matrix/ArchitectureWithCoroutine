package com.example.architecturewithcoroutine.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.architecturewithcoroutine.data.network.ResponseStatus
import com.example.architecturewithcoroutine.framework.helpers.ContextProviders
import kotlinx.coroutines.*

/*
 * Hero of our application. It performs whole flow of data.
 * What to do after something.*/
abstract class DataManager<RequestType, ResultType>(private val contextProviders: ContextProviders) {

    private val result: MediatorLiveData<ResponseStatus<ResultType>> = MediatorLiveData()
    private val job = SupervisorJob()
    private val coroutineContext = Dispatchers.IO + job

    init {
        result.postValue(ResponseStatus.loading(null))
        val sourceDatabase = loadFromDatabase()
        result.addSource(sourceDatabase) { data ->
            result.removeSource(sourceDatabase)
            if (shouldFetchData(sourceDatabase)) {
                fetchFromNetwork(sourceDatabase)
            } else {
                result.addSource(sourceDatabase) { newDataFromDatabase -> setValue(ResponseStatus.success(newDataFromDatabase)) }
            }

        }
    }

    private fun fetchFromDatabase() {
        val sourceDatabase = loadFromDatabase()
        result.addSource(sourceDatabase) {
                newDataFromDatabase -> setValue(ResponseStatus.success(newDataFromDatabase)) }
    }

    /*
     * This method works asynchronously by Room's own implementation.*/
    protected abstract fun loadFromDatabase(): LiveData<ResultType>

    /*
     * This method works asynchronously by Coroutine.*/
    protected abstract fun loadFromNetwork(): LiveData<RequestType>?

    protected abstract fun shouldFetchData(sourceDatabase: LiveData<ResultType>): Boolean

    protected abstract fun saveDataToDatabase(data: ResultType)

    protected abstract fun clearPreviousData()

    fun onFetchFailed(throwable: Throwable) {
        setValue(ResponseStatus.error(throwable.localizedMessage!!, null))
    }

    /*
     * Updates the live data which we are interested in.*/
    private fun setValue(newValue: ResponseStatus<ResultType>) {
        if (result.value !== newValue) {
            result.value = newValue
        }
    }

    fun toLiveData(): LiveData<ResponseStatus<ResultType>> {
        return result
    }

    protected abstract fun processResponse(response: RequestType): ResultType?

    private fun fetchFromNetwork(dbSource: LiveData<ResultType>) {
        val apiResponse = loadFromNetwork()
        result.addSource(dbSource) { newData ->
            setValue(ResponseStatus.loading(newData))
        }

        if (apiResponse != null) {
            result.addSource(apiResponse) { response ->
                result.removeSource(apiResponse)
                result.removeSource(dbSource)

                val processedData = processResponse(response)
                if (processedData == null) {
                    GlobalScope.launch(contextProviders.IO) {
                        result.addSource(loadFromDatabase()) { newData ->
                            setValue(ResponseStatus.success(newData))
                        }
                    }
                }

                clearPreviousData()
                GlobalScope.launch(contextProviders.IO) {
                    saveDataToDatabase(processedData!!)
                    GlobalScope.launch(contextProviders.Main) {
                        result.addSource(loadFromDatabase()) { newData ->
                            setValue(ResponseStatus.success(newData))
                        }
                    }
                }
            }
        }
    }
}
