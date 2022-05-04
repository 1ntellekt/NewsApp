package com.example.newsapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.R
import com.example.newsapp.models.Articles
import org.ocpsoft.prettytime.PrettyTime
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class Adapter: RecyclerView.Adapter<Adapter.NewsHolder>() {

    val listArticles = mutableListOf<Articles>()

    fun setData(list: List<Articles>){
        listArticles.clear()
        listArticles.addAll(list)
        notifyDataSetChanged()
    }

     class NewsHolder(itemView: View) :RecyclerView.ViewHolder(itemView) {
        val tvTitle:TextView = itemView.findViewById(R.id.tvTitle)
        val tvSource:TextView = itemView.findViewById(R.id.tvSource)
        val tvDate:TextView = itemView.findViewById(R.id.tvDate)
        val img:ImageView = itemView.findViewById(R.id.img)
        val cardView:CardView = itemView.findViewById(R.id.cardView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsHolder {
        return NewsHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_news,parent,false))
    }

    override fun onBindViewHolder(holder: NewsHolder, position: Int) {
        val article = listArticles[position]
        holder.apply {
            tvDate.text = "\u2022"+dateTime(article.publishedAt!!)
            tvSource.text = article.source!!.name
            tvTitle.text = article.title
            Glide.with(img.context).load(article.urlToImage).into(img)
        }
    }

    override fun getItemCount(): Int {
        return listArticles.size
    }

    fun dateTime(t:String):String?{
        val prettyTime = PrettyTime(Locale(getCountry()))
        var time:String? = null
        try {
            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:", Locale.ENGLISH)
            val date = simpleDateFormat.parse(t)
            time = prettyTime.format(date)
        } catch (e:ParseException){
            e.printStackTrace()
        }
        return time
    }

    fun getCountry():String{
        return Locale.getDefault().country.lowercase(Locale.getDefault())
    }


}