package com.example.architecturewithcoroutine.view

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.architecturewithcoroutine.data.models.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject

class MainActivityViewModel : ViewModel(),KoinComponent {

    private val repository : MovieRepository by inject()
    private val job = SupervisorJob()
    private val coroutineContext = Dispatchers.IO + job


    val observableMovieList: MutableLiveData<List<Movie>> = MutableLiveData()

    fun fetchMovies(){
        viewModelScope.launch(coroutineContext) {
            val  response = repository.getMovies()
            Log.d("response is" , response.data.toString())
//            observableMovieList.postValue(response.data?.movies as List<Movie>)
        }

    }
}