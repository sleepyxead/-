package com.example.orders.Utilities

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context?) : SQLiteOpenHelper(context, "SignLog.db", null, 1) {


    override fun onCreate(MyDatabase: SQLiteDatabase) {
        MyDatabase.execSQL("create Table users(login TEXT primary key, password TEXT)")
    }

    override fun onUpgrade(MyDB: SQLiteDatabase, i: Int, i1: Int) {
        MyDB.execSQL("drop Table if exists users")
    }

    fun insertData(Login: String?, password: String?): Boolean {
        val MyDatabase: SQLiteDatabase = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("login", Login)
        contentValues.put("password", password)
        val result = MyDatabase.insert("users", null, contentValues)
        return result != -1L
    }

    fun checkLogin(login: String): Boolean {
        val MyDatabase: SQLiteDatabase = this.writableDatabase
        val cursor = MyDatabase.rawQuery("Select * from users where login = ?", arrayOf(login))
        return cursor.count > 0
    }

    fun checkLoginPassword(login: String, password: String): Boolean {
        val MyDatabase: SQLiteDatabase = this.writableDatabase
        val cursor = MyDatabase.rawQuery(
            "Select * from users where login = ? and password = ?",
            arrayOf(login, password)
        )
        return cursor.count > 0
    }
}