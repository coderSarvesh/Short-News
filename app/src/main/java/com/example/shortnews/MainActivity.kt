package com.example.shortnews

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Callback
import retrofit2.Response
import java.text.FieldPosition


class MainActivity : AppCompatActivity() {

    lateinit var adapter: NewsAdapter
    private var articles = mutableListOf<Article>()
    var pageNum = 1
    var totalResults = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        adapter = NewsAdapter(this@MainActivity,articles)
//        val myLM = LinearLayoutManager(this)
        rvNews.adapter = adapter
        rvNews.layoutManager = LinearLayoutManager((this@MainActivity), LinearLayoutManager.HORIZONTAL, false)

//        rvNews.layoutManager = LinearLayoutManager(this@MainActivity)
        getNews()
        if(totalResults > (rvNews.layoutManager as LinearLayoutManager).itemCount && (rvNews.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition() >= (rvNews.layoutManager as LinearLayoutManager).itemCount -5)
            {
            pageNum++
            getNews()
        }
    }

    private fun getNews() {
        val news:retrofit2.Call<News> = NewsService.newsInstance.getHeadlines("in",pageNum)
        news.enqueue(object : Callback<News>{
            override fun onResponse(call: retrofit2.Call<News>, response: Response<News>) {
                val news = response.body()
                if (news != null)
                {
                    Log.d("SARVESHCODE",news.toString())
                    totalResults=news.totalResults
                    articles.addAll(news.articles)
                    adapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: retrofit2.Call<News>, t: Throwable) {
                Log.d("SARVESHCODE","Error infetching News",t)
            }
        })
    }
}