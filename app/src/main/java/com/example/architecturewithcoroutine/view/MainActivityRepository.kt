package com.example.architecturewithcoroutine.view

import com.example.architecturewithcoroutine.data.models.Post
import com.example.architecturewithcoroutine.data.network.ResponseHandler
import com.example.architecturewithcoroutine.data.network.ResponseStatus
import com.example.architecturewithcoroutine.framework.ApiInterface
import org.koin.core.KoinComponent
import org.koin.core.inject


class MainActivityRepository():KoinComponent {

    private val apiInterface: ApiInterface by inject()
    private val responseHandler: ResponseHandler by inject()

    suspend fun getPosts(): ResponseStatus<List<Post>> {
        return responseHandler.handleSuccess(apiInterface.getPost())
    }

}