package com.jr2081.jianrong34693807.ui.pageUi

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.jr2081.jianrong34693807.util.ClinicalPassword
import com.jr2081.jianrong34693807.data.viewModels.ClinicianLoginViewModel



@Composable
fun ClinicalLoginScreen(navController: NavHostController, clinicianLoginViewModel: ClinicianLoginViewModel) {


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {

        Column(
            modifier = Modifier.fillMaxWidth().padding(14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Clinician Login",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )

            Spacer(modifier = Modifier.height(60.dp))

            OutlinedTextField(
                value = clinicianLoginViewModel.userInputKey,
                onValueChange = { clinicianLoginViewModel.userInputKey = it },
                label = { Text("Enter your clinician key") },
                modifier = Modifier.padding(14.dp),
            )


            val context = LocalContext.current

            Button(
                modifier = Modifier
                    .padding(14.dp)
                    .padding(horizontal = 10.dp)
                    .fillMaxWidth()
                    .height(40.dp)
                ,
                onClick = {

                    if (clinicianLoginViewModel.userInputKey.isEmpty()) {
                        Toast.makeText(context, "Input Key Cannot be Empty", Toast.LENGTH_LONG).show()
                    } else if (clinicianLoginViewModel.userInputKey != ClinicalPassword.CLINICALPASSWORDSTRING) {
                        Toast.makeText(context, "Invalid access key - please verify and try again", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(context, "Login Success", Toast.LENGTH_LONG).show()
                        navController.navigate("ClinicalView")
                    }

                }
            ) {
                Text("Clinician Login")
            }

        }


    }

}


// dummy preview screen
@Preview(showBackground = true)
@Composable
fun PreviewClinicalViewScreen() {
    val context = LocalContext.current
    val navController = rememberNavController()
    val clinicianLoginViewModel: ClinicianLoginViewModel = viewModel(
        factory = ClinicianLoginViewModel.ClinicianLoginViewModelFactory(context)
    )
    ClinicalLoginScreen(navController, clinicianLoginViewModel )
}