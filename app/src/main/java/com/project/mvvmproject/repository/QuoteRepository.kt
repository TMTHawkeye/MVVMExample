package com.project.mvvmproject.repository

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.project.mvvmproject.Api.BASE_URL
import com.project.mvvmproject.Api.QuotesApi
import com.project.mvvmproject.model.CatsFacts
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class QuoteRepository(val application: Application) {
    val quotesList=MutableLiveData<List<CatsFacts>>()
    var responseResult=true


    fun showQuotes(){
        val retrofit=Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()

        val service=retrofit.create(QuotesApi::class.java)
        service.getQuotes().enqueue(object:Callback<List<CatsFacts>>{
            override fun onResponse(
                call: Call<List<CatsFacts>>,
                response: Response<List<CatsFacts>>
            ) {
                quotesList.value=response.body()
                Toast.makeText(application, "Response success!", Toast.LENGTH_SHORT).show()
                Log.d("tag", "onResponse:${Gson().toJson(response.body())}")
            }

            override fun onFailure(call: Call<List<CatsFacts>>, t: Throwable) {
                Toast.makeText(application, "Response failure!", Toast.LENGTH_SHORT).show()
                responseResult=false
            }
        })
    }


}