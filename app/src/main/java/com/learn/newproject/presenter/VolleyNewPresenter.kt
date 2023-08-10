package com.learn.newproject.presenter

import android.util.Log
import com.android.volley.toolbox.ImageLoader
import com.android.volley.toolbox.NetworkImageView
import com.learn.newproject.model.ResponseCallBack
import com.learn.newproject.model.entity.New
import com.learn.newproject.model.remote.VolleyHandler

class VolleyNewPresenter(private val volleyHandler: VolleyHandler,
                         private val newView: MVPNew.NewView)
    :MVPNew.INewPresenter {
    override fun getNews() {
        volleyHandler.fetchNewsResponse(responseCallBack = object :ResponseCallBack{
            override fun success(new: Any) {
                new as List<New>
                newView.setResult(new)
            }

            override fun failure(error: String) {
                newView.setError(error)
            }

        })
    }

    override fun addNews(new:New) {
        Log.i("Volley","No add method for volley")
    }
    fun fetchImg(url: String,
                 imgThumbnail: NetworkImageView,
                 placeHolder: Int,
                 error: Int):ImageLoader {
        volleyHandler.imgLoader.get(url, ImageLoader.getImageListener(
            imgThumbnail,placeHolder,error
        ))

        imgThumbnail.setImageUrl(url,volleyHandler.imgLoader)
        return volleyHandler.imgLoader
    }
    fun getNewsByString(key:String) {
        volleyHandler.fetchNewsByStringResponse(
            key,
            responseCallBack = object :ResponseCallBack{
            override fun success(new: Any) {
                new as List<New>
                Log.i(TAG,"getNewsByString(key:String) success")
                newView.setResult(new)
            }

            override fun failure(error: String) {
                Log.i(TAG,"getNewsByString(key:String) failure")
                newView.setError(error)
            }

        })
    }

    companion object{
        const val TAG= "VolleyNewPresenter"
    }
}