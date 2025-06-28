package com.jr2081.jianrong34693807.data.foodIntake


import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.jr2081.jianrong34693807.data.patient.Patient


@Entity(
    tableName = "food_intake",
    foreignKeys = [
        ForeignKey(
            entity = Patient::class,
            parentColumns = ["userID"],
            childColumns = ["intakePatientId"],
            onDelete = ForeignKey.CASCADE // Delete food records if patient is deleted
        )
    ]
)



data class FoodIntake(

    @PrimaryKey()
    val intakePatientId: Int,  // This is the foreign key to Patient.userID

    val checkFruits: Boolean,
    val checkVegetables: Boolean,
    val checkGrains: Boolean,
    val checkRedMeat: Boolean,
    val checkSeafood: Boolean,
    val checkPoultry: Boolean,
    val checkFish: Boolean,
    val checkEggs: Boolean,
    val checkNutsSeeds: Boolean,
    val selectedPersona: String,

    val biggestMealTime: String,
    val wakeUpTime: String,
    val sleepTime: String
)