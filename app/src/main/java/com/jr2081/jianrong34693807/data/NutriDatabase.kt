package com.jr2081.jianrong34693807.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jr2081.jianrong34693807.data.foodIntake.FoodIntake
import com.jr2081.jianrong34693807.data.foodIntake.FoodIntakeDao
import com.jr2081.jianrong34693807.data.nutriCoachTips.NutriCoachTip
import com.jr2081.jianrong34693807.data.nutriCoachTips.NutriCoachTipDao
import com.jr2081.jianrong34693807.data.patient.Patient
import com.jr2081.jianrong34693807.data.patient.PatientDao


@Database(entities = [Patient::class, FoodIntake::class, NutriCoachTip::class], version = 2, exportSchema = false)

abstract class NutriDatabase : RoomDatabase(){

    abstract fun patientDao(): PatientDao

    abstract fun foodIntake() : FoodIntakeDao

    abstract fun nutriCoachTipDao() : NutriCoachTipDao


    companion object{
        @Volatile
        private var Instance: NutriDatabase?= null


        fun getDatabase(context: Context) : NutriDatabase{
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, NutriDatabase::class.java, "item_database")
                    .build()
                    .also { Instance = it }
            }
        }

    }

}