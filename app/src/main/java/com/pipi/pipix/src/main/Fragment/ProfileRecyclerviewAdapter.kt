package com.pipi.pipix.src.main.Fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pipi.pipix.R


class ProfileRecyclerviewAdapter(val context: ProfileFragment, val result: List<String>) :
    RecyclerView.Adapter<ProfileRecyclerviewAdapter.ViewHolder>() {

    //val dec = DecimalFormat("#,###")

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     *
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var more: ImageView? = null
        var time: TextView? = null


        init {
            // Define click listener for the ViewHolder's View.
            more = view!!.findViewById(R.id.item_more)
            time = view!!.findViewById(R.id.item_time)



           /* 클릭 리스너 쓰고 싶다면, 예시
             uriBtn!!.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(text4))
                view.getContext().startActivity(intent)
            } */




        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.recyclerview_item, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {


        // Get element from your dataset at this position and replace the
        // contents of the view with that element


        viewHolder.time?.setText(result[position])


    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = result.size

}



