package com.jr2081.jianrong34693807.data.viewModels

import android.content.Context
import android.widget.Toast

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope

import com.jr2081.jianrong34693807.data.UiState
import com.jr2081.jianrong34693807.data.network.FruitInfoRepository
import com.jr2081.jianrong34693807.data.network.Nutritions
import com.jr2081.jianrong34693807.data.network.ResponseModel
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content

import com.jr2081.jianrong34693807.BuildConfig
import com.jr2081.jianrong34693807.data.foodIntake.FoodIntakeRepository
import com.jr2081.jianrong34693807.data.nutriCoachTips.NutriCoachTip
import com.jr2081.jianrong34693807.data.nutriCoachTips.NutriCoachTipRepository
import com.jr2081.jianrong34693807.data.patient.PatientRepository
import kotlinx.coroutines.Dispatchers

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


class NutriCoachVIewModel(context: Context) : ViewModel() {

    private val fruitInfoRepository = FruitInfoRepository(context = context)

    private val nutriCoachTipRepository = NutriCoachTipRepository(context = context)

    private val patientRepository = PatientRepository(context = context)

    private val foodIntakeRepository = FoodIntakeRepository(context = context)

    var userFruitServeSize by mutableDoubleStateOf(0.0)
    var userFruitVariationsScore by mutableDoubleStateOf(0.0)

    var inputFruitName by mutableStateOf("")

    var result by mutableStateOf("")

    val motivationalMessagePrompt  = "Generate a short encouraging message to help someone improve their fruit intake. Make each message unique and generated output only the exact one message text"

    var displayFruitData by mutableStateOf(
        ResponseModel(
            "N/A",
            0,
            "N/A",
            "N/A",
            "N/A",
            Nutritions(0.0, 0.0, 0.0, 0.0, 0.0) // Updated default
        )
    )


    fun updateUserFruitScore(userId: Int, context: Context) {
        viewModelScope.launch {
            // Get the current list of patients for this user
            val patients = patientRepository.getPatientByPatientId(userId).first()

            if (patients.isNotEmpty()) {
                val patient = patients.first()


                userFruitServeSize = patient.fruitServeSize
                userFruitVariationsScore = patient.fruitVariationsScore
            } else {
                Toast.makeText(context, "Error : Patient not found !", Toast.LENGTH_LONG).show()
            }
        }
    }



    // GEN AI
    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState.Initial)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()



    // To update the Fruit info to default value state. When no fruit has been entered / at initial page creation
    fun updateFruitInfo(fruitName: String) {
        viewModelScope.launch {
            val formattedName = fruitName.trim().lowercase()
            val info = fruitInfoRepository.getFruitInfo(formattedName)
            displayFruitData = info ?: ResponseModel(
                "N/A", 0, "N/A", "N/A", "N/A", Nutritions(0.0, 0.0, 0.0, 0.0, 0.0)
            )
        }
    }


    private val generativeModel = GenerativeModel(
        modelName = "gemini-1.5-flash",
        apiKey = BuildConfig.apiKey
    )


    fun updateMotivatioalMessage(userId: Int) {
        _uiState.value = UiState.Loading

        viewModelScope.launch(Dispatchers.IO) {
            try {
                // Fetch patient and food intake data
                val patient = patientRepository.getPatientByPatientId(userId).firstOrNull()?.firstOrNull()
                val foodIntake = foodIntakeRepository.getFoodIntakeByPatientId(userId).firstOrNull()?.firstOrNull()

                if (patient == null) {
                    _uiState.value = UiState.Error("Patient not found.")
                    return@launch
                }

                // Build GenAI prompt
                val promptBuilder = StringBuilder()
                promptBuilder.appendLine("You are a friendly nutrition coach. Write a short motivational message (2–3 sentences) for this patient.")
                promptBuilder.appendLine(motivationalMessagePrompt)
                promptBuilder.appendLine("Patient name: ${patient.name}")
                promptBuilder.appendLine("Sex: ${patient.sex}")
                promptBuilder.appendLine("Fruit score: ${patient.fruits} (servings: ${patient.fruitServeSize}, variety: ${patient.fruitVariationsScore})")
                promptBuilder.appendLine("Vegetable score: ${patient.vegetables}")
                promptBuilder.appendLine("Water intake: ${patient.water}")
                promptBuilder.appendLine("Whole grains: ${patient.wholeGrains}")
                promptBuilder.appendLine("Sugar intake: ${patient.sugar}")
                promptBuilder.appendLine("Saturated fat: ${patient.saturatedFat}")

                foodIntake?.let {
                    promptBuilder.appendLine()
                    promptBuilder.appendLine("Food intake habits:")
                    promptBuilder.appendLine("- User is allowed to eat Fruits?: ${it.checkFruits}")
                    promptBuilder.appendLine("- User is allowed to eat Vegetables?: ${it.checkVegetables}")
                    promptBuilder.appendLine("- User is allowed to eat Grains?: ${it.checkGrains}")
                    promptBuilder.appendLine("- User is allowed to eat RedMeat?: ${it.checkRedMeat}")
                    promptBuilder.appendLine("- User is allowed to eat Seafood?: ${it.checkSeafood}")
                    promptBuilder.appendLine("- User is allowed to eat Poultry?: ${it.checkPoultry}")
                    promptBuilder.appendLine("- User is allowed to eat Fish?: ${it.checkFish}")
                    promptBuilder.appendLine("- User is allowed to eat Eggs?: ${it.checkEggs}")
                    promptBuilder.appendLine("- User is allowed to eat Nuts and Seeds?: ${it.checkNutsSeeds}")
                    promptBuilder.appendLine("- Biggest meal: ${it.biggestMealTime}")
                    promptBuilder.appendLine("- Wake up time: ${it.wakeUpTime}")
                    promptBuilder.appendLine("- Sleep time: ${it.sleepTime}")
                    promptBuilder.appendLine("- Persona: ${it.selectedPersona}")
                }

                promptBuilder.appendLine()
                promptBuilder.append("Encourage their good habits and gently suggest areas to improve. By providing some unique tips")

                // Generate content
                val response = generativeModel.generateContent(
                    content { text(promptBuilder.toString()) }
                )

                val message = response.text
                if (!message.isNullOrBlank()) {
                    _uiState.value = UiState.Success(message)

                    // Save to DB
                    nutriCoachTipRepository.insert(
                        NutriCoachTip(userId = userId, message = message)
                    )
                } else {
                    _uiState.value = UiState.Error("No response from GenAI.")
                }

            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.localizedMessage ?: "Error occurred.")
            }
        }
    }





    fun getTipsForUser(userId: Int): StateFlow<List<NutriCoachTip>> {
        return nutriCoachTipRepository.getTipsUser(userId)
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    }



    class NutriCoachVIewModelFactory(context: Context) : ViewModelProvider.Factory {
        private val context = context.applicationContext
        override fun <T : ViewModel> create(modelClass: Class<T>) : T = NutriCoachVIewModel(context) as T
    }


}



