package com.pipi.pipix.src.main.Fragment

import android.content.Intent
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.room.PrimaryKey
import com.pipi.pipix.R
import com.pipi.pipix.data.PureResult
import com.pipi.pipix.src.chart.ChartActivity
import kotlinx.coroutines.NonDisposableHandle
import kotlinx.coroutines.NonDisposableHandle.parent
import java.io.Serializable
import java.security.AccessController.getContext
import java.text.SimpleDateFormat

class ProfileRecyclerviewAdapter (val context: ProfileFragment) :  RecyclerView.Adapter<ProfileRecyclerviewAdapter.ViewHolder>() {

    private var userList = emptyList<PureResult>()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var more: ImageView? = null
        var dateTime: TextView? = null
        lateinit var data : PureResult

        
        init {
            // Define click listener for the ViewHolder's View.
            more = view!!.findViewById(R.id.item_more)
            dateTime = view!!.findViewById(R.id.item_time)

              view.setOnClickListener {
                  val intent = Intent(view.context, ChartActivity::class.java)
                  intent.putExtra("test",data)
                  view.getContext().startActivity(intent)
             }
        }
    }
    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int):ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.recyclerview_item, viewGroup, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return  userList.size
    }


    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element

        var currentItem = userList[position]
        val time = currentItem.date
        viewHolder.dateTime?.setText(time)
        viewHolder.data = userList[position]
    }



    fun setData(user : List<PureResult>){
        this.userList = user
        notifyDataSetChanged()
    }

}