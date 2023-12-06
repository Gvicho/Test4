package com.example.test4.View

import android.graphics.Color
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.test4.Model.Person
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

                tvName.text = person.owner
                tvLastMessage.text = person.getTextForLastMessageType()

                if(person.hasImage){ // fitch person image by glide
                    Glide.with(itemView.context)
                        .load(person.getImageUrl())
                        .into(binding.userImage)
                }

                if(!person.isLastMessageText()){ //setting icon for last message type -> (text, voice, file )
                    binding.msgType.visibility = View.VISIBLE
                    val image = person.getImageForLastMessageType()
                    val drawable = ContextCompat.getDrawable(itemView.context,image )
                    binding.msgType.setImageDrawable(drawable)
                }

                binding.tvLastTimeAmPm.text = person.last_active

                if(person.is_typing){
                    binding.isTyping.visibility = View.VISIBLE
                }

                if(person.hasUnreadMessages){
                    binding.missedMessages.visibility = View.VISIBLE
                    binding.missedMessages.text = person.unread_messages.toString()

                    binding.tvLastMessage.setTextColor(Color.WHITE)
                    binding.tvLastMessage.setTypeface(null,Typeface.BOLD)
                }

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