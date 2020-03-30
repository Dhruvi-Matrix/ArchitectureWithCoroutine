package com.example.architecturewithcoroutine.view

import com.example.architecturewithcoroutine.data.models.Response
import com.example.architecturewithcoroutine.data.network.ApiUrls
import com.example.architecturewithcoroutine.data.network.ResponseHandler
import com.example.architecturewithcoroutine.data.network.ResponseStatus
import com.example.architecturewithcoroutine.framework.MovieListApi
import org.koin.core.KoinComponent
import org.koin.core.inject


class MovieRepository():KoinComponent {

    private val movieListApi: MovieListApi by inject()
    private val responseHandler: ResponseHandler by inject()

    suspend fun getMovies(): ResponseStatus<Response> {
        return responseHandler.handleSuccess(movieListApi.searchMovie(ApiUrls.apiToken, "ka", "movie"))
    }

}