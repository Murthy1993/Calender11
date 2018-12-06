package com.mobiapp4u.pc.calender

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.ContentValues
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import com.mobiapp4u.pc.calender.database.Database
import kotlinx.android.synthetic.main.activity_save_event.*
import java.text.SimpleDateFormat
import java.util.*

class SaveEvent : AppCompatActivity() {

    var dayOfWeek:String? = null
    var dayMonth:String? = null
    var yearOfMonth:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_save_event)
        supportActionBar!!.title = "New Event"

        imageView_date_picker.setOnClickListener {
            var listener = object : DatePickerDialog.OnDateSetListener {
                override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
                    editText_date.setText("$p3 - ${p2+1} - $p1")
                    val simpledateformat = SimpleDateFormat("EEE")
                    val date = Date(p1, p2, p3 - 1)
                    dayMonth = "${p3}"
                    dayOfWeek = simpledateformat.format(date)
                    yearOfMonth = "$p1"
                }
            }
            var cal = Calendar.getInstance()
            var year = cal.get(Calendar.YEAR)
            var month = cal.get(Calendar.MONTH)
            var day = cal.get(Calendar.DAY_OF_MONTH)
            // context,OnDateSetListener,year,month,day
            var dpd = DatePickerDialog(this@SaveEvent, listener,
                year,month,day)
            dpd.show()
        }

        imageView_time_picker.setOnClickListener {
            val cal = Calendar.getInstance()
            val listener = object: TimePickerDialog.OnTimeSetListener{
                override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
                    cal.set(Calendar.HOUR_OF_DAY,hourOfDay)
                    cal.set(Calendar.MINUTE,minute)
                    val time =     SimpleDateFormat("HH:mm a").format(cal.time)
                    editText_time.setText(time)
                }

            }
            val timePkr = TimePickerDialog(this@SaveEvent, listener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }

        //save event in to table
        button_save.setOnClickListener {
            var cv = ContentValues()
            cv.put("date",editText_date.text.toString())
            cv.put("day_month",dayMonth)
            cv.put("year_month",yearOfMonth)
            cv.put("day_week",dayOfWeek)
            cv.put("time",editText_time.text.toString())
            cv.put("description",editText_event_desc.text.toString())
            var actvity = Database(this)
            var status = actvity.dBase!!.insert("event",null, cv)
            if(status == -1L)
            {
                Toast.makeText(this@SaveEvent, "Save Event  is Failed", Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(this@SaveEvent, "Data Insertion is Success", Toast.LENGTH_LONG).show()
                editText_date.setText(""); editText_time.setText("");editText_event_desc.setText("")
            }
        }

    }
}
