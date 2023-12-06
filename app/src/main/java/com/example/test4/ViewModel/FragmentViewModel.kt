package com.example.test4.ViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.test4.Model.Person
import com.example.test4.Model.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FragmentViewModel(private val repository: Repository) :ViewModel(){

    private val _items = MutableStateFlow<List<Person>>(emptyList())
    val items: StateFlow<List<Person>> = _items.asStateFlow()

    private val originalItems = mutableListOf<Person>()

    fun updateItem(updatedItem: Person) {
        _items.value = _items.value.map { if (it.id == updatedItem.id) updatedItem else it }
        updateOriginalItemList(updatedItem)
    }

    fun addItem(item: Person) {
        _items.value = _items.value + item
        originalItems.add(item)
    }

    fun removeItem(itemId: Int) {
        _items.value = _items.value.filter { it.id != itemId }
        originalItems.removeAll { it.id == itemId }
    }

    fun filterItems(query: String) {
        if (query.isEmpty()) {
            _items.value = originalItems
        } else {
            _items.value = originalItems.filter { person ->
                // Replace 'name' with the field of 'Person' you want to filter by
                person.owner.contains(query, ignoreCase = true)
            }
        }
    }

    private fun updateOriginalItemList(updatedItem: Person) {
        val index = originalItems.indexOfFirst { it.id == updatedItem.id }
        if (index != -1) {
            originalItems[index] = updatedItem
        }
    }

    fun getPersons() {
        viewModelScope.launch {
            try {
                val persons = repository.getPersons()
                Log.d("tag1234","geting something with size ${persons.size}")
                persons.forEach{ it ->
                    addItem(it)
                }
            } catch (e: Exception) {
                // Handle errors
                Log.d("tag1234", "Error: ${e.message}")
            }
        }
    }
}


class YourViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FragmentViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FragmentViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}