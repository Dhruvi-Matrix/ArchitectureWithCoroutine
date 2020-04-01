package com.example.architecturewithcoroutine.data.network

 class ResponseStatus<out T>(val status: Status, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T?): ResponseStatus<T> {
            return ResponseStatus(
                Status.SUCCESS,
                data,
                null
            )
        }

        fun <T> error(msg: String, data: T?): ResponseStatus<T> {
            return ResponseStatus(
                Status.FAILED,
                data,
                msg
            )
        }

        fun <T> loading(data: T?): ResponseStatus<T> {
            return ResponseStatus(
                Status.RUNNING,
                data,
                null
            )
            
        }
    }
     enum class Status {
         RUNNING,
         SUCCESS,
         FAILED
     }
}

