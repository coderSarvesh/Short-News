package com.example.shortnews

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

//https://newsapi.org/v2/top-headlines?country=in&apiKey=API_KEY

const val BASE_URL = "https://newsapi.org/"
const val API_KEY = "13f4894346d248afbe461c0dda93f5f0"

interface NewsInterface {

    @GET("v2/top-headlines?apiKey=$API_KEY")
    fun getHeadlines(@Query("country")country: String,@Query("page")page: Int) : retrofit2.Call<News>

}

object NewsService{
    val newsInstance : NewsInterface
    init{
        val retrofit : Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        newsInstance=retrofit.create(NewsInterface::class.java)

    }

}