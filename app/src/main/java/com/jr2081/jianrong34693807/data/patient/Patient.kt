package com.jr2081.jianrong34693807.data.patient

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "patient")
data class Patient(


    @PrimaryKey(autoGenerate = true)
    val userID: Int = 0,
    val phoneNumber: String,
    val password: String,
    val name: String,

    val sex: String,
    val isRegistered: Boolean,
    val heifaTotalScore: Double,
    val vegetables: Double,
    val fruits: Double,
    val fruitServeSize:  Double,
    val fruitVariationsScore: Double,
    val grainsCereals: Double,
    val wholeGrains: Double,
    val meatAlternative: Double,
    val dairy: Double,
    val water: Double,
    val unsaturatedFat: Double,
    val sodium: Double,
    val sugar: Double,
    val alcohol: Double,
    val discretionary: Double,
    val saturatedFat: Double
)
