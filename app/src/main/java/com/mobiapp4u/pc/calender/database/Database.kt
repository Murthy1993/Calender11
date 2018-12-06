package com.mobiapp4u.pc.calender.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase


class Database{
    var dBase:SQLiteDatabase? = null
    constructor(activity:Context){
        dBase = activity.openOrCreateDatabase("events_db",Context.MODE_PRIVATE,null)
        dBase!!.execSQL("create table if not exists event(_id integer primary key autoincrement, date varchar(50),day_month varchar(50),day_week varchar(100),year_month varchar(200),time varchar(50), description varchar(300))")
    }


}