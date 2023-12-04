package com.example.test4

import android.graphics.drawable.GradientDrawable.Orientation
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test4.databinding.FragmentMessegesBinding
import kotlinx.coroutines.launch

class MessegesFragment :  BaseFragment<FragmentMessegesBinding>(FragmentMessegesBinding::inflate),ItemListener {

    private lateinit var myAdaper: MyRecyclerAdapter
    private val viewModel: FragmentViewModel by viewModels()


    companion object {

        @JvmStatic
        fun newInstance() =
            MessegesFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

    override fun setUp() {
        myAdaper = MyRecyclerAdapter(this)
        bindings()
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.items.collect { itemList ->
                myAdaper.submitList(itemList)
            }
        }
        viewModel.addItem(Person(1,"Giorgi","rogor xaar kacii",Message.FILE))
        viewModel.addItem(Person(2,"Jemali","gamo davliot",Message.FILE))
    }

    private fun bindings(){

        binding.apply {


            recycler.apply {
                setHasFixedSize(false)
                layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
                adapter = myAdaper
            }
        }
    }

    override fun initData() {

    }

    override fun removeItem(field: Person) {

    }

    override fun editItem(field: Person) {

    }
}