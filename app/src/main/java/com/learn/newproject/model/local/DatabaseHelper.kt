package com.learn.newproject.model.local

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.learn.newproject.model.local.DataBaseConstant.COLUMN_AUTHOR
import com.learn.newproject.model.local.DataBaseConstant.COLUMN_CATEGORY
import com.learn.newproject.model.local.DataBaseConstant.COLUMN_DESCRIPTION
import com.learn.newproject.model.local.DataBaseConstant.COLUMN_IMAGE
import com.learn.newproject.model.local.DataBaseConstant.COLUMN_LANGUAGE
import com.learn.newproject.model.local.DataBaseConstant.COLUMN_NEW_ID
import com.learn.newproject.model.local.DataBaseConstant.COLUMN_PUBLISHED
import com.learn.newproject.model.local.DataBaseConstant.COLUMN_TITLE
import com.learn.newproject.model.local.DataBaseConstant.COLUMN_URL
import com.learn.newproject.model.local.DataBaseConstant.DATABASE_NAME
import com.learn.newproject.model.local.DataBaseConstant.NEW_TABLE_NAME

class DatabaseHelper (val context: Context)
    : SQLiteOpenHelper(context,DATABASE_NAME,null, 1) {
    lateinit var database: SQLiteDatabase


    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_NEW_TABLE)

        database = db!!


    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }


    companion object{
        val CREATE_NEW_TABLE ="""
            CREATE TABLE $NEW_TABLE_NAME(
            $COLUMN_NEW_ID TEXT ,
            $COLUMN_TITLE TEXT, 
            $COLUMN_PUBLISHED TEXT,
            $COLUMN_URL TEXT,
            $COLUMN_AUTHOR TEXT,
            $COLUMN_IMAGE TEXT,
            $COLUMN_DESCRIPTION TEXT,
            $COLUMN_LANGUAGE TEXT,
            $COLUMN_CATEGORY TEXT
            )
        """.trimIndent()




    }
}