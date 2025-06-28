package com.jr2081.jianrong34693807.data.viewModels

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.jr2081.jianrong34693807.util.CsvPosContraints
import com.jr2081.jianrong34693807.data.patient.Patient
import com.jr2081.jianrong34693807.data.patient.PatientRepository
import kotlinx.coroutines.Dispatchers

import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader

class WelcomePageViewModel(context: Context) : ViewModel() {

    private val patientRepository = PatientRepository(context = context)
    private var userName by mutableStateOf("")


    private fun insertPatient(patient: Patient) {
        viewModelScope.launch { patientRepository.insert(patient) }
    }


    suspend fun getUserNameById(userId: Int): String {
        return withContext(Dispatchers.IO) {
            patientRepository.getPatientNameByUserId(userId)
        }
    }


    fun updateUserNameState(name: String) {
        userName = name
    }



    fun loadDataIntoDatabase(context: Context) {

        val sharedPreferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        val isDataLoaded = sharedPreferences.getBoolean("isDataLoaded", false)

        if (isDataLoaded) {
            Toast.makeText(context, "Data already loaded", Toast.LENGTH_SHORT).show()
            return
        }

        val data = loadCsv( context, "data.csv")

        try {
            for (row in data) {
                // Put in as Magic Numbers **

                if (row[2] == "Male") {
                    val patientResult = Patient(row[CsvPosContraints.USER_ID_CSV_POS].toInt(), row[CsvPosContraints.PHONE_NUMBER_CSV_POS], "None*"  , "",row[CsvPosContraints.SEX_CSV_POS], false, row[CsvPosContraints.HEIFA_TOTAL_SCORE_CSV_POS].toDouble(),
                        row[CsvPosContraints.VEGETABLES_CSV_POS].toDouble(),
                        row[CsvPosContraints.FRUITS_CSV_POS].toDouble(),
                        row[CsvPosContraints.FRUIT_SERVE_SIZE_CSV_POS].toDouble(),
                        row[CsvPosContraints.FRUIT_VARIATIONS_SCORE_CSV_POS].toDouble(),
                        row[CsvPosContraints.GRAINS_CEREALS_CSV_POS].toDouble(),
                        row[CsvPosContraints.WHOLE_GRAINS_CSV_POS].toDouble(),
                        row[CsvPosContraints.MEAT_ALTERNATIVE_CSV_POS].toDouble(),
                        row[CsvPosContraints.DAIRY_CSV_POS].toDouble(),
                        row[CsvPosContraints.WATER_CSV_POS].toDouble(),
                        row[CsvPosContraints.UNSATURATED_FAT_CSV_POS].toDouble(),
                        row[CsvPosContraints.SODIUM_CSV_POS].toDouble(),
                        row[CsvPosContraints.SUGAR_CSV_POS].toDouble(),
                        row[CsvPosContraints.ALCOHOL_CSV_POS].toDouble(),
                        row[CsvPosContraints.DISCRETIONARY_CSV_POS].toDouble(),
                        row[CsvPosContraints.SATURATED_FAT_CSV_POS].toDouble()
                    )
                    insertPatient(patientResult)

                } else {
                    val patientResult = Patient(row[CsvPosContraints.USER_ID_CSV_POS].toInt(), row[CsvPosContraints.PHONE_NUMBER_CSV_POS], "None*" ,"" , row[CsvPosContraints.SEX_CSV_POS],false, row[CsvPosContraints.HEIFA_TOTAL_SCORE_CSV_POS + 1].toDouble(),
                        row[CsvPosContraints.VEGETABLES_CSV_POS + 1].toDouble(),
                        row[CsvPosContraints.FRUITS_CSV_POS + 1].toDouble(),
                        row[CsvPosContraints.FRUIT_SERVE_SIZE_CSV_POS].toDouble(),
                        row[CsvPosContraints.FRUIT_VARIATIONS_SCORE_CSV_POS].toDouble(),
                        row[CsvPosContraints.GRAINS_CEREALS_CSV_POS + 1].toDouble(),
                        row[CsvPosContraints.WHOLE_GRAINS_CSV_POS + 1].toDouble(),
                        row[CsvPosContraints.MEAT_ALTERNATIVE_CSV_POS + 1].toDouble(),
                        row[CsvPosContraints.DAIRY_CSV_POS + 1].toDouble(),
                        row[CsvPosContraints.WATER_CSV_POS + 1].toDouble(),
                        row[CsvPosContraints.UNSATURATED_FAT_CSV_POS + 1].toDouble(),
                        row[CsvPosContraints.SODIUM_CSV_POS + 1].toDouble(),
                        row[CsvPosContraints.SUGAR_CSV_POS + 1].toDouble(),
                        row[CsvPosContraints.ALCOHOL_CSV_POS + 1].toDouble(),
                        row[CsvPosContraints.DISCRETIONARY_CSV_POS + 1].toDouble(),
                        row[CsvPosContraints.SATURATED_FAT_CSV_POS + 1].toDouble()
                    )
                    insertPatient(patientResult)
                }

            }

            sharedPreferences.edit().putBoolean("isDataLoaded", true).apply()
            Toast.makeText(context, "Data loaded successfully", Toast.LENGTH_SHORT).show()


        } catch (e: Exception) {
            Toast.makeText(context, "CSV Data was added before or is invalid", Toast.LENGTH_LONG).show()
        }
    }


    class WelcomePageViewModelFactory(context: Context) : ViewModelProvider.Factory {
        private val context = context.applicationContext
        override fun <T : ViewModel> create(modelClass: Class<T>) : T = WelcomePageViewModel(context) as T
    }



    // Format of [  [val1, val2, val3,]   ]
    private fun loadCsv(context: Context, fileName:String) : MutableList<List<String>> {

        val assets = context.assets
        val userData: MutableList<List<String>> = mutableListOf()

        try {
            val inputStream = assets.open(fileName)
            val reader = BufferedReader(InputStreamReader(inputStream))
            reader.useLines { lines ->
                lines.drop(1).forEach { line ->
                    val values = line.split(",")

                    if (values.size > 2) {
                        userData.add(values)
                    }
                }
            }
        } catch (e: Exception) {
            Toast.makeText(context, "Failed to catch user details", Toast.LENGTH_LONG).show()
        }

        return userData
    }






}



