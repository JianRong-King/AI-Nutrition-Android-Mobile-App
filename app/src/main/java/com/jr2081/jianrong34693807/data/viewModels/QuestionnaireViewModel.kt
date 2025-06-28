package com.jr2081.jianrong34693807.data.viewModels

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.jr2081.jianrong34693807.data.foodIntake.FoodIntake
import com.jr2081.jianrong34693807.data.foodIntake.FoodIntakeRepository
import kotlinx.coroutines.launch


class QuestionnaireViewModel(context: Context) : ViewModel() {

    private val foodIntakeRepository = FoodIntakeRepository(context = context)



    // Boolean state fields with default = false
    private var checkFruits = mutableStateOf(false)

    private var checkVegetables = mutableStateOf(false)

    private var checkGrains = mutableStateOf(false)

    private var checkRedMeat = mutableStateOf(false)

    private var checkSeafood = mutableStateOf(false)

    private var checkPoultry = mutableStateOf(false)

    private var checkFish = mutableStateOf(false)

    private var checkEggs = mutableStateOf(false)

    private var checkNutsSeeds = mutableStateOf(false)

    var selectedPersona by mutableStateOf("Select option") // Or any default value


    var biggestMealTime = mutableStateOf("00:00")

    var sleepTime = mutableStateOf("00:00")
    var wakeUpTime = mutableStateOf("00:00")






    // Public accessors
    fun isFruitsChecked() = checkFruits.value
    fun isVegetablesChecked() = checkVegetables.value
    fun isGrainsChecked() = checkGrains.value
    fun isRedMeatChecked() = checkRedMeat.value
    fun isSeafoodChecked() = checkSeafood.value
    fun isPoultryChecked() = checkPoultry.value
    fun isFishChecked() = checkFish.value
    fun isEggsChecked() = checkEggs.value
    fun isNutsSeedsChecked() = checkNutsSeeds.value

    // Public mutators
    fun setFruitsChecked(value: Boolean) { checkFruits.value = value }
    fun setVegetablesChecked(value: Boolean) { checkVegetables.value = value }
    fun setGrainsChecked(value: Boolean) { checkGrains.value = value }
    fun setRedMeatChecked(value: Boolean) { checkRedMeat.value = value }
    fun setSeafoodChecked(value: Boolean) { checkSeafood.value = value }
    fun setPoultryChecked(value: Boolean) { checkPoultry.value = value }
    fun setFishChecked(value: Boolean) { checkFish.value = value }
    fun setEggsChecked(value: Boolean) { checkEggs.value = value }
    fun setNutsSeedsChecked(value: Boolean) { checkNutsSeeds.value = value }


    fun addSelectedPersona(persona: String) {
        selectedPersona = persona
    }



    fun loadCurrentUserData(userId: Int) {
        viewModelScope.launch {
            foodIntakeRepository.getFoodIntakeByPatientId(userId).collect { foodList ->
                val foodIntake = foodList.firstOrNull()
                foodIntake?.let {
                    checkFruits.value = it.checkFruits
                    checkVegetables.value = it.checkVegetables
                    checkGrains.value = it.checkGrains
                    checkRedMeat.value = it.checkRedMeat
                    checkSeafood.value = it.checkSeafood
                    checkPoultry.value = it.checkPoultry
                    checkFish.value = it.checkFish
                    checkEggs.value = it.checkEggs
                    checkNutsSeeds.value = it.checkNutsSeeds

                    selectedPersona = it.selectedPersona
                    biggestMealTime.value = it.biggestMealTime
                    wakeUpTime.value = it.wakeUpTime
                    sleepTime.value = it.sleepTime
                }
            }
        }
    }


    fun saveCurrentUserData(userId: Int) {
        viewModelScope.launch { foodIntakeRepository.insert(FoodIntake(
            userId, checkFruits.value, checkVegetables.value,  checkGrains.value,  checkRedMeat.value, checkSeafood.value, checkPoultry.value,
            checkFish.value, checkEggs.value, checkNutsSeeds.value,  selectedPersona, biggestMealTime.value, wakeUpTime.value , sleepTime.value,
        )) }
    }


    class QuestionnaireViewModelFactory(context: Context) : ViewModelProvider.Factory {
        private val context = context.applicationContext
        override fun <T : ViewModel> create(modelClass: Class<T>) : T = QuestionnaireViewModel(context) as T
    }







}



