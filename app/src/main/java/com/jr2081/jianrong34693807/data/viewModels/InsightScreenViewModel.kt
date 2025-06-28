package com.jr2081.jianrong34693807.data.viewModels

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.jr2081.jianrong34693807.data.patient.PatientRepository
import kotlinx.coroutines.launch


class InsightScreenViewModel(context: Context) : ViewModel() {

    private val patientRepository = PatientRepository(context)

    var vegetableScore by mutableDoubleStateOf(0.0)
    var fruitsScore by mutableDoubleStateOf(0.0)
    var grainsCerealsScore by mutableDoubleStateOf(0.0)
    var wholeGrainScore by mutableDoubleStateOf(0.0)
    var meatAltScore by mutableDoubleStateOf(0.0)
    var dairyScore by mutableDoubleStateOf(0.0)
    var waterScore by mutableDoubleStateOf(0.0)
    var unsaturatedFatScore by mutableDoubleStateOf(0.0)
    var saturatedFatScore by mutableDoubleStateOf(0.0)
    var sodiumScore by mutableDoubleStateOf(0.0)
    var sugarScore by mutableDoubleStateOf(0.0)
    var alcoholScore by mutableDoubleStateOf(0.0)
    var discretionaryFoodScore by mutableDoubleStateOf(0.0)

    var totalScore by mutableDoubleStateOf(0.0)


    fun updateInsightScore(userId: Int) {
        viewModelScope.launch {
            patientRepository.getPatientByPatientId(userId).collect { patientList ->
                val patient = patientList.firstOrNull()
                if (patient != null) {
                    vegetableScore = patient.vegetables
                    fruitsScore = patient.fruits
                    grainsCerealsScore = patient.grainsCereals
                    wholeGrainScore = patient.wholeGrains
                    meatAltScore = patient.meatAlternative
                    dairyScore = patient.dairy
                    waterScore = patient.water
                    unsaturatedFatScore = patient.unsaturatedFat
                    sodiumScore = patient.sodium
                    sugarScore = patient.sugar
                    alcoholScore = patient.alcohol
                    discretionaryFoodScore = patient.discretionary
                    totalScore = patient.heifaTotalScore
                    saturatedFatScore = patient.saturatedFat
                }
            }
        }
    }



    class InsightScreenViewModelFactory(context: Context) : ViewModelProvider.Factory {
        private val context = context.applicationContext
        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            InsightScreenViewModel(context) as T
    }

}
