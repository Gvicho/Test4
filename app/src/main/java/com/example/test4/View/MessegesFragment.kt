package com.example.test4.View

import android.os.Bundle
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test4.Model.Person
import com.example.test4.Model.Repository
import com.example.test4.ViewModel.FragmentViewModel
import com.example.test4.ViewModel.YourViewModelFactory
import com.example.test4.databinding.FragmentMessegesBinding
import kotlinx.coroutines.launch

class MessegesFragment :  BaseFragment<FragmentMessegesBinding>(FragmentMessegesBinding::inflate),
    ItemListener {

    private lateinit var myAdaper: MyRecyclerAdapter
    private val viewModel: FragmentViewModel by viewModels {
        YourViewModelFactory(Repository())
    }


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

    override fun initData(savedInstanceState: Bundle?) {
        if(savedInstanceState == null ){
            // so that we don't get new data on each configuration changes.
            // Fragments bundle is null only when it is first created
            viewModel.getPersons()
        }
    }

    override fun removeItem(field: Person) {

    }

    override fun editItem(field: Person) {

    }
}