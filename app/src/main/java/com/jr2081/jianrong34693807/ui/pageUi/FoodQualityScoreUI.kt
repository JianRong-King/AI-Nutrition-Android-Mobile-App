package com.jr2081.jianrong34693807.ui.pageUi

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width

import androidx.compose.material3.Button

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.jr2081.jianrong34693807.FoodQuestionnaire
import com.jr2081.jianrong34693807.R
import com.jr2081.jianrong34693807.data.AuthManager
import com.jr2081.jianrong34693807.data.viewModels.FoodQualityScoreViewModel





@Composable
fun FoodQualityScorePage(innerPaddingValues: PaddingValues, navController: NavHostController, foodQualityScoreViewModel: FoodQualityScoreViewModel) {

    val context = LocalContext.current

    var userId = AuthManager.getUserId()
    if (userId == null) {
        userId = "Unknown"
    } else {
        foodQualityScoreViewModel.updateTotalScore(userId.toInt())
        foodQualityScoreViewModel.updateUserName(userId.toInt())
    }
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {


        Column (modifier = Modifier.fillMaxSize().padding(horizontal = 20.dp, vertical = 36.dp)) {
            Greetings(foodQualityScoreViewModel.userName)
            EditQuestionnaire(context)
            DisplayFoodImage()
            DisplayScore(navController , foodQualityScoreViewModel)
            Spacer(modifier = Modifier.height(28.dp))
            FoodScoreExplanation()
        }
    }
}






@Composable
fun Greetings(currentUserName: String){
    Row (modifier = Modifier.fillMaxWidth()) {
        Column  {
            Text(text="Hello," , fontSize = 14.sp)
            Text(text= currentUserName, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun EditQuestionnaire(context: Context) {

    Row (modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween){
        Text( modifier = Modifier.width(280.dp),
            text = "You've already filled in the Food Intake Questionnaire, but you can change details here:",
            fontSize = 12.sp, lineHeight = 20.sp)

        Button(onClick = {

            context.startActivity(Intent(context, FoodQuestionnaire::class.java))

        }) {

            Image(
                painter = painterResource(id = R.drawable.baseline_edit_24),
                contentDescription = "Edit Logo",
                modifier = Modifier.size(18.dp).padding(end = 6.dp)
            )


            Text(text="Edit")
        }
    }
}

@Composable
fun DisplayScore(navController: NavHostController, foodQualityScoreViewModel: FoodQualityScoreViewModel) {

    Column  {
        Row(
            modifier = Modifier.fillMaxWidth().height(36.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "My Score", fontSize = 20.sp, fontWeight = FontWeight.Bold)

            Button(onClick = {
                navController.navigate("Insights")
                // nav bar to instead
            }) { Text(text = "See all scores >", fontSize = 12.sp) }

        }

        Spacer(modifier = Modifier.height(14.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.uparrow),
                    contentDescription = "Food Logo",
                    modifier = Modifier.padding(horizontal = 6.dp).size(30.dp)
                )
                Text(
                    text = "Your Food Quality score",
                    modifier = Modifier.padding(horizontal = 4.dp)
                )
            }
            Text(text="${foodQualityScoreViewModel.userTotalScore}/100",  color = Color(0xFF006400))

        }

    }
}

@Composable
fun FoodScoreExplanation() {

    Column (modifier = Modifier.fillMaxWidth()) {
        Text(text="What is the Food Quality Score?", fontWeight= FontWeight.Bold , fontSize = 16.sp)
        Text(text="Your Food Quality Scores provides a snapshot of how well your eating patterns align with established food guidelines, helping you identify both strengths and opportunities for improvement in your diet."
            , fontSize = 12.sp,  lineHeight = 20.sp)
        Spacer(modifier = Modifier.height(14.dp))
        Text(text="This personalized measurement considers various food groups including vegetables, fruits, whole grains, and proteins to give you practical insights for making healthier food choices.",fontSize = 12.sp,  lineHeight = 20.sp)
    }
}

@Composable
fun DisplayFoodImage() {
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(id = R.drawable.food),
            contentDescription = "Food Logo",
            modifier = Modifier.size(300.dp)
        )
    }

}


