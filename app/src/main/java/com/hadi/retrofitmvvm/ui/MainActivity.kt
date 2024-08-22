package com.hadi.retrofitmvvm.ui

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.hadi.retrofitmvvm.R
import com.hadi.retrofitmvvm.adapter.PicsAdapter
import com.hadi.retrofitmvvm.repository.AppRepository
import com.hadi.retrofitmvvm.util.*
import com.hadi.retrofitmvvm.viewmodel.PicsViewModel
import com.hadi.retrofitmvvm.viewmodel.ViewModelProviderFactory
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: PicsViewModel
    lateinit var picsAdapter: PicsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }

    private fun init() {
//        rvPics.setHasFixedSize(true)
//        rvPics.layoutManager = LinearLayoutManager(this)
//        picsAdapter = PicsAdapter()
        setupViewModel()
    }

    private fun setupViewModel() {
        val repository = AppRepository()
        val factory = ViewModelProviderFactory(application, repository)
        viewModel = ViewModelProvider(this, factory).get(PicsViewModel::class.java)
        viewModel.imageByteData.observe(this, Observer {
                response ->
            val bitmap = BitmapFactory.decodeStream(response)
            iv.setImageBitmap(bitmap)
        })
        viewModel.strLatency.observe(this, Observer {
                latency ->
            tv.setText(latency)
        })
        btn_refresh.setOnClickListener {
            viewModel.getImage()
        }
//        getPictures()
    }

//    private fun getPictures() {
//        viewModel.picsData.observe(this, Observer { response ->
//            when (response) {
//                is Resource.Success -> {
//                    hideProgressBar()
//                    response.data?.let { picsResponse ->
//                        picsAdapter.differ.submitList(picsResponse)
//                        rvPics.adapter = picsAdapter
//                    }
//                }
//
//                is Resource.Error -> {
//                    hideProgressBar()
//                    response.message?.let { message ->
//                        rootLayout.errorSnack(message,Snackbar.LENGTH_LONG)
//                    }
//
//                }
//
//                is Resource.Loading -> {
//                    showProgressBar()
//                }
//            }
//        })
//    }
//
//    private fun hideProgressBar() {
//        progress.visibility = View.GONE
//    }
//
//    private fun showProgressBar() {
//        progress.visibility = View.VISIBLE
//    }


    fun onProgressClick(view: View) {
        //Preventing Click during loading
    }
}