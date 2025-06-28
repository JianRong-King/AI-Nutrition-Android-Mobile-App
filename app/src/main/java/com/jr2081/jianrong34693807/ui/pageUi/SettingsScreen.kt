package com.jr2081.jianrong34693807.ui.pageUi

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.jr2081.jianrong34693807.AuthPage
import com.jr2081.jianrong34693807.R
import com.jr2081.jianrong34693807.data.AuthManager
import com.jr2081.jianrong34693807.data.viewModels.SettingsScreenViewModel


@Composable
fun SettingsScreenPage(navController: NavHostController, settingsScreenViewModel: SettingsScreenViewModel) {


    AuthManager.getUserId()?.let { settingsScreenViewModel.updatePatientNameNumber(it.toInt()) }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start // Align content to start (left)
    ) {

        Column(modifier = Modifier.fillMaxWidth().padding(14.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Settings",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            horizontalAlignment = Alignment.Start // Align inner content to start
        ) {

            Spacer(Modifier.height(18.dp))

            Text(
                text = "ACCOUNT",
                fontWeight = FontWeight.Bold,
                color = Color.Gray,
                fontSize = 14.sp
            )

            Spacer(Modifier.height(26.dp))


            AccountInfoRow(R.drawable.baseline_person,
                settingsScreenViewModel.patientName
            )

            Spacer(modifier = Modifier.height(12.dp))


            AccountInfoRow(R.drawable.baseline_add_call_24,
                settingsScreenViewModel.patientPhoneNumber
            )

            Spacer(modifier = Modifier.height(12.dp))

            AccountIdInfoRow(R.drawable.baseline_userid_icon_24, AuthManager.getUserId())

            Spacer(Modifier.height(26.dp))

            Divider(
                color = Color.LightGray,
                thickness = 1.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            )

            Spacer(Modifier.height(26.dp))


            Text(
                text = "OTHER SETTINGS",
                fontWeight = FontWeight.Bold,
                color = Color.Gray,
                fontSize = 14.sp
            )

            Spacer(Modifier.height(26.dp))

            val context = LocalContext.current

            OtherSettingButton(
                onSettingClick = {
                    AuthManager.logout(context)
                    context.startActivity(Intent(context, AuthPage::class.java))
                    // End current Screen
                },
                settingLogo = R.drawable.baseline_arrow_logout_24,
                settingText = "Log out"
            )


            Spacer(modifier = Modifier.height(20.dp))

            OtherSettingButton(onSettingClick = {
                // Go to Clinician screen

                navController.navigate("ClinicalLogin")
            }, R.drawable.baseline_person, "Clinician Login" )

        }
    }
}



@Composable
fun OtherSettingButton(
    onSettingClick: () -> Unit,
    settingLogo: Int,
    settingText: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(30.dp)
            .clickable { onSettingClick() }
        ,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(settingLogo),
            contentDescription = "Setting Icon",
            modifier = Modifier
                .size(24.dp)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = settingText,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.weight(1f)) // Push arrow to the end

        Image(
            painter = painterResource(id = R.drawable.baseline_arrow_forward_grey),
            contentDescription = "Arrow Icon",
            modifier = Modifier
                .size(20.dp)
                .align(Alignment.CenterVertically)
        )

        Spacer(modifier = Modifier.width(8.dp))

    }
}


@Composable
fun AccountIdInfoRow(iconResId: Int, label: String?) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = iconResId),
            contentDescription = "$label Icon",
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(20.dp))
        if (label != null) {
            Text(label)
        }
    }
}

@Composable
fun AccountInfoRow(iconResId: Int, label: MutableState<String>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = iconResId),
            contentDescription = "${label.value} Icon",
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(20.dp))
        Text(text = label.value)
    }
}



// dummy preview screen
@Preview(showBackground = true)
@Composable
fun PreviewSettingsScreenPage() {
    val context = LocalContext.current
    val navController = rememberNavController()
    val settingsScreenViewModel: SettingsScreenViewModel = viewModel(
        factory = SettingsScreenViewModel.SettingsScreenViewModelFactory(context)
    )
    SettingsScreenPage(navController,settingsScreenViewModel )
}