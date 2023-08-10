package com.learn.newproject.model.local.Dao

import android.content.ContentValues
import com.learn.newproject.model.ResponseCallBack
import com.learn.newproject.model.entity.New
import com.learn.newproject.model.local.DataBaseConstant.COLUMN_AUTHOR
import com.learn.newproject.model.local.DataBaseConstant.COLUMN_CATEGORY
import com.learn.newproject.model.local.DataBaseConstant.COLUMN_DESCRIPTION
import com.learn.newproject.model.local.DataBaseConstant.COLUMN_IMAGE
import com.learn.newproject.model.local.DataBaseConstant.COLUMN_LANGUAGE
import com.learn.newproject.model.local.DataBaseConstant.COLUMN_NEW_ID
import com.learn.newproject.model.local.DataBaseConstant.COLUMN_PUBLISHED
import com.learn.newproject.model.local.DataBaseConstant.COLUMN_TITLE
import com.learn.newproject.model.local.DataBaseConstant.COLUMN_URL
import com.learn.newproject.model.local.DataBaseConstant.NEW_TABLE_NAME
import com.learn.newproject.model.local.DatabaseHelper


class NewDao(dbHelper: DatabaseHelper) {
    private var db: DatabaseHelper = dbHelper

    fun addNew(new: New,responseCallBack: ResponseCallBack){
        val contentValue = ContentValues()
        contentValue.apply {
            put(COLUMN_TITLE, new.title)
            put(COLUMN_PUBLISHED, new.published)
            put(COLUMN_NEW_ID,new.id)
            put(COLUMN_AUTHOR, new.author)
            put(COLUMN_URL, new.url)
            put(COLUMN_CATEGORY,new.category.toString())
            put(COLUMN_IMAGE,new.image)
            put(COLUMN_DESCRIPTION,new.description)
            put(COLUMN_LANGUAGE,new.language)
        }
        // return -1 if not success
        val result=db.writableDatabase.insert(NEW_TABLE_NAME, null, contentValue)
        if(result==-1L)
            responseCallBack.failure(result.toString())
        else
            responseCallBack.success(result)
    }

    fun getAllNew(responseCallBack: ResponseCallBack){
        val newList = ArrayList<New>()

        try {
            val cursor =
                db.readableDatabase.query(NEW_TABLE_NAME, null, null, null, null, null, null, null)

            while (cursor.moveToNext()) {
                val id = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NEW_ID))
                val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
                val published = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PUBLISHED))
                val url = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_URL))
                val author = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_AUTHOR))
                val description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION))
                val category = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY)).split(",")
                val language = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LANGUAGE))
                val img = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGE))


                val new = New(
                    id = id,
                    title = title,
                    published = published,
                    url = url,
                    author = author,
                    description = description,
                    language=language,
                    image=img,
                    category = category
                )
                newList.add(new)
            }
            cursor.close()
            responseCallBack.success(newList)
        }catch (exception :Exception){
            responseCallBack.failure(exception.toString())
        }
    }
    fun deleteNew(new: New,responseCallBack: ResponseCallBack){
        val whereClause ="$COLUMN_NEW_ID = ?"
        val whereArgs = arrayOf(new.id)
        val deletedRow =
            db.readableDatabase.delete(NEW_TABLE_NAME,whereClause,whereArgs)

        if(deletedRow>0)
            responseCallBack.success(deletedRow)
        else
            responseCallBack.failure("Delete failed")
    }
}