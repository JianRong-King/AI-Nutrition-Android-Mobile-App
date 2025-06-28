package com.jr2081.jianrong34693807.ui.pageUi

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.jr2081.jianrong34693807.data.UiState
import com.jr2081.jianrong34693807.data.viewModels.ClinicianDashboardViewModel


@Composable
fun ClinicianDashboardScreen(navController: NavHostController, clinicianDashboardViewModel: ClinicianDashboardViewModel) {

    val uiState by clinicianDashboardViewModel.uiState.collectAsState()

    LaunchedEffect(uiState) {
        when (uiState) {
            is UiState.Success -> {
                clinicianDashboardViewModel.genAiResult = (uiState as UiState.Success).outputText
            }
            is UiState.Error -> {
                clinicianDashboardViewModel.genAiResult = (uiState as UiState.Error).errorMessage
            }
            else -> {}
        }
    }

    clinicianDashboardViewModel.updateAverageScore()




    Column(
        modifier = Modifier
            .fillMaxSize()
        .padding(horizontal = 18.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {

        Column(
            modifier = Modifier.fillMaxWidth().padding(14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Clinician Dashboard",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )


        }

        Spacer(modifier = Modifier.height(24.dp))

        AverageScoreRow("Male", clinicianDashboardViewModel.maleAverageScore)
        Spacer(modifier = Modifier.height(10.dp))
        AverageScoreRow("Female", clinicianDashboardViewModel.femaleAverageScore)

        Spacer(modifier = Modifier.height(24.dp))

        HorizontalDivider()

        Spacer(modifier = Modifier.height(8.dp))


        Button(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .fillMaxWidth(),
            onClick = {
                clinicianDashboardViewModel.updateDataAnalysis()
            }
        ) {
            Text(text = "Find Data Pattern",
                fontSize = 24.sp,
                fontWeight = FontWeight.Light)
        }


        Column (modifier = Modifier.height(470.dp),

        ) {

            if (uiState is UiState.Loading) {
                Column (modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                }
            } else {
                DisplayGenAiDataResult(clinicianDashboardViewModel.genAiResult)
            }


        }

        Row (modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End) {
            Button(
                modifier = Modifier
                    .padding(14.dp)
                    .padding(horizontal = 20.dp),
                onClick = {
                    navController.navigate("SettingsMain")
                }
            ) {
                Text("Done")
            }
        }

    }

}


@Composable
fun DisplayGenAiDataResult(genAiResult: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        val insights = genAiResult.split("*").filter { it.isNotBlank() }

        LazyColumn {
            items(insights) { insight ->
                val trimmed = insight.trim()
                val start = trimmed.indexOf(":")
                val end = trimmed.indexOf(":", start + 1)

                val header = if (start != -1 && end != -1) {
                    trimmed.substring(start + 1, end).trim()
                } else ""

                val content = if (end != -1 && end + 1 < trimmed.length) {
                    trimmed.substring(end + 1).trimStart(':', ' ').trim()
                } else trimmed

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        if (header.isNotEmpty()) {
                            Text(
                                text = "$header:",
                                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                                fontSize = 14.sp
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = content,
                            style = MaterialTheme.typography.bodyMedium,
                            fontSize = 10.sp
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun AverageScoreRow(sex: String, dataScore: Double) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color.Gray)
            .padding(horizontal = 8.dp, vertical = 4.dp)
            ,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Average HEIFA ($sex)",
            maxLines = 1,
            modifier = Modifier.width(200.dp),
            fontSize = 16.sp
        )

        Text(
            text = ":  $dataScore",
            modifier = Modifier.padding(start = 8.dp),
            fontSize = 16.sp
        )
    }
}


// dummy preview screen
@Preview(showBackground = true)
@Composable
fun PreviewClinicianDashboardScreen() {
    val context = LocalContext.current
    val navController = rememberNavController()
    val clinicianDashboardViewModel: ClinicianDashboardViewModel = viewModel(
        factory = ClinicianDashboardViewModel.ClinicianDashboardViewModelFactory(context)
    )
    ClinicianDashboardScreen(navController, clinicianDashboardViewModel )
}