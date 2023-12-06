package com.example.test4.ViewModel

import androidx.lifecycle.ViewModel
import com.example.test4.Model.Person
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class FragmentViewModel :ViewModel(){
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
}