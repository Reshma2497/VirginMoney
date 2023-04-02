package com.example.virginmoney.ui.rooms

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.virginmoney.R
import com.example.virginmoney.data.model.people.PeopleItemModel
import com.example.virginmoney.data.model.rooms.RoomsModel
import com.example.virginmoney.data.model.rooms.RoomsModelItemModel
import com.example.virginmoney.databinding.ItemRoomsBinding


class RoomsAdapter (var rooms: RoomsModel) :  RecyclerView.Adapter<RoomsAdapter.ViewHolder>() {

    var onItemClick: ((RoomsModelItemModel) -> Unit)? = null

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val binding= ItemRoomsBinding.bind(view)
        fun handleData(item: RoomsModelItemModel?, function: (RoomsModelItemModel) -> Unit?) {
            item?.let {
               binding.tvId.text= "Room id " + item.id
                binding.tvMaxOccupied.text= "Max Occupancy "+ item.maxOccupancy.toString()
                binding.cvCardview.setCardBackgroundColor(if(item.isOccupied?:false) Color.RED else Color.GREEN)



            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomsAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_rooms, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = rooms.size ?: 0

    override fun onBindViewHolder(holder: RoomsAdapter.ViewHolder, position: Int) {
        val resultModel = rooms.get(position)
        holder.handleData(resultModel) { em: RoomsModelItemModel ->
            onItemClick?.invoke(em)

        }
    }

    fun updateData(filteredRooms: RoomsModel) {
       rooms = filteredRooms
        notifyDataSetChanged()

    }
}