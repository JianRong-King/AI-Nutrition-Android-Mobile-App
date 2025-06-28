package com.jr2081.jianrong34693807.data.viewModels

import com.jr2081.jianrong34693807.data.patient.Patient
import com.jr2081.jianrong34693807.data.patient.PatientRepository


import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf

import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.jr2081.jianrong34693807.FoodQuestionnaire
import com.jr2081.jianrong34693807.data.AuthManager
import com.jr2081.jianrong34693807.data.foodIntake.FoodIntake
import com.jr2081.jianrong34693807.data.foodIntake.FoodIntakeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class AuthViewModel(context: Context) : ViewModel() {

    private val patientRepository : PatientRepository = PatientRepository(context)

    private val foodIntakeRepository: FoodIntakeRepository = FoodIntakeRepository(context)


    val allPatient: Flow<List<Patient>> = patientRepository.getAllPatient()



    var userInputPassword by mutableStateOf("") // add a default
    var selectedId by mutableStateOf("")  // Move state here
    var validPassword by mutableStateOf("None*")  // Move state here
    var validNumber by mutableStateOf("")

    var userName by mutableStateOf("")


    var isRegisteredLogin by mutableStateOf(false)

    var phoneNumber by   mutableStateOf("")
    var password by   mutableStateOf("")
    var confirmPassword by  mutableStateOf("")

    fun resetSelectedId() {
        selectedId = ""
    }


    fun validateLogin (context: Context) {
        // Check if such user exist in the Db
        // if true. get the patient object and compare the password
        // use isAuthoized and save to authManager
        // Go the next page -> the page that has navbar .....

        // else: show toast message and let the user input again till valid

        if (!isRegisteredLogin) {
            Toast.makeText(context, "User Have Not Registered - Please Register First !", Toast.LENGTH_LONG).show()
        } else {
            if (userInputPassword == validPassword) {
                AuthManager.login(context, selectedId)


                Toast.makeText(context, "Login Successful", Toast.LENGTH_LONG).show()

                // Use start intent to the next page
                context.startActivity(Intent(context, FoodQuestionnaire::class.java))

            } else {
                Toast.makeText(context, "Wrong Password Please Enter The Correct Password !", Toast.LENGTH_LONG).show()
            }
        }
    }


    private fun updateNameAndPasswordAndRegistered(userId: Int, newPassword: String, hasRegistered: Boolean, userName:String) {
        viewModelScope.launch { patientRepository.updateNameAndPasswordAndRegistered(userId, newPassword, hasRegistered, userName) }
    }


    private fun createInitialStateFoodIntake(userId: Int) {
        viewModelScope.launch {
            foodIntakeRepository.insert(
                FoodIntake(
                    intakePatientId = userId,
                    checkFruits = false,
                    checkVegetables = false,
                    checkGrains = false,
                    checkRedMeat = false,
                    checkSeafood = false,
                    checkPoultry = false,
                    checkFish = false,
                    checkEggs = false,
                    checkNutsSeeds = false,
                    selectedPersona = "Select option",
                    biggestMealTime = "00:00",
                    wakeUpTime = "00:00",
                    sleepTime = "00:00"
                )
            )
        }
    }





    fun validateRegister(navController: NavHostController, context: Context) {
        if (!isRegisteredLogin) {

            if (selectedId.isBlank()) {
                Toast.makeText(context, "User ID cannot be empty", Toast.LENGTH_LONG).show()
                return
            }

            val userId = selectedId.toIntOrNull()
            if (userId == null) {
                Toast.makeText(context, "Invalid User ID format", Toast.LENGTH_LONG).show()
                return
            }

            if (phoneNumber == validNumber) {
                if (password == confirmPassword) {

                    if (userName.isBlank()) {
                        Toast.makeText(context, "Username cannot be empty", Toast.LENGTH_LONG).show()
                        return
                    }
                    // Safe to update now
                    updateNameAndPasswordAndRegistered(userId, password, true, userName)

                    createInitialStateFoodIntake(userId)

                    AuthManager.login(context, selectedId)

                    Toast.makeText(context, "Successfully Registered", Toast.LENGTH_LONG).show()
                    navController.navigate("patient_login")

                } else {
                    Toast.makeText(context, "Two Passwords Entered Do Not Match", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(context, "Wrong Phone Number Given!", Toast.LENGTH_LONG).show()
            }

        } else {
            Toast.makeText(context, "Current UserID Has Been Registered Before. Please Log In Instead", Toast.LENGTH_LONG).show()
        }
    }







    class  AuthViewModelFactory(context: Context) : ViewModelProvider.Factory {
        private val context = context.applicationContext
        override fun <T : ViewModel> create(modelClass: Class<T>) : T = AuthViewModel(context) as T
    }

}