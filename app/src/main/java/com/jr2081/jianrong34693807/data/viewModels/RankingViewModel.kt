package com.jr2081.jianrong34693807.data.viewModels

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope

import com.jr2081.jianrong34693807.data.patient.Patient
import com.jr2081.jianrong34693807.data.patient.PatientRepository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class RankingViewModel(context: Context) : ViewModel() {

    private val patientRepository : PatientRepository = PatientRepository(context)


    var vegetablesAverage by mutableDoubleStateOf(0.0)
    var fruitsAverage by mutableDoubleStateOf(0.0)
    var grainsCerealsAverage by mutableDoubleStateOf(0.0)
    var wholeGrainsAverage by mutableDoubleStateOf(0.0)
    var meatAlternativeAverage by mutableDoubleStateOf(0.0)
    var dairyAverage by mutableDoubleStateOf(0.0)
    var waterAverage by mutableDoubleStateOf(0.0)
    var unsaturatedFatAverage by mutableDoubleStateOf(0.0)
    var sodiumAverage by mutableDoubleStateOf(0.0)
    var sugarAverage by mutableDoubleStateOf(0.0)
    var alcoholAverage by mutableDoubleStateOf(0.0)
    var discretionaryAverage by mutableDoubleStateOf(0.0)
    var saturatedFatAverage by mutableDoubleStateOf(0.0)

    var currentPatient = mutableStateOf<Patient?>(null)

    val topThreePatients = MutableStateFlow<List<Patient>>(emptyList())


    fun updateFoodAverageScore() {
        viewModelScope.launch {
            vegetablesAverage = patientRepository.getAverageVegetables().first() ?: 0.0
            fruitsAverage = patientRepository.getAverageFruits().first() ?: 0.0
            grainsCerealsAverage = patientRepository.getAverageGrainsCereals().first() ?: 0.0
            wholeGrainsAverage = patientRepository.getAverageWholeGrains().first() ?: 0.0
            meatAlternativeAverage = patientRepository.getAverageMeatAlternative().first() ?: 0.0
            dairyAverage = patientRepository.getAverageDairy().first() ?: 0.0
            waterAverage = patientRepository.getAverageWater().first() ?: 0.0
            unsaturatedFatAverage = patientRepository.getAverageUnsaturatedFat().first() ?: 0.0
            sodiumAverage = patientRepository.getAverageSodium().first() ?: 0.0
            sugarAverage = patientRepository.getAverageSugar().first() ?: 0.0
            alcoholAverage = patientRepository.getAverageAlcohol().first() ?: 0.0
            discretionaryAverage = patientRepository.getAverageDiscretionary().first() ?: 0.0
            saturatedFatAverage = patientRepository.getAverageSaturatedFat().first() ?: 0.0
        }
    }


    fun loadPatientById(patientId: Int) {
        viewModelScope.launch {
            patientRepository.getPatientByPatientId(patientId).collectLatest { patientList ->
                if (patientList.isNotEmpty()) {
                    currentPatient.value = patientList[0] // Assuming only one patient per ID
                }
            }
        }
    }

    fun getCurrentPatient() : Patient? {
        return currentPatient.value
    }


    fun loadTopThreePatients() {
        viewModelScope.launch {
            patientRepository.getTop3Patients().collect { topList ->
                topThreePatients.value = topList
            }
        }
    }

    var currentUserRank by mutableIntStateOf(0)
        private set
    private val _isUserRankLoading = MutableStateFlow(true) // Loading state for currentUserRank
    val isUserRankLoading = _isUserRankLoading.asStateFlow() // Expose as StateFlow

    fun getUserRank(userId: Int) {
        viewModelScope.launch {
            _isUserRankLoading.value = true // Set loading to true
            currentUserRank = patientRepository.getUserRank(userId)
            _isUserRankLoading.value = false // Set loading to false after data is received
        }
    }




    class  RankingViewModelFactory(context: Context) : ViewModelProvider.Factory {
        private val context = context.applicationContext
        override fun <T : ViewModel> create(modelClass: Class<T>) : T = RankingViewModel(context) as T
    }


}