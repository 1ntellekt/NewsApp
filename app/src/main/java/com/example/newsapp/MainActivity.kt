package com.example.newsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.newsapp.adapters.Adapter
import com.example.newsapp.data.api.ApiClient
import com.example.newsapp.databinding.ActivityMainBinding
import com.example.newsapp.models.Articles
import com.example.newsapp.models.Headlines
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: Adapter
    private lateinit var listArticles: MutableList<Articles>
    val API_KEY = "d09483672c774af1b551dc286a42aaff"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = Adapter()
        listArticles = mutableListOf()

        binding.apply {
            recyclerView.setHasFixedSize(true)
            recyclerView.adapter = adapter

            swipeRefreshLayout.setOnRefreshListener {
                retrieveJson(getCountry(),API_KEY)
            }
        }

        retrieveJson(getCountry(),API_KEY)
    }

    override fun onStart() {
        super.onStart()
        retrieveJson(getCountry(),API_KEY)
    }

    fun retrieveJson(country:String, apiKey:String){
        binding.swipeRefreshLayout.isRefreshing = true
        val call = ApiClient.getInstance().getApi().getHeadlines(country, apiKey)
        call.enqueue(object : Callback<Headlines>{

            override fun onResponse(call: Call<Headlines>, response: Response<Headlines>) {
                if (response.isSuccessful && response.body()!!.articles != null){
                    binding.swipeRefreshLayout.isRefreshing = false
                    listArticles.clear()
                    listArticles.addAll(response.body()!!.articles!!)
                    adapter.setData(listArticles)
                }
            }

            override fun onFailure(call: Call<Headlines>, t: Throwable) {
                binding.swipeRefreshLayout.isRefreshing = false
                Toast.makeText(this@MainActivity,"${t.message.toString()}",Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun getCountry():String{
        return Locale.getDefault().country.lowercase(Locale.getDefault())
    }

}