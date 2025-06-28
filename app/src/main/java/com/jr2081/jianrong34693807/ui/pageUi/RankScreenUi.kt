package com.jr2081.jianrong34693807.ui.pageUi

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.jr2081.jianrong34693807.data.AuthManager
import com.jr2081.jianrong34693807.data.patient.Patient

import com.jr2081.jianrong34693807.data.viewModels.RankingViewModel
import kotlin.math.roundToInt


@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun RankScreenUI(navController: NavHostController, rankingViewModel: RankingViewModel) {

    val context = LocalContext.current
    val userId = AuthManager.getUserId()

    val isUserRankLoading by rankingViewModel.isUserRankLoading.collectAsState()

    LaunchedEffect(userId) {
        if (userId != null) {
            rankingViewModel.updateFoodAverageScore()
            rankingViewModel.loadTopThreePatients()
            rankingViewModel.loadPatientById(userId.toInt())
            rankingViewModel.getUserRank(userId.toInt())

        } else {
            Toast.makeText(context, "Invalid User", Toast.LENGTH_LONG).show()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Ranking", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Spacer(Modifier.height(10.dp))
        }


        if (rankingViewModel.topThreePatients.value.size >= 3) {
            TopThreeRankingUI(
                rankingViewModel.topThreePatients.value[0],
                rankingViewModel.topThreePatients.value[1],
                rankingViewModel.topThreePatients.value[2]
            )
        } else {
            Text("Not enough data for top 3 rankings.", modifier = Modifier.padding(16.dp))
        }

        HorizontalDivider(
            modifier = Modifier.padding(vertical = 4.dp), // optional spacing
            thickness = 4.dp,
            color = Color.Gray
        )

        Spacer(Modifier.height(5.dp))


        // Display User Rank
        if (isUserRankLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        } else if (rankingViewModel.currentUserRank > 0) {
            DisplayUserRank(rankingViewModel.currentUserRank)
        } else {
            // Handle case where rank is 0 or user not found after loading
            Text("Your rank could not be determined.", fontSize = 14.sp, color = Color.LightGray)
        }


        Text(text = "Your Detailed Ranking Statistic", fontWeight = FontWeight.Bold, fontSize = 18.sp)

        Spacer(Modifier.height(10.dp))

        val patient = rankingViewModel.currentPatient.value

        if (patient != null) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                item { AverageScoreProgressBar("Vegetable Score", patient.vegetables , rankingViewModel.vegetablesAverage) }
                item { AverageScoreProgressBar("Fruit Score", patient.fruits, rankingViewModel.fruitsAverage) }
                item { AverageScoreProgressBar("Grains & Cereals Score", patient.grainsCereals, rankingViewModel.grainsCerealsAverage) }
                item { AverageScoreProgressBar("Whole Grains Score", patient.wholeGrains, rankingViewModel.wholeGrainsAverage) }
                item { AverageScoreProgressBar("Meat & Alternative Score", patient.meatAlternative, rankingViewModel.meatAlternativeAverage) }
                item { AverageScoreProgressBar("Dairy Score", patient.dairy, rankingViewModel.dairyAverage) }
                item { AverageScoreProgressBar("Water Score", patient.water, rankingViewModel.waterAverage) }
                item { AverageScoreProgressBar("Unsaturated Fat Score", patient.unsaturatedFat, rankingViewModel.unsaturatedFatAverage) }
                item { AverageScoreProgressBar("Sodium Score", patient.sodium, rankingViewModel.sodiumAverage) }
                item { AverageScoreProgressBar("Sugar Score", patient.sugar, rankingViewModel.sugarAverage) }
                item { AverageScoreProgressBar("Alcohol Score", patient.alcohol, rankingViewModel.alcoholAverage) }
                item { AverageScoreProgressBar("Discretionary Score", patient.discretionary, rankingViewModel.discretionaryAverage) }
                item { AverageScoreProgressBar("Saturated Fat Score", patient.saturatedFat, rankingViewModel.saturatedFatAverage) }
            }
        } else {
            // Optional loading state
            Text("Loading patient data...", modifier = Modifier.padding(20.dp))
        }


    }
}




@Composable
fun AverageScoreProgressBar(foodDisplay: String, foodScore: Double, averageScore: Double) {
    val roundedScore = foodScore.roundToInt()

    val percentage = if (averageScore != 0.0) {
        (roundedScore.toDouble() / averageScore) * 100
    } else {
        0.0
    }

    // Choose background color based on performance
    val backgroundColor = when {
        percentage >= 100 -> Color(0xFFE0F7E9) // Light green
        else -> Color(0xFFFFE0E0)              // Light red
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .padding(6.dp)
            .border(1.dp, Color.Gray)
            .padding(4.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .width(240.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = foodDisplay,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                )

                Row(
                    modifier = Modifier.width(480.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    LinearProgressIndicator(
                        progress = {
                            if (averageScore != 0.0) foodScore.toFloat() / averageScore.toFloat()
                            else 0f
                        },
                        modifier = Modifier
                            .padding(10.dp)
                            .width(120.dp)
                    )
                }
            }

            Text(
                text = "Your $foodDisplay Have Beaten: ${percentage.roundToInt()} % of Other Users",
                fontSize = 11.sp
            )
        }
    }
}


@Composable
fun TopThreeRankingUI(firstPatient: Patient, secondPatient: Patient, thirdPatient: Patient) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFE3F2FD), shape = RoundedCornerShape(12.dp)) // Light Blue
            .padding(16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "HEIFA Score Top Ranking",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.Bottom
            ) {
                // Second Place
                RankingColumn(placeDesc = "Silver", name = secondPatient.name, score = secondPatient.heifaTotalScore ,height = 100.dp, color = Color.Gray)

                // First Place
                RankingColumn(placeDesc = "Champion", name = firstPatient.name, score = firstPatient.heifaTotalScore, height = 140.dp, color = Color(0xFFFFD700)) // Gold

                // Third Place
                RankingColumn(placeDesc = "Bronze", name = thirdPatient.name, score = thirdPatient.heifaTotalScore , height = 80.dp, color = Color(0xFFCD7F32)) // Bronze
            }
        }
    }
}



@Composable
fun RankingColumn(placeDesc: String, name: String, score: Double, height: Dp, color: Color) {
    Column(
        modifier = Modifier
            .width(80.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (name == "" ) {
            Text(text = "Unregistered User", fontWeight = FontWeight.Bold, fontSize = 9.sp, maxLines = 1)
        } else {
            Text(text = name, fontWeight = FontWeight.Bold, fontSize = 14.sp, maxLines = 1)
        }
        Box(
            modifier = Modifier
                .height(height)
                .fillMaxWidth()
                .background(color, shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)),
            contentAlignment = Alignment.Center
        ) {
            Column (modifier = Modifier, verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    placeDesc,
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "$score",
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontSize = 16.sp,
                )
            }
        }
    }
}


@Composable
 fun DisplayUserRank(currentUserRank: Int) {
      Text(
         text = "You Are Ranked At No.$currentUserRank",
         fontWeight = FontWeight.Bold,
         color = Color.Black,
         fontSize = 16.sp,
     )
 }





// dummy preview screen
@Preview(showBackground = true)
@Composable
fun PreviewRankScreenUI() {
    val context = LocalContext.current
    val navController = rememberNavController()
    val rankingViewModel: RankingViewModel = viewModel(
        factory = RankingViewModel.RankingViewModelFactory(context)
    )

    RankScreenUI(navController, rankingViewModel)
}