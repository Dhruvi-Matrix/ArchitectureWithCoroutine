package com.example.architecturewithcoroutine.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.example.architecturewithcoroutine.ArchitectureApplication
import com.example.architecturewithcoroutine.Utils
import com.example.architecturewithcoroutine.data.network.ResponseStatus
import kotlinx.coroutines.Dispatchers

fun <T, A> resultLiveData(databaseQuery: suspend () -> LiveData<T>,
                          networkCall: suspend () -> ResponseStatus<A>,
                          saveCallResult: suspend (A) -> Unit): LiveData<ResponseStatus<T>> =
    liveData(Dispatchers.IO) {
        emit(ResponseStatus.loading(null))
        val source = databaseQuery.invoke().map { ResponseStatus.success(it) }
            emitSource(source)
            if (Utils.NetManager(ArchitectureApplication.applicationContext()).isConnectedToInternet) {
                val responseStatus = networkCall.invoke()
                Log.d("TestDataManager", "the data is:" + responseStatus.data.toString())
                if (responseStatus.status == ResponseStatus.Status.SUCCESS) {
                    saveCallResult(responseStatus.data!!)
                } else if (responseStatus.status == ResponseStatus.Status.ERROR) {
                    emit(ResponseStatus.error(responseStatus.message.toString(), null))
                    emitSource(source)
                }
            } else {
                emit(ResponseStatus.error("No Network Available, Please put internet on", null))
                emitSource(source)
            }
    }