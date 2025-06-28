package com.jr2081.jianrong34693807.data.foodIntake

import android.content.Context
import com.jr2081.jianrong34693807.data.NutriDatabase
import kotlinx.coroutines.flow.Flow

class FoodIntakeRepository(context: Context) {

    private val foodIntakeDao = NutriDatabase.getDatabase(context).foodIntake()


    suspend fun insert(foodIntake: FoodIntake) {
        foodIntakeDao.insert(foodIntake)
    }

    fun getFoodIntakeByPatientId(patientId: Int) : Flow<List<FoodIntake>> =
        foodIntakeDao.getFoodIntakeByPatientId(patientId)



}