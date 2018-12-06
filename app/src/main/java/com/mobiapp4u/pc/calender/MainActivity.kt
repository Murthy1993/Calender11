package com.mobiapp4u.pc.calender

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.support.v7.app.AppCompatActivity;

import com.mobiapp4u.pc.calender.adapter.CalenderAdaptor
import com.mobiapp4u.pc.calender.beans.CalenderModel
import com.mobiapp4u.pc.calender.database.Database
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {
    lateinit var dt1:String
   lateinit var dBase:SQLiteDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        loadEvents()

        swipeRefreshLayout.setOnRefreshListener {
            loadEvents()
        }

        calendarView.setOnDateChangedListener(object: OnDateSelectedListener{
            override fun onDateSelected(p0: MaterialCalendarView, p1: CalendarDay, p2: Boolean) {

                val dt = ""+p1.day +" - "+p1.month+" - "+p1.year
                dt1 = dt.trim()

//                Toast.makeText(this@MainActivity,"Selected date is: $dt1",Toast.LENGTH_SHORT).show()
                var actvity = Database(this@MainActivity)
                var c =  actvity.dBase!!.query("event",null,"date=?", arrayOf(dt1),null,null,null)

                var calModel1 = mutableListOf<CalenderModel>()

                while (c.moveToNext()){
                    val date = c.getString(c.getColumnIndex("date"))
                    val day_month =   c.getString(c.getColumnIndex("day_month"))
                    val day_week =   c.getString(c.getColumnIndex("day_week"))
                    val year_month =   c.getString(c.getColumnIndex("year_month"))
                    val time =   c.getString(c.getColumnIndex("time"))
                    val description =   c.getString(c.getColumnIndex("description"))

                    calModel1.add(CalenderModel(date,day_month,day_week,year_month,time,description))

                }
                recview.setHasFixedSize(true)
                var cAdapter = CalenderAdaptor(this@MainActivity,calModel1)
                cAdapter.notifyDataSetChanged()
                recview.adapter = cAdapter
            }

        })


        fab.setOnClickListener { view ->
            val intent = Intent(this@MainActivity,SaveEvent::class.java)
            startActivity(intent)

        }
    }

    private fun loadEvents() {
        swipeRefreshLayout.isRefreshing = false
        var calModel = mutableListOf<CalenderModel>()
        var actvity = Database(this@MainActivity)
        var c =  actvity.dBase!!.query("event",null,null, null,
            null,null,"day_month,year_month")

        while (c.moveToNext()){
            val date = c.getString(c.getColumnIndex("date"))
            val day_month =   c.getString(c.getColumnIndex("day_month"))
            val day_week =   c.getString(c.getColumnIndex("day_week"))
            val year_month =   c.getString(c.getColumnIndex("year_month"))
            val time =   c.getString(c.getColumnIndex("time"))
            val description =   c.getString(c.getColumnIndex("description"))

            calModel.add(CalenderModel(date,day_month,day_week,year_month,time,description))

        }
        recview.setHasFixedSize(true)
        var cAdapter = CalenderAdaptor(this@MainActivity,calModel)
        cAdapter.notifyDataSetChanged()
        recview.adapter = cAdapter
    }


}
