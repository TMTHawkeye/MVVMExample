package com.project.mvvmproject.Api

import com.project.mvvmproject.model.CatsFacts
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.http.GET

const val BASE_URL = "https://cat-fact.herokuapp.com/"
interface QuotesApi {
    @GET("facts")
    fun getQuotes():Call<List<CatsFacts>>
}