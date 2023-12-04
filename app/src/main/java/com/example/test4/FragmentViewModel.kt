package com.example.test4

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class FragmentViewModel :ViewModel(){
    private val _items = MutableStateFlow<List<Person>>(emptyList())
    val items: StateFlow<List<Person>> = _items.asStateFlow()

    fun updateItem(updatedItem: Person) {
        _items.value = _items.value.map { if (it.id == updatedItem.id) updatedItem else it }
    }

    fun addItem(item: Person) {
        _items.value = _items.value + item
    }

    fun removeItem(itemId: Int) {
        _items.value = _items.value.filter { it.id != itemId }
    }
}