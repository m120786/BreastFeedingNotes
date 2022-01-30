package com.vb.breastfeedingnotes.database.weight

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class WeightViewModel @Inject constructor(private val weightService: WeightService): ViewModel() {

    val weightSelectedDate = MutableStateFlow<LocalDate>(LocalDate.now())

    val allWeightData by lazy { weightService.getAllWeight() }

    fun insertWeight(weight: Weight) {
        viewModelScope.launch {
            weightService.insert(weight)
        }
    }

    fun deleteWeight(weight: Weight) {
        viewModelScope.launch {
            weightService.delete(weight)
        }
    }



}