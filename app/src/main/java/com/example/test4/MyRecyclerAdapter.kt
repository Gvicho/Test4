package com.example.test4

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.test4.databinding.PersonMessegeItemBinding


class MyRecyclerAdapter(private val listener: ItemListener) : ListAdapter<Person, RecyclerView.ViewHolder>(
    DIFF_CALLBACK
) {
    companion object {

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Person>() {
            override fun areItemsTheSame(oldItem: Person, newItem: Person): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Person, newItem: Person): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class PersonMessageItem(private val binding: PersonMessegeItemBinding):RecyclerView.ViewHolder(binding.root){

        fun bind(person: Person){
            setInfo(person)
        }

        private fun setInfo(person: Person){
            binding.apply {
                tvName.text = person.name
                tvLastMessege.text = person.lastMessage

            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return PersonMessageItem(
            PersonMessegeItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val mushroom = getItem(position)
        if(holder is PersonMessageItem)holder.bind(mushroom)
    }
}

interface ItemListener {
    fun removeItem(field: Person)
    fun editItem(field: Person)
}