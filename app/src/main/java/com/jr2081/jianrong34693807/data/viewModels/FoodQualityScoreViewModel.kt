package com.jr2081.jianrong34693807.data.viewModels

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.jr2081.jianrong34693807.data.patient.PatientRepository
import kotlinx.coroutines.launch


class FoodQualityScoreViewModel(context: Context) : ViewModel() {

    private val patientRepository = PatientRepository(context)
    var userTotalScore by mutableDoubleStateOf(0.0)

    var userName by mutableStateOf("")

    fun updateUserName(patientId: Int) {
        viewModelScope.launch {
            userName = patientRepository.getPatientNameByUserId(patientId)
        }
    }


    class FoodQualityScoreViewModelFactory(context: Context) : ViewModelProvider.Factory {
        private val context = context.applicationContext
        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            FoodQualityScoreViewModel(context) as T
    }


    fun updateTotalScore(userId: Int) {
        viewModelScope.launch {
            patientRepository.getUserTotalScore(userId).collect { score ->
                userTotalScore = score
            }
        }
    }

}







