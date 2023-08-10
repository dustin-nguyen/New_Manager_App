package com.learn.newproject.model.remote

import android.content.Context
import android.graphics.Bitmap
import android.util.LruCache
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.BasicNetwork
import com.android.volley.toolbox.DiskBasedCache
import com.android.volley.toolbox.HurlStack
import com.android.volley.toolbox.ImageLoader
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.learn.newproject.model.ResponseCallBack
import com.learn.newproject.model.remote.entity.NewsResponse

class VolleyHandler(val context:Context) {

    lateinit var imgLoader: ImageLoader
    init {
        initializeImgLoader(context)
    }
    fun fetchNewsResponse(responseCallBack: ResponseCallBack){
        val requestQueue = Volley.newRequestQueue(context)

        val stringRequest =object: StringRequest(
            Request.Method.GET,
            BASE_URL_ALL_NEW,
            {
                //sucess
                // type conversion
                val typeToken = object : TypeToken<NewsResponse>(){}
                val response = Gson().fromJson(it,typeToken)
                responseCallBack.success(response.news)

            },{
                // error
                responseCallBack.failure(it.toString())
            })
        {override fun getHeaders():MutableMap<String,String>{
            val header =HashMap<String,String>()
            header[KEY_AUTHORIZATION]= AUTHORIZATION
            return header
        }}

        requestQueue.add(stringRequest)
    }
    fun fetchNewsByStringResponse(key:String,responseCallBack: ResponseCallBack){
        val requestQueue = Volley.newRequestQueue(context)
        val params=HashMap<String,String>()
        params[KEY_KEYWORD]=key
        val stringRequest =object: StringRequest(
            Request.Method.GET,
            BASE_URL_SEARCH_NEW,
            {
                //sucess
                // type conversion
                val typeToken = object : TypeToken<NewsResponse>(){}
                val response = Gson().fromJson(it,typeToken)
                responseCallBack.success(response.news)

            },{
                // error
                responseCallBack.failure(it.toString())
            })
        {
            override fun getHeaders():MutableMap<String,String>{
                val header =HashMap<String,String>()
                header[KEY_AUTHORIZATION]= AUTHORIZATION
                return header
            }

            override fun getParams(): MutableMap<String, String>? {
                return params
            }

        }

        requestQueue.add(stringRequest)
    }

    fun initializeImgLoader(context: Context){
        RequestQueue(
            DiskBasedCache(
                context.cacheDir, MAX_CACHE_SIZE_IN_BYTE),
            BasicNetwork(HurlStack())
        ).apply{
            start()
            imgLoader = ImageLoader(this,
                object : ImageLoader.ImageCache{
                    private val internal_cache= LruCache<String, Bitmap>(CACHE_SIZE)
                    override fun getBitmap(url: String?): Bitmap? {
                        return internal_cache[url]
                    }

                    override fun putBitmap(url: String?, bitmap: Bitmap?) {
                        internal_cache.put(url,bitmap)
                    }
                })
        }}

    companion object{
        const val BASE_URL_SEARCH_NEW ="https://api.currentsapi.services/v1/search"
        const val BASE_URL_ALL_NEW ="https://api.currentsapi.services/v1/latest-news"
        const val AUTHORIZATION="WMARt5YoGmUau-vnXgml5oXpR87etkBEgDSuRJv1xqo1Sln5"
        const val KEY_AUTHORIZATION="Authorization"
        const val CACHE_SIZE=40
        const val MAX_CACHE_SIZE_IN_BYTE= 1000000000
        const val KEY_KEYWORD="keywords"
    }
}