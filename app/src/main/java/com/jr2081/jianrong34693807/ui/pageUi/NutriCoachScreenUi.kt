package com.jr2081.jianrong34693807.ui.pageUi



import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.jr2081.jianrong34693807.data.UiState
import com.jr2081.jianrong34693807.data.viewModels.NutriCoachVIewModel
import  androidx.compose.material3.Card
import androidx.compose.material3.Icon


import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import coil3.compose.AsyncImage
import com.jr2081.jianrong34693807.R
import com.jr2081.jianrong34693807.data.AuthManager


@Composable
fun NutriCoachScreenUI(navController: NavHostController, nutriCoachVIewModel: NutriCoachVIewModel) {

    val context = LocalContext.current

    val uiState by nutriCoachVIewModel.uiState.collectAsState()

    val userId = AuthManager.getUserId()
    if (userId != null) {
        nutriCoachVIewModel.updateUserFruitScore(userId.toInt(), context)
    }

    LaunchedEffect(uiState) {
        when (uiState) {
            is UiState.Success -> {
                nutriCoachVIewModel.result = (uiState as UiState.Success).outputText
            }
            is UiState.Error -> {
                nutriCoachVIewModel.result = (uiState as UiState.Error).errorMessage
            }
            else -> {}
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start // Align content to start (left)
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "NutriCoach",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }




        Column (modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        )
        {

            if (nutriCoachVIewModel.userFruitServeSize < 5 || nutriCoachVIewModel.userFruitVariationsScore < 5 ) {
                DisplayFruitInfo(nutriCoachVIewModel, LocalContext.current)
            } else{
                //  Random Image

                Text(
                    text = "Congrats Your Fruit Score is Optimal !",
                    fontWeight = FontWeight.Bold,
                )

                AsyncImage(
                    model = "https://fastly.picsum.photos/id/89/4608/2592.jpg?hmac=G9E4z5RMJgMUjgTzeR4CFlORjvogsGtqFQozIRqugBk",
                            contentDescription = "Random image from Picsum",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(16.dp)
                )
            }





            Spacer(modifier = Modifier.height(10.dp))


            Button(
                modifier = Modifier
                    .height(50.dp),
                onClick = {
                    if (userId != null) {
                        nutriCoachVIewModel.updateMotivatioalMessage(userId.toInt())
                    } else {
                        Toast.makeText(context, "Invalid. User not Found", Toast.LENGTH_LONG).show()
                    }
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_insert_emoticon_24),
                    contentDescription = "Improve my diet",
                    modifier = Modifier.size(24.dp) // fixed size
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text("Motivational Message (Al)")
            }



            if (uiState is UiState.Loading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            } else {

                var textColor = MaterialTheme.colorScheme.onSurface

                if (uiState is UiState.Error) {
                    nutriCoachVIewModel.result = (uiState as UiState.Error).errorMessage
                    textColor = MaterialTheme.colorScheme.error
                } else if (uiState is UiState.Success) {
                    nutriCoachVIewModel.result = (uiState as UiState.Success).outputText
                    textColor = MaterialTheme.colorScheme.onSurface
                }

                    Text(
                    text = nutriCoachVIewModel.result,
                    textAlign = TextAlign.Start,
                    color = textColor,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(16.dp)
                )

            }



            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End)
            {

                if (userId != null) {
                    ShowButtonAndModal(nutriCoachVIewModel,userId.toInt())
                } else {
                    Toast.makeText(context, "Invalid. User not Found", Toast.LENGTH_LONG).show()
                }
            }




        }


    }


}

@Composable
fun NutriDataRow(dataCategory: String, dataName: String) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color.Gray)
            .padding(horizontal = 8.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = dataCategory,
            maxLines = 1,
            modifier = Modifier.width(150.dp).padding(horizontal = 4.dp)
        )

        Text(
            text = ":  $dataName",
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}

@Composable
fun NutriDataScoreRow(dataCategory: String, dataScore: Double) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color.Gray)
            .padding(horizontal = 8.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = dataCategory,
            maxLines = 1,
            modifier = Modifier.width(150.dp).padding(horizontal = 4.dp)
        )

        Text(
            text = ":  $dataScore",
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}




@Composable
fun ShowButtonAndModal(nutriCoachVIewModel: NutriCoachVIewModel, userId: Int) {
    var showModal by remember { mutableStateOf(false) }
    val tips by nutriCoachVIewModel.getTipsForUser(userId).collectAsState()

    Button(onClick = { showModal = true }) {
        Icon(
            painter = painterResource(id = R.drawable.baseline_backup_24),
            contentDescription = "Improve my diet",
            modifier = Modifier.size(24.dp) // fixed size
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text("Show All Tips", fontSize = 14.sp)
    }

    if (showModal) {
        AlertDialog(
            onDismissRequest = { showModal = false },
            title = { Text("AI Tips", style = MaterialTheme.typography.titleLarge) },
            text = {
                Column {
                    if (tips.isEmpty()) {
                        Text("No tips for this user yet.", modifier = Modifier.padding(16.dp))
                    } else {
                        LazyColumn(
                            modifier = Modifier
                                .heightIn(max = 300.dp)
                                .padding(8.dp)
                        ) {
                            items(tips.filter { it.message.isNotBlank() }) { tip ->
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 6.dp),
                                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                                ) {
                                    Text(
                                        text = tip.message,
                                        style = MaterialTheme.typography.bodyMedium,
                                        modifier = Modifier.padding(16.dp)
                                    )
                                }
                            }

                        }
                    }
                }
            },

            confirmButton = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(onClick = { showModal = false }) {
                        Text("Dismiss")
                    }
                }
            }
        )
    }
}



@Composable
fun DisplayFruitInfo(nutriCoachVIewModel: NutriCoachVIewModel, context: Context) {

    Row {
        Text(
            text = "Fruit Name"
        )
    }

    Row(
        modifier = Modifier.padding(vertical = 14.dp)
    ) {

        OutlinedTextField(
            value = nutriCoachVIewModel.inputFruitName,
            onValueChange = { nutriCoachVIewModel.inputFruitName = it },
            modifier = Modifier
                .width(200.dp)
                .height(50.dp)
        )

        Spacer(modifier = Modifier.width(25.dp))

        Button(
            modifier = Modifier
                .height(50.dp)
                .width(140.dp),
            onClick = {
                // Get API here
                if (nutriCoachVIewModel.inputFruitName == "") {
                    Toast.makeText(context, "Fruit Name Cannot Be Empty !", Toast.LENGTH_LONG).show()
                } else {
                    nutriCoachVIewModel.updateFruitInfo(nutriCoachVIewModel.inputFruitName)
                }

            }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_info_outline_24),
                contentDescription = "Improve my diet",
                modifier = Modifier.size(24.dp) // fixed size
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text("Details")
        }
    }


    NutriDataRow("Family", nutriCoachVIewModel.displayFruitData.family)
    NutriDataScoreRow("calories", nutriCoachVIewModel.displayFruitData.nutritions.calories)
    NutriDataScoreRow("fat", nutriCoachVIewModel.displayFruitData.nutritions.fat)
    NutriDataScoreRow("sugar", nutriCoachVIewModel.displayFruitData.nutritions.sugar)
    NutriDataScoreRow("carbohydrates", nutriCoachVIewModel.displayFruitData.nutritions.carbohydrates)
    NutriDataScoreRow("protein", nutriCoachVIewModel.displayFruitData.nutritions.protein)

}



// dummy preview screen
@Preview(showBackground = true)
@Composable
fun PreviewNutriCoachScreenPage() {
    val context = LocalContext.current
    val navController = rememberNavController()
    val nutriCoachVIewModel: NutriCoachVIewModel = viewModel(
        factory = NutriCoachVIewModel.NutriCoachVIewModelFactory(context)
    )

    NutriCoachScreenUI(navController, nutriCoachVIewModel)
}