package com.example.test4.View

import android.os.Bundle
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test4.Model.Person
import com.example.test4.ViewModel.FragmentViewModel
import com.example.test4.databinding.FragmentMessegesBinding
import kotlinx.coroutines.launch

class MessegesFragment :  BaseFragment<FragmentMessegesBinding>(FragmentMessegesBinding::inflate),
    ItemListener {

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
        viewModel.addItem(Person(1,"https://www.alia.ge/wp-content/uploads/2022/09/grisha.jpg","Giorgi","rogor xaar kacii","12:15AM",0,false, "file"))
        viewModel.addItem(Person(2,null,"Goga","sa","12:15AM",3,false, "voice"))
        viewModel.addItem(Person(3,null,"Makho","rogor xaar kacii","12:15AM",0,true, "text"))
    }

    private fun bindings(){

        binding.apply {


            recycler.apply {
                setHasFixedSize(false)
                layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
                adapter = myAdaper
            }

            searchEdtTxt.doOnTextChanged { text, start, before, count ->
                viewModel.filterItems(text.toString())
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