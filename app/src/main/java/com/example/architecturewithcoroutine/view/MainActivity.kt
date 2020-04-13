package com.example.architecturewithcoroutine.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.architecturewithcoroutine.R
import com.example.architecturewithcoroutine.data.network.ResponseStatus
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private val viewModel: MainActivityViewModel by inject()
    private lateinit var postAdapter:PostAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        linearLayoutManager = LinearLayoutManager(this)
        recycler_view.layoutManager = linearLayoutManager
        recycler_view.isNestedScrollingEnabled = false
        postAdapter = PostAdapter()
        recycler_view.adapter = postAdapter


        button.setOnClickListener {
            viewModel.postListLiveData.observe(this, Observer { result ->
                when (result.status) {
                    ResponseStatus.Status.RUNNING -> progress_bar.visibility=View.VISIBLE
                    ResponseStatus.Status.SUCCESS -> {
                        progress_bar.visibility=View.GONE
                        result.data.let {
                            Log.d("MainActivity","The list is" + it?.size)
                            postAdapter.setList(it)
                            postAdapter.notifyDataSetChanged()
                        }
                    }
                    ResponseStatus.Status.FAILED -> {
                        progress_bar.visibility=View.GONE
                        Toast.makeText(this,result.message,Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }


    }
}
