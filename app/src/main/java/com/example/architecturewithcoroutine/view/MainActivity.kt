package com.example.architecturewithcoroutine.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.architecturewithcoroutine.R
import com.example.architecturewithcoroutine.databinding.ActivityMainBinding
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private val viewModel: MainActivityViewModel by inject()
    private lateinit var postAdapter:PostAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var mainPresenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)


        mainPresenter = MainPresenter(viewModel,this)
        activityMainBinding.mainPresenter = mainPresenter

        activityMainBinding.button.setOnClickListener {
            mainPresenter.getPosts()
            mainPresenter.setPostListTrigger(true)
        }
        linearLayoutManager = LinearLayoutManager(this)
        activityMainBinding.recyclerView.layoutManager = linearLayoutManager
        activityMainBinding.recyclerView.isNestedScrollingEnabled = false
        postAdapter = PostAdapter(layoutInflater)
        activityMainBinding.recyclerView.adapter = postAdapter

    }
}
