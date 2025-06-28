package com.jr2081.jianrong34693807.data.nutriCoachTips

import android.content.Context
import com.jr2081.jianrong34693807.data.NutriDatabase
import kotlinx.coroutines.flow.Flow

class NutriCoachTipRepository(context: Context) {

    val nutriCoachTipDao = NutriDatabase.getDatabase(context).nutriCoachTipDao()

    suspend fun insert(nutriCoachTip: NutriCoachTip) {
        nutriCoachTipDao.insert(nutriCoachTip)
    }


    fun getTipsUser(userId: Int): Flow<List<NutriCoachTip>> {
        return nutriCoachTipDao.getAllNutriCoachTips(userId)
    }
}