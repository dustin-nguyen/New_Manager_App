package com.learn.newproject.presenter

import android.util.Log
import com.learn.newproject.model.ResponseCallBack
import com.learn.newproject.model.entity.New
import com.learn.newproject.model.local.Dao.NewDao
import com.learn.newproject.model.local.DatabaseHelper
import com.learn.newproject.model.remote.VolleyHandler

class LocalNewPresenter(private val dbHelper: DatabaseHelper,
                        private val newView: MVPNew.NewView)
    :MVPNew.INewPresenter {
    private var newDao: NewDao= NewDao(dbHelper)

    override fun getNews() {
        newDao.getAllNew(object:ResponseCallBack{
            override fun success(listOfNew: Any) {
                listOfNew as List<New>
                newView.setResult(listOfNew)
            }

            override fun failure(error: String) {
                Log.i("LocalNewPresenter",error)
                newView.setError(error)
            }

        })
    }

    override fun addNews(new: New) {
        newDao.addNew(new,object:ResponseCallBack{
            override fun success(obj: Any) {
                newView.makeToast("Saved New Success")
            }

            override fun failure(error: String) {
                Log.i("LocalNewPresenter",error)
                newView.setError(error)
            }

        })
    }
    fun deleteNews(new:New){
        newDao.deleteNew(new, object :ResponseCallBack{
            override fun success(obj: Any) {
                getNews()
            }

            override fun failure(error: String) {
                Log.i("LocalNewPresenter",error)
                newView.setError(error)
            }

        })
    }
}