package com.example.architecturewithcoroutine.framework

import com.example.architecturewithcoroutine.data.network.ApiUrls
import com.example.architecturewithcoroutine.ArchitectureApplication
import com.example.architecturewithcoroutine.Utils
import com.example.architecturewithcoroutine.data.models.Response
import com.example.architecturewithcoroutine.data.network.ResponseHandler
import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


object RetrofitService {


    val networkModuleDi = module {

        single {
            val REWRITE_CACHE_CONTROL_INTERCEPTOR = Interceptor { chain ->
                val originalResponse = chain.proceed(chain.request())
                if (Utils.isConnected(ArchitectureApplication.applicationContext())) {
                    val maxAge = 60
                    return@Interceptor originalResponse.newBuilder()
                        .header("Cache-Control", "public, max-age=$maxAge").build()
                } else {
                    val maxStale = 60 * 60 * 24 * 28
                    return@Interceptor originalResponse.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
                        .build()
                }
            }


            val okHttpClient: OkHttpClient =
                OkHttpClient.Builder()
                    .addInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
                    .addNetworkInterceptor(StethoInterceptor())
                    .build()



            Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(ApiUrls.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        factory {
            (get<Retrofit>()).create<MovieListApi>(
                MovieListApi::class.java
            )
        }
        single {
            ResponseHandler()
        }

    }


}

interface MovieListApi {
    //http://www.omdbapi.com/?s=inter&apikey=13b629a1&type=movie
    @GET("/")
    suspend fun searchMovie(
        @Query("apikey") apikey: String, @Query("s") searchString: String, @Query(
            "type"
        ) type: String
    ): Response
}