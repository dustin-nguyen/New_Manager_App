package com.learn.newproject.model

import com.learn.newproject.model.entity.New

interface ResponseCallBack {
    fun success(obj: Any)
    fun failure(error: String)
}