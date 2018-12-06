package com.mobiapp4u.pc.calender.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.mobiapp4u.pc.calender.ItemLongClickListener
import com.mobiapp4u.pc.calender.R
import com.mobiapp4u.pc.calender.beans.CalenderModel
import com.mobiapp4u.pc.calender.database.Database


class CalenderAdaptor(
    var context:Context,
    var data: MutableList<CalenderModel>
): RecyclerView.Adapter<CalenderAdaptor.CalViewHolder>(){



    override fun onCreateViewHolder(viewGroup: ViewGroup, pos: Int): CalViewHolder {
        var inflater = LayoutInflater.from(context)
        var itemV = inflater.inflate(R.layout.events_item,viewGroup,false)
        return CalViewHolder(itemV)

    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(vh: CalViewHolder, pos: Int) {
        vh.day.text = data[pos].day_week
        vh.dateNum.text = data[pos].day_month
        vh.time.text = data[pos].time
        vh.event.text = data[pos].description
        vh.date.text = data[pos].date
        vh.setItemLongClickListner(object : ItemLongClickListener {
            override fun onItemLongClick(v: View, pos: Int) {
                var actvity = Database(context)
                var alert = AlertDialog.Builder(context)
                alert.setTitle("Do you want to delete event??")
                alert.setPositiveButton("Yes",object : DialogInterface.OnClickListener{
                    override fun onClick(dialog: DialogInterface?, which: Int) {

                        var status =  actvity!!.dBase!!.delete("event","date=?", arrayOf(data[pos].date))
                        if(status == 0){
                            Toast.makeText( context,
                                "Data Deletion is Failed",
                                Toast.LENGTH_LONG).show()

                        }else{
                            Toast.makeText(context,
                                "Data Deletion is Success",
                                Toast.LENGTH_LONG).show()
                        }

                    }

                })
                alert.setNegativeButton("No",object :DialogInterface.OnClickListener{
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        dialog!!.dismiss()
                    }
                })
                alert.show()
            }

        })


    }

    class  CalViewHolder(itemView: View):RecyclerView.ViewHolder(itemView),View.OnLongClickListener{
        lateinit var itemLongClickListener:ItemLongClickListener

        init {
            itemView.setOnLongClickListener(this)
        }

        var day:TextView = itemView.findViewById(R.id.day_month)
        var dateNum:TextView = itemView.findViewById(R.id.date_num)
        var time:TextView = itemView.findViewById(R.id.time)
        var event:TextView = itemView.findViewById(R.id.event_desc)
        var date:TextView = itemView.findViewById(R.id.dateOfItem)

        fun setItemLongClickListner(ic:ItemLongClickListener){
            this.itemLongClickListener  = ic
        }
        override fun onLongClick(v: View?): Boolean {
            this.itemLongClickListener.onItemLongClick(v!!,adapterPosition)
            return false
        }


    }
}