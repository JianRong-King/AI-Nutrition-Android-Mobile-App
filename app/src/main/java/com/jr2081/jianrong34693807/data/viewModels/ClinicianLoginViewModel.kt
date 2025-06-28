package com.jr2081.jianrong34693807.data.viewModels


import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider



class ClinicianLoginViewModel(context: Context) : ViewModel() {


    var userInputKey by mutableStateOf("")

    class ClinicianLoginViewModelFactory(context: Context) : ViewModelProvider.Factory {
        private val context = context.applicationContext
        override fun <T : ViewModel> create(modelClass: Class<T>) : T = ClinicianLoginViewModel(context) as T
    }

}



