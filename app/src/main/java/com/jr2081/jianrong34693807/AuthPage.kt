package com.jr2081.jianrong34693807

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jr2081.jianrong34693807.data.viewModels.AuthViewModel
import com.jr2081.jianrong34693807.data.patient.Patient
import com.jr2081.jianrong34693807.ui.theme.JianRong34693807Theme
import kotlinx.coroutines.flow.Flow



class AuthPage : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JianRong34693807Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()

                    val context = LocalContext.current
                    val authViewModel: AuthViewModel = viewModel(
                        factory = AuthViewModel.AuthViewModelFactory(context)
                    )

                    AuthNavHostSelecter(navController = navController, authViewModel,  modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}




// LoginScreenLayout
@Composable
fun LoginScreenLayout(navController: NavHostController, authViewModel: AuthViewModel) {

    val context = LocalContext.current

    authViewModel.resetSelectedId()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1565C0)),
        contentAlignment = Alignment.BottomCenter
    ) {
        Card(
            modifier = Modifier
                .height(775.dp)
                .fillMaxSize(),
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(modifier = Modifier.height(28.dp))
                Text(
                    modifier = Modifier.padding(14.dp),
                    text = "Log in",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                )

                // Pass the selected ID and valid number to the dropdown menu
                // When the selected ID is passed to the dropdown, we will straightaway extract the valid number from th CSV. using onSelectChange
                MyDropdownMenuLogin(
                    userDetails = authViewModel.allPatient,
                    selectedId = authViewModel.selectedId,
                    onSelectedChange = { newId, newPassword, validPhoneNumber ,isRegistered ->
                        authViewModel.selectedId = newId
                        authViewModel.validPassword = newPassword
                        authViewModel.validNumber = validPhoneNumber
                        authViewModel.isRegisteredLogin = isRegistered }
                )

                // Text holder for number
                OutlinedTextField(
                    value = authViewModel.userInputPassword,
                    onValueChange = { authViewModel.userInputPassword = it },
                    label = { Text("Enter your Password") },
                    modifier = Modifier.padding(14.dp),
                )


                Text(
                    text = "This app is only for pre-registered users. Please have your ID and phone number handy before continuing.",
                    modifier = Modifier.padding(horizontal = 14.dp),
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    lineHeight = 18.sp
                )



                Button(
                    modifier = Modifier
                        .padding(14.dp)
                        .padding(horizontal = 20.dp)
                        .fillMaxWidth(),
                    onClick = {

                        authViewModel.validateLogin(context)

                    }
                ) {
                    Text("Login")
                }

                Button(
                    modifier = Modifier
                        .padding(14.dp)
                        .padding(horizontal = 20.dp)
                        .fillMaxWidth(),
                    onClick = {
                            navController.navigate("patient_register")
                    }
                ) {
                    Text("Register")
                }
            }
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyDropdownMenuLogin(
    userDetails: Flow<List<Patient>>,
    selectedId: String,
    onSelectedChange: (String, String, String, Boolean) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    // Convert Flow to State
    val patients by userDetails.collectAsState(initial = emptyList())

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        TextField(
            value = selectedId,
            onValueChange = {},
            readOnly = true,
            label = { Text("My ID (Provided by your Clinician)") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier.menuAnchor()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            patients.forEach { patient ->
                DropdownMenuItem(
                    text = { Text(text = patient.userID.toString()) },
                    onClick = {
                        onSelectedChange(patient.userID.toString(), patient.password, patient.phoneNumber, patient.isRegistered)
                        expanded = false
                    }
                )
            }
        }
    }
}







@Composable
fun AuthNavHostSelecter(
    navController: NavHostController,  // Navigation controller to handle screen transitions
    authViewModel: AuthViewModel,
    modifier: Modifier = Modifier  // Optional modifier for customizing layout
) {

    // Set up navigation host to manage different login screens
    NavHost(
        navController = navController,
        startDestination = "patient_login",
        modifier = modifier
    ) {
        // Define the Staff Login screen destination
        composable("patient_login") {

            // Display the login interface
            LoginScreenLayout(navController, authViewModel)
        }
        // Define the Student Login screen destination
        composable("patient_register") {

            // Display the register screen
            RegisterScreenLayout(navController, authViewModel)
        }
    }
}






@Composable
fun RegisterScreenLayout(navController: NavHostController, authViewModel: AuthViewModel) {



    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1565C0)),
        contentAlignment = Alignment.BottomCenter
    ) {
        Card(
            modifier = Modifier
                .height(775.dp)
                .fillMaxSize(),
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(modifier = Modifier.height(28.dp))
                Text(
                    modifier = Modifier.padding(14.dp),
                    text = "Register",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                )

                // Pass the selected ID and valid number to the dropdown menu
                // When the selected ID is passed to the dropdown, we will straightaway extract the valid number from th CSV. using onSelectChange
                MyDropdownMenuLogin(
                    userDetails = authViewModel.allPatient,
                    selectedId = authViewModel.selectedId,
                    onSelectedChange = { newId, newPassword, validPhoneNumber ,isRegistered ->
                        authViewModel.selectedId = newId
                        authViewModel.validPassword = newPassword
                        authViewModel.validNumber = validPhoneNumber
                        authViewModel.isRegisteredLogin = isRegistered }
                )


                // Text holder for Name input
                OutlinedTextField(
                    value = authViewModel.userName,
                    onValueChange = { authViewModel.userName = it },
                    label = { Text("Enter Your Name Here") },
                    modifier = Modifier.padding(14.dp),
                )



                // Text holder for number
                OutlinedTextField(
                    value = authViewModel.phoneNumber,
                    onValueChange = { authViewModel.phoneNumber = it },
                    label = { Text("Enter your phone number") },
                    modifier = Modifier.padding(14.dp),
                )


                OutlinedTextField(
                    value = authViewModel.password,
                    onValueChange = { authViewModel.password = it },
                    label = { Text("Enter your password") },
                    modifier = Modifier.padding(14.dp),
                )



                OutlinedTextField(
                    value = authViewModel.confirmPassword,
                    onValueChange = { authViewModel.confirmPassword = it },
                    label = { Text("Enter your password again") },
                    modifier = Modifier.padding(14.dp),
                )



                Text(
                    text = "This app is only for pre-registered users. Please have your ID and phone number handy before continuing.",
                    modifier = Modifier.padding(horizontal = 14.dp),
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    lineHeight = 18.sp
                )


                Button(
                    modifier = Modifier
                        .padding(14.dp)
                        .padding(horizontal = 20.dp)
                        .fillMaxWidth(),
                    onClick = {

                        authViewModel.validateRegister(navController ,context)



                    }
                ) {
                    Text("Register")
                }



                Button(
                    modifier = Modifier
                        .padding(14.dp)
                        .padding(horizontal = 20.dp)
                        .fillMaxWidth(),
                    onClick = {
                        navController.navigate("patient_login")
                    }
                ) {
                    Text("Login")
                }

            }
        }
    }
}









