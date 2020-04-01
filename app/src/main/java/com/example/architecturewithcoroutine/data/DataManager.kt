package com.example.architecturewithcoroutine.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.architecturewithcoroutine.data.network.ResponseStatus
import kotlinx.coroutines.*

/*
 * Hero of our application. It performs whole flow of data.
 * What to do after something.*/
abstract class DataManager<RequestType, ResultType> {

    private val result: MediatorLiveData<ResponseStatus<ResultType>> = MediatorLiveData()
    private val job = SupervisorJob()
    private val coroutineContext = Dispatchers.IO + job

    init {
        result.postValue(ResponseStatus.loading(null))
        val sourceDatabase = loadFromDatabase()
        result.addSource(sourceDatabase) { data ->
            result.removeSource(sourceDatabase)
            if (shouldFetchData()) {
                fetchFromNetwork(sourceDatabase)
            } else {
                result.addSource(sourceDatabase) { newDataFromDatabase -> setValue(ResponseStatus.success(newDataFromDatabase)) }
            }

        }
    }

    /*
     * This method works asynchronously by Room's own implementation.*/
    protected abstract fun loadFromDatabase(): LiveData<ResultType>

    /*
     * This method works asynchronously by Coroutine.*/
    protected abstract fun loadFromNetwork(): LiveData<RequestType>?

    protected abstract fun shouldFetchData(): Boolean

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

    private fun fetchFromNetwork(sourceDatabase: LiveData<ResultType>) {
        val sourceNetwork = loadFromNetwork()
        result.addSource(sourceDatabase) { dataFromDatabase ->
            /*setValue(DataRequest.loading(dataFromDatabase)));*/
            setValue(ResponseStatus.loading(null))
        }
        result.addSource(sourceNetwork!!) { dataFromNetwork ->
            result.removeSource(sourceNetwork)
            result.removeSource(sourceDatabase)
            CoroutineScope(coroutineContext).launch{
                withContext(Dispatchers.IO) {
                    val processedData = processResponse(dataFromNetwork)
                    if (processedData == null) {
                        withContext(Dispatchers.Main) {
                            setValue(
                                ResponseStatus.error(
                                    "Not Found",
                                    null
                                )
                            )
                        }

                    }

                    clearPreviousData()
                    saveDataToDatabase(processedData!!)
                    withContext(Dispatchers.Main) {
                        result.addSource(loadFromDatabase()) { newDataFromDatabase ->
                            setValue(
                                ResponseStatus.success(newDataFromDatabase)
                            )
                        }
                    }
                }

            }
        }
    }
}