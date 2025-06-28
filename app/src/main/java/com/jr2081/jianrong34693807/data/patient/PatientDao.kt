package com.jr2081.jianrong34693807.data.patient

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface PatientDao {


    @Insert
    suspend fun insert(patient: Patient)


    @Query("SELECT * FROM patient")
    fun getAllPatient() : Flow<List<Patient>>

    @Query("SELECT * FROM patient WHERE userID =:checkingUserId")
    fun getPatientByUserId(checkingUserId: Int) : Flow<List<Patient>>

    @Query("SELECT name FROM patient WHERE userID =:checkingUserId")
    suspend fun getPatientNameByUserId(checkingUserId: Int) : String

    @Query("UPDATE patient SET password = :newPassword, isRegistered = :hasRegistered, name = :userName  WHERE userID = :userId")
    suspend fun updateNameAndPasswordAndRegistered(userId: Int, newPassword: String, hasRegistered: Boolean, userName: String)

    @Query("SELECT heifaTotalScore FROM patient WHERE userID = :checkingUserId")
    fun getUserTotalScore(checkingUserId: Int): Flow<Double>

    @Query("SELECT AVG(heifaTotalScore) FROM patient WHERE sex = :gender")
    fun getAverageHeifaScoreByGender(gender: String): Flow<Double?>


    // For Rank Page
    @Query("SELECT AVG(vegetables) FROM patient")
    fun getAverageVegetables(): Flow<Double?>

    @Query("SELECT AVG(fruits) FROM patient")
    fun getAverageFruits(): Flow<Double?>

    @Query("SELECT AVG(grainsCereals) FROM patient")
    fun getAverageGrainsCereals(): Flow<Double?>

    @Query("SELECT AVG(wholeGrains) FROM patient")
    fun getAverageWholeGrains(): Flow<Double?>

    @Query("SELECT AVG(meatAlternative) FROM patient")
    fun getAverageMeatAlternative(): Flow<Double?>

    @Query("SELECT AVG(dairy) FROM patient")
    fun getAverageDairy(): Flow<Double?>

    @Query("SELECT AVG(water) FROM patient")
    fun getAverageWater(): Flow<Double?>

    @Query("SELECT AVG(unsaturatedFat) FROM patient")
    fun getAverageUnsaturatedFat(): Flow<Double?>

    @Query("SELECT AVG(sodium) FROM patient")
    fun getAverageSodium(): Flow<Double?>

    @Query("SELECT AVG(sugar) FROM patient")
    fun getAverageSugar(): Flow<Double?>

    @Query("SELECT AVG(alcohol) FROM patient")
    fun getAverageAlcohol(): Flow<Double?>

    @Query("SELECT AVG(discretionary) FROM patient")
    fun getAverageDiscretionary(): Flow<Double?>

    @Query("SELECT AVG(saturatedFat) FROM patient")
    fun getAverageSaturatedFat(): Flow<Double?>

    @Query("SELECT * FROM Patient ORDER BY heifaTotalScore DESC LIMIT 3")
    fun getTop3Patients(): Flow<List<Patient>>

    @Query(" SELECT COUNT(*) + 1 FROM patient WHERE heifaTotalScore > (SELECT heifaTotalScore FROM patient WHERE userID = :userId) ")
    suspend fun getUserRank(userId: Int): Int

}