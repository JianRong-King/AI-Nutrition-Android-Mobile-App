package com.jr2081.jianrong34693807.ui.pageUi

import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_SEND
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.jr2081.jianrong34693807.R
import com.jr2081.jianrong34693807.data.AuthManager
import com.jr2081.jianrong34693807.data.viewModels.InsightScreenViewModel
import com.jr2081.jianrong34693807.util.FoodMaxScore

import kotlin.math.roundToInt



@Composable
fun InsightScreenPage(navController: NavHostController, insightScreenViewModel: InsightScreenViewModel) {

    val userId = AuthManager.getUserId()?.toInt()

    if (userId != null) {
        insightScreenViewModel.updateInsightScore(userId)
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally) {

        val context = LocalContext.current



        Column(modifier = Modifier.fillMaxWidth().padding(14.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text="Insights:Food Score", fontWeight = FontWeight.Bold, fontSize = 16.sp)

            Spacer(Modifier.height(26.dp))

            // For Displaying each score progress bar
            FoodProgressBar("Vegetables", insightScreenViewModel.vegetableScore, FoodMaxScore.VEGETABLES_MaxScore)
            Spacer(Modifier.height(8.dp))
            FoodProgressBar("Fruits", insightScreenViewModel.fruitsScore, FoodMaxScore.FRUITS_MaxScore)
            Spacer(Modifier.height(8.dp))
            FoodProgressBar("Grains & Cereals", insightScreenViewModel.grainsCerealsScore, FoodMaxScore.GRAINS_CEREALS_MaxScore)
            Spacer(Modifier.height(8.dp))
            FoodProgressBar("Whole Grains", insightScreenViewModel.wholeGrainScore, FoodMaxScore.WHOLE_GRAINS_MaxScore)
            Spacer(Modifier.height(8.dp))
            FoodProgressBar("Meat & Alternative", insightScreenViewModel.meatAltScore, FoodMaxScore.MEAT_ALTERNATIVE_MaxScore)
            Spacer(Modifier.height(8.dp))
            FoodProgressBar("Dairy", insightScreenViewModel.dairyScore, FoodMaxScore.DAIRY_MaxScore)
            Spacer(Modifier.height(8.dp))
            FoodProgressBar("Water", insightScreenViewModel.waterScore, FoodMaxScore.WATER_MaxScore)
            Spacer(Modifier.height(8.dp))
            FoodProgressBar("Unsaturated Fats", insightScreenViewModel.unsaturatedFatScore, FoodMaxScore.UNSATURATED_MaxScore)
            Spacer(Modifier.height(8.dp))
            FoodProgressBar("Sodium", insightScreenViewModel.sodiumScore, FoodMaxScore.SODIUM_MaxScore)
            Spacer(Modifier.height(8.dp))
            FoodProgressBar("Sugar", insightScreenViewModel.sugarScore, FoodMaxScore.SUGAR_MaxScore)
            Spacer(Modifier.height(8.dp))
            FoodProgressBar("Alcohol", insightScreenViewModel.alcoholScore, FoodMaxScore.ALCOHOL_MaxScore)
            Spacer(Modifier.height(8.dp))
            FoodProgressBar("Saturated Fats", insightScreenViewModel.saturatedFatScore, FoodMaxScore.SATURATED_FAT_MaxScore)
            Spacer(Modifier.height(8.dp))
            FoodProgressBar("Discretionary Foods", insightScreenViewModel.discretionaryFoodScore, FoodMaxScore.DISCRETIONARY_MaxScore)
            Spacer(Modifier.height(50.dp))

            // Display total score
            TotalFoodScore(insightScreenViewModel.totalScore.toString())
            Spacer(Modifier.height(30.dp))

            // Button to share total foodScore only
            ShareButton(context, insightScreenViewModel.totalScore.toString())
            Spacer(Modifier.height(16.dp))
            ImproveButton(navController)
        }

    }


}




@Composable
fun FoodProgressBar(foodDisplay:String, foodScore: Double, maxScore:String) {

    val max = maxScore.toFloatOrNull() ?: 1f
    val roundedScore = foodScore.roundToInt()   // round to whole number
    Row(modifier = Modifier.fillMaxWidth().width(240.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
        Text(text = foodDisplay, fontSize = 14.sp, modifier = Modifier.width(125.dp))

        Row(modifier = Modifier.width(480.dp), horizontalArrangement = Arrangement.End) {
            LinearProgressIndicator(
                progress = { foodScore.toFloat() / max },
                modifier = Modifier.padding(10.dp).width(120.dp)
            )
            Text(text = "${roundedScore}/${maxScore}", modifier = Modifier.width(50.dp))
        }
    }
}



@Composable
fun TotalFoodScore(totalScore : String) {
    val score = totalScore.toFloatOrNull() ?: 0f
    Column(modifier = Modifier.fillMaxWidth()){
        Text(text= "Total Food Quality Score", fontSize = 18.sp, fontWeight = FontWeight.Bold)

        Row ( modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            LinearProgressIndicator(
                progress = { score / 100f },
                modifier = Modifier.width(280.dp)
            )
            Text(text="${totalScore}/100")
        }
    }
}

@Composable
fun ShareButton(context: Context, totalScore : String){

    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        Button(onClick = {
            val shareText = "Hi, I just got a HEIFA score of: $totalScore"
            val shareIntent = Intent(ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareText)
            context.startActivity(Intent.createChooser(shareIntent, "Share text via"))

        }) {
            Icon(
                painter = painterResource(id = R.drawable.share_vector_),
                contentDescription = "share with someone",
                modifier = Modifier.size(24.dp) // fixed size
            )
            Spacer(modifier = Modifier.width(4.dp))

            Text(text="Share with someone")
        }
    }
}

@Composable
fun ImproveButton(navController: NavHostController){

    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        Button(onClick = {

            navController.navigate("NutriCoach")

        }) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_analytics_24),
                contentDescription = "Improve my diet",
                modifier = Modifier.size(24.dp) // fixed size
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(text="Improve my diet!")
        }
    }
}