package com.jr2081.jianrong34693807.data.nutriCoachTips

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.jr2081.jianrong34693807.data.patient.Patient
import kotlinx.coroutines.flow.Flow

@Dao
interface NutriCoachTipDao {

    @Insert
    suspend fun insert(nutriCoachTip : NutriCoachTip)


    @Query("SELECT * FROM NutriCoachTips WHERE userId =:userId")
    fun getAllNutriCoachTips(userId: Int) : Flow<List<NutriCoachTip>>

}