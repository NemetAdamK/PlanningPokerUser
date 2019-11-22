package com.example.planningpokeruser

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.ArrayList

class SummaryAdapter(private val mDataList: ArrayList<VotedUsers>) : RecyclerView.Adapter<SummaryAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.votersummary_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        //holder.info_text.text = mDataList[position]
        holder.voterName.text = mDataList[position].name
        holder.voterPoint.text = mDataList[position].vote
    }


    override fun getItemCount(): Int {
        return mDataList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var voterName: TextView
        internal var voterPoint : TextView

        init {
            voterName = itemView.findViewById<View>(R.id.voterName) as TextView
            voterPoint = itemView.findViewById<View>(R.id.votepoint) as TextView
        }
    }
}