package com.example.counter.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CounterViewModel : ViewModel()  {

    private val _counter = MutableStateFlow(0) // Private mutable state


    val counter : StateFlow<Int> = _counter // Public read-only state

    fun increment(){
        _counter.value++ // Modify state
    }

    fun decrement(){
        _counter.value--
    }

    fun reset(){
        _counter.value = 0
    }



}