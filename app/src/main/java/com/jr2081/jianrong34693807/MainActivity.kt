package com.jr2081.jianrong34693807

import android.content.Intent
import android.os.Bundle
import android.widget.Toast

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch

import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jr2081.jianrong34693807.data.AuthManager
import com.jr2081.jianrong34693807.data.viewModels.WelcomePageViewModel


import com.jr2081.jianrong34693807.ui.theme.JianRong34693807Theme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JianRong34693807Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    val context = LocalContext.current
                    val welcomePageViewModel: WelcomePageViewModel = viewModel(
                        factory = WelcomePageViewModel.WelcomePageViewModelFactory(context)
                    )
                    WelcomePageLayout(welcomePageViewModel, modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}





@Composable
fun WelcomePageLayout(welcomePageViewModel: WelcomePageViewModel, modifier: Modifier = Modifier) {



    // Only Get to MainActivity once - So Csv Data is only loaded once
    val context = LocalContext.current
    welcomePageViewModel.loadDataIntoDatabase(context)

    val coroutineScope = rememberCoroutineScope()



    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Spacer(modifier = Modifier.height(130.dp))


            // Text for App title
            Text(
                text = "NutriTrack",
                modifier = Modifier.padding(16.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 46.sp
            )

            // Image placement
            Image(
                painter = painterResource(id = R.drawable.applogo),
                contentDescription = "App Logo",
                modifier = Modifier.size(120.dp)
            )


            // Disclaimer text
            Text(
                text = "This app provides general health and nutrition information for\n" +
                        "educational purposes only. It is not intended as medical advice,\n" +
                        "diagnosis, or treatment. Always consult a qualified healthcare\n" +
                        "professional before making any changes to your diet, exercise, or\n" +
                        "health regimen.\n" +
                        "Use this app at your own risk.\n" +
                        "If you’d like to an Accredited Practicing Dietitian (APD), please\n" +
                        "visit the Monash Nutrition/Dietetics Clinic (discounted rates for\n" +
                        "students):\n" +
                        "https://www.monash.edu/medicine/scs/nutrition/clinics/nutrition ",
                modifier = Modifier.padding(16.dp),
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
                lineHeight = 18.sp
            )

            Spacer(modifier = Modifier.height(10.dp))



            // Login Button
            Button(
                onClick = {
                    context.startActivity(Intent(context, AuthPage::class.java))
                },

                modifier = Modifier.fillMaxWidth().padding(16.dp).height(50.dp)
            ) {
                Text("Login")
            }


            // Continue With Previous Logged User Button
            Button(
                onClick = {
                    AuthManager.loadUserIdFromPrefs(context)
                    AuthManager.getUserId()?.let { userId ->

                        coroutineScope.launch {
                            val userName = welcomePageViewModel.getUserNameById(userId.toInt())

                            Toast.makeText(context, "Welcome Back $userName!", Toast.LENGTH_LONG).show()
                            context.startActivity(Intent(context, FoodQuestionnaire::class.java))

                            welcomePageViewModel.updateUserNameState(userName)
                        }
                    } ?: run {
                        Toast.makeText(context, "User Not Logged In Before", Toast.LENGTH_LONG).show()
                    }
                },
                modifier = Modifier.fillMaxWidth().padding(16.dp).height(50.dp)
            ) {
                Text("Continue With Previous Logged User")
            }




            Spacer(modifier = Modifier.height(70.dp))


            // TextView with Student Name + ID
            Text(
                text = "Developed by: King Jian Rong (34693807)",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
            )
        }

    }

}













