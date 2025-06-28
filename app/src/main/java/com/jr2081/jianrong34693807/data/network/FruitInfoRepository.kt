package com.jr2081.jianrong34693807.data.network

import android.content.Context

class FruitInfoRepository(context: Context) {

    private val apiService = APIService.create()

    suspend fun getFruitInfo(name: String): ResponseModel? {
        return try {
            val response = apiService.getFoodInfo(name)
            if (response.isSuccessful) response.body() else null
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

}