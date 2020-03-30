package com.example.architecturewithcoroutine.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.architecturewithcoroutine.R
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private val viewModel: MainActivityViewModel by inject()
    private lateinit var postAdapter:PostAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {
            progress_bar.visibility=View.VISIBLE
            viewModel.fetchPosts()
        }
        linearLayoutManager = LinearLayoutManager(this)
        recycler_view.layoutManager = linearLayoutManager
        recycler_view.isNestedScrollingEnabled = false
        postAdapter = PostAdapter()
        recycler_view.adapter = postAdapter
        viewModel.observableMovieList.observe(this, Observer {
            if(it.isNotEmpty()){
                progress_bar.visibility=View.GONE
                postAdapter.setList(it)
                postAdapter.notifyDataSetChanged()
                Toast.makeText(this,"You have got response size:" +it.size,Toast.LENGTH_SHORT).show()
            }
        })
    }
}
