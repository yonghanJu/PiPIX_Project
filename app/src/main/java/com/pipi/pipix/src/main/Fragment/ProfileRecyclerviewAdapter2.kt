package com.pipi.pipix.src.main.Fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pipi.pipix.R
import com.pipi.pipix.data.PureResult
import kotlinx.coroutines.NonDisposableHandle
import kotlinx.coroutines.NonDisposableHandle.parent
import java.text.SimpleDateFormat

class ProfileRecyclerviewAdapter2 (val context: ProfileFragment) :  RecyclerView.Adapter<ProfileRecyclerviewAdapter2.ViewHolder>() {

    private var userList = emptyList<PureResult>()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var more: ImageView? = null
        var dateTime: TextView? = null


        init {
            // Define click listener for the ViewHolder's View.
            more = view!!.findViewById(R.id.item_more)
            dateTime = view!!.findViewById(R.id.item_time)

            /* 클릭 리스너 쓰고 싶다면, 예시
              uriBtn!!.setOnClickListener {
                 val intent = Intent(Intent.ACTION_VIEW, Uri.parse(text4))
                 view.getContext().startActivity(intent)
             }  */
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
        //val sdf = SimpleDateFormat("yyyy.mm.dd hh:mm")
        val currentItem = userList[position]
        val time = "${currentItem.year}.${currentItem.month}.${currentItem.date}.${currentItem.time}"
        viewHolder.dateTime?.setText(time)


    }



    fun setData(user : List<PureResult>){
        this.userList = user
        notifyDataSetChanged()
    }


}