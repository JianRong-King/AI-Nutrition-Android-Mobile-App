package com.jr2081.jianrong34693807.data.viewModels

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import com.jr2081.jianrong34693807.BuildConfig
import com.jr2081.jianrong34693807.data.UiState
import com.jr2081.jianrong34693807.data.patient.PatientRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


class ClinicianDashboardViewModel(context: Context) : ViewModel() {

    private val patientRepository = PatientRepository(context = context)


    var maleAverageScore by mutableStateOf(0.0)

    var femaleAverageScore by mutableStateOf(0.0)


    val dataAnalyticPrompt  = "Analyze the following patient data and return three key data patterns. Display 3 interesting patterns in the data fed. From your response, seperate each message by * and put : in the start and end of the header. for example: :Variable Water Intake: Consumption of water varies greatly " +
            "among the users in this dataset, with scores ranging from 0 to " +
            "100. There isn't a clear, immediate correlation in this small sample " +
            "between water intake score and the overall HEIFA score, though" +
            "some high scorers did have high water intake.*"

    var genAiResult by mutableStateOf("")


    class ClinicianDashboardViewModelFactory(context: Context) : ViewModelProvider.Factory {
        private val context = context.applicationContext
        override fun <T : ViewModel> create(modelClass: Class<T>) : T = ClinicianDashboardViewModel(context) as T
    }


    fun updateAverageScore() {
        viewModelScope.launch {
            // Launch separate coroutines for parallel collection
            launch {
                patientRepository.getAverageHeifaScoreByGender("Male").collectLatest { average ->
                    maleAverageScore = average ?: 0.0
                }
            }

            launch {
                patientRepository.getAverageHeifaScoreByGender("Female").collectLatest { average ->
                    femaleAverageScore = average ?: 0.0
                }
            }
        }


    }



    // GEN AI
    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState.Initial)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()



    private val generativeModel = GenerativeModel(
        modelName = "gemini-1.5-flash",
        apiKey = BuildConfig.apiKey
    )


    fun updateDataAnalysis() {

        _uiState.value = UiState.Loading

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val allPatientInfo = patientRepository.getAllPatient().first()

                val prompt = StringBuilder()
                prompt.append(dataAnalyticPrompt)
                allPatientInfo.forEach { patient ->
                    prompt.append(
                        """
                        Patient ID: ${patient.userID}
                        Sex: ${patient.sex}
                        Heifa Total Score: ${patient.heifaTotalScore}
                        Vegetables: ${patient.vegetables}, Fruits: ${patient.fruits}, Grains/Cereals: ${patient.grainsCereals}
                        Whole Grains: ${patient.wholeGrains}, Meat Alternatives: ${patient.meatAlternative}, Dairy: ${patient.dairy}
                        Water: ${patient.water}, Unsaturated Fat: ${patient.unsaturatedFat}, Sodium: ${patient.sodium}
                        Sugar: ${patient.sugar}, Alcohol: ${patient.alcohol}, Discretionary: ${patient.discretionary}, Saturated Fat: ${patient.saturatedFat}
                        
                        """.trimIndent()
                    )
                }


                val response = generativeModel.generateContent(
                    content { text(prompt.toString()) }
                )
                response.text?.let {outputContent ->
                    _uiState.value = UiState.Success(outputContent)

                }

            } catch (e:Exception) {
                _uiState.value = UiState.Error(e.localizedMessage ?: "")
            }
        }
    }






}
