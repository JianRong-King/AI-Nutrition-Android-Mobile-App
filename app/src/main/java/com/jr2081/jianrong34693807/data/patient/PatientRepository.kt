package com.jr2081.jianrong34693807.data.patient

import android.content.Context
import com.jr2081.jianrong34693807.data.NutriDatabase
import kotlinx.coroutines.flow.Flow

class PatientRepository(context: Context) {

    private val patientDao = NutriDatabase.getDatabase(context).patientDao()

    fun getAllPatient(): Flow<List<Patient>> {
        return patientDao.getAllPatient()}

    suspend fun insert(patient: Patient) {
        patientDao.insert(patient)
    }

    fun getPatientByPatientId(patientId: Int):Flow<List<Patient>> =
         patientDao.getPatientByUserId(patientId)

    suspend fun getPatientNameByUserId(patientId: Int) : String  =
        patientDao.getPatientNameByUserId(patientId)



    suspend fun updateNameAndPasswordAndRegistered(userId: Int, newPassword: String, hasRegistered: Boolean, userName: String) {
        patientDao.updateNameAndPasswordAndRegistered(userId, newPassword, hasRegistered, userName)
    }


    fun getUserTotalScore(checkingUserId: Int): Flow<Double> {
        return patientDao.getUserTotalScore(checkingUserId)
    }



    fun getAverageHeifaScoreByGender(gender: String): Flow<Double?> {
        return patientDao.getAverageHeifaScoreByGender(gender)
    }

    fun getAverageVegetables(): Flow<Double?> = patientDao.getAverageVegetables()
    fun getAverageFruits(): Flow<Double?> = patientDao.getAverageFruits()
    fun getAverageGrainsCereals(): Flow<Double?> = patientDao.getAverageGrainsCereals()
    fun getAverageWholeGrains(): Flow<Double?> = patientDao.getAverageWholeGrains()
    fun getAverageMeatAlternative(): Flow<Double?> = patientDao.getAverageMeatAlternative()
    fun getAverageDairy(): Flow<Double?> = patientDao.getAverageDairy()
    fun getAverageWater(): Flow<Double?> = patientDao.getAverageWater()
    fun getAverageUnsaturatedFat(): Flow<Double?> = patientDao.getAverageUnsaturatedFat()
    fun getAverageSodium(): Flow<Double?> = patientDao.getAverageSodium()
    fun getAverageSugar(): Flow<Double?> = patientDao.getAverageSugar()
    fun getAverageAlcohol(): Flow<Double?> = patientDao.getAverageAlcohol()
    fun getAverageDiscretionary(): Flow<Double?> = patientDao.getAverageDiscretionary()
    fun getAverageSaturatedFat(): Flow<Double?> = patientDao.getAverageSaturatedFat()

    fun getTop3Patients(): Flow<List<Patient>> = patientDao.getTop3Patients()

    suspend fun getUserRank(userId: Int): Int = patientDao.getUserRank(userId)

}