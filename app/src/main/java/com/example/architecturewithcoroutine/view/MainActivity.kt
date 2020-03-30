package com.example.architecturewithcoroutine.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.architecturewithcoroutine.R
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private val viewModel: MainActivityViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {
            progress_bar.visibility=View.VISIBLE
            viewModel.fetchPosts()
        }

        viewModel.observableMovieList.observe(this, Observer {
            if(it.isNotEmpty()){
                progress_bar.visibility=View.GONE

                Toast.makeText(this,"You have got response size:" +it.size,Toast.LENGTH_SHORT).show()
            }
        })
    }
}
