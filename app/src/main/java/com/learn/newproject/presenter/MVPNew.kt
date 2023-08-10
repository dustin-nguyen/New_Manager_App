package com.learn.newproject.presenter

import com.learn.newproject.model.entity.New

interface MVPNew {
    interface INewPresenter{
        fun getNews()
        fun addNews(new:New)
    }
    interface NewView{
        fun setResult(listOfNewsResponse: List<New>)
        fun setError(error: String)
        fun makeToast(message: String)
    }
}