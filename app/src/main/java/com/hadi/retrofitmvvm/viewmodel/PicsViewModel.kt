package com.hadi.retrofitmvvm.viewmodel

import android.app.Application
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hadi.retrofitmvvm.R
import com.hadi.retrofitmvvm.app.MyApplication
import com.hadi.retrofitmvvm.repository.AppRepository
import com.hadi.retrofitmvvm.util.Utils.hasInternetConnection
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import java.io.IOException
import java.io.InputStream
import java.time.Duration

class PicsViewModel(
    app: Application,
    private val appRepository: AppRepository
) : AndroidViewModel(app) {

    val imageByteData: MutableLiveData<InputStream> = MutableLiveData()
    val strLatency: MutableLiveData<String> = MutableLiveData()
    var before: Long = 0

    init {
        getImage()
    }

    fun getImage() = viewModelScope.launch {
        fetchImage()
    }


    private suspend fun fetchImage() {
        try {
            if (hasInternetConnection(getApplication<MyApplication>())) {
                before = System.nanoTime()
                val response = appRepository.getImage()
                handleImageResponse(response)
            } else {
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> Log.e("EEEE", getApplication<MyApplication>().getString(
                    R.string.network_failure
                ))
                else ->
                    Log.e("EEEE", getApplication<MyApplication>().getString(
                        R.string.conversion_error
                    ))

            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun handleImageResponse(response: ResponseBody) {
        var latency = Duration.ofNanos(System.nanoTime() - before)
        strLatency.postValue(latency.toMillis().toString() +"ms")
        Log.d("latency: ", latency.toMillis().toString() +"ms")
        imageByteData.postValue(response.byteStream())
    }


}