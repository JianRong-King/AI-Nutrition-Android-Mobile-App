package com.jr2081.jianrong34693807.data.nutriCoachTips



import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import androidx.room.ForeignKey
import com.jr2081.jianrong34693807.data.patient.Patient

@Entity(
    tableName = "NutriCoachTips",
    foreignKeys = [
        ForeignKey(
            entity = Patient::class,
            parentColumns = ["userID"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class NutriCoachTip(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "userId", index = true)
    val userId: Int,

    val message: String // A single motivational message

)
