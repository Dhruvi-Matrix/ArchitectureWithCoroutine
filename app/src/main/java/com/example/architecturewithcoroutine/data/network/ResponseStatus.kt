package com.example.architecturewithcoroutine.data.network

data class ResponseStatus<out T>(val status: Status, val data: T?, val message: String?) {

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    companion object {
        fun <T> success(data: T): ResponseStatus<T> {
            return ResponseStatus(Status.SUCCESS, data, null)
        }

        fun <T> error(message: String, data: T? = null): ResponseStatus<T> {
            return ResponseStatus(Status.ERROR, data, message)
        }

        fun <T> loading(data: T? = null): ResponseStatus<T> {
            return ResponseStatus(Status.LOADING, data, null)
        }
    }
}

