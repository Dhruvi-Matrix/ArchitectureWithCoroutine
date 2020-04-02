package com.example.architecturewithcoroutine.framework

import com.example.architecturewithcoroutine.ArchitectureApplication
import com.example.architecturewithcoroutine.Utils
import com.example.architecturewithcoroutine.data.models.Post
import com.example.architecturewithcoroutine.data.network.ResponseHandler
import com.facebook.stetho.okhttp3.StethoInterceptor
import kotlinx.coroutines.Deferred
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
                .baseUrl("https://jsonplaceholder.typicode.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        factory {
            (get<Retrofit>()).create<ApiInterface>(
                ApiInterface::class.java
            )
        }
        single {
            ResponseHandler()
        }

    }


}

interface ApiInterface {
    @GET("/posts")
    suspend fun getPost() : List<Post>
}