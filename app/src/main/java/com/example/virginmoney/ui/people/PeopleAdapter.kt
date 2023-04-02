package com.example.virginmoney.ui.people

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.virginmoney.R
import com.example.virginmoney.data.model.people.PeopleItemModel
import com.example.virginmoney.data.model.people.PeopleModel
import com.example.virginmoney.databinding.ItemPeopleBinding


//
class PeopleAdapter(var people: PeopleModel) :  RecyclerView.Adapter<PeopleAdapter.ViewHolder>() {

    var onItemClick: ((PeopleItemModel)-> Unit)?= null

    class ViewHolder (val view:View): RecyclerView.ViewHolder(view){
        val binding= ItemPeopleBinding.bind(view)
        fun handleData(item: PeopleItemModel?){
            item?.let {
                Glide.with(view).load(it.avatar).placeholder(R.drawable.ic_launcher_foreground)
                    .into(binding.ivImage)
                binding.tvName.text= item.lastName + " " + item.firstName
                binding.tvJobTitle.text=item.jobtitle

            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeopleAdapter.ViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.item_people, parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int= people.size?:0

    override fun onBindViewHolder(holder: PeopleAdapter.ViewHolder, position: Int) {
        holder.handleData(people?.get(position))
        holder.itemView.setOnClickListener{
            people?.get(position)?.let {
                onItemClick?.invoke(it)
            }
        }
    }

    // update adapter with the filter data
    fun updateData(filteredPeople: PeopleModel) {
        people = filteredPeople
        notifyDataSetChanged()
    }

}
