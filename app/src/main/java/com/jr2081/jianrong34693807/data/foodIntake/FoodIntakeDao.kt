package com.jr2081.jianrong34693807.data.foodIntake

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow



@Dao
interface FoodIntakeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(foodIntake: FoodIntake)

    @Query("SELECT * FROM FOOD_INTAKE")
    fun getAllFoodIntake(): Flow<List<FoodIntake>>

    @Query("SELECT * FROM food_intake WHERE intakePatientId =:checkPatientId")
    fun getFoodIntakeByPatientId(checkPatientId: Int) : Flow<List<FoodIntake>>
}