package com.jr2081.jianrong34693807.data.viewModels

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.jr2081.jianrong34693807.data.patient.PatientRepository
import kotlinx.coroutines.launch


class SettingsScreenViewModel(context: Context) : ViewModel() {

    private val patientRepository = PatientRepository(context = context)

    var patientName = mutableStateOf("")
    var patientPhoneNumber = mutableStateOf("")



    fun updatePatientNameNumber(userId: Int) {
        viewModelScope.launch {
            patientRepository.getPatientByPatientId(userId).collect { patientList ->
                if (patientList.isNotEmpty()) {
                    val patient = patientList.first()
                    patientName.value = patient.name
                    patientPhoneNumber.value = patient.phoneNumber
                }
            }
        }
    }



    class SettingsScreenViewModelFactory(context: Context) : ViewModelProvider.Factory {
        private val context = context.applicationContext
        override fun <T : ViewModel> create(modelClass: Class<T>) : T = SettingsScreenViewModel(context) as T
    }
}
