package com.jr2081.jianrong34693807

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.jr2081.jianrong34693807.data.viewModels.ClinicianDashboardViewModel
import com.jr2081.jianrong34693807.data.viewModels.ClinicianLoginViewModel
import com.jr2081.jianrong34693807.data.viewModels.FoodQualityScoreViewModel
import com.jr2081.jianrong34693807.data.viewModels.InsightScreenViewModel
import com.jr2081.jianrong34693807.data.viewModels.NutriCoachVIewModel
import com.jr2081.jianrong34693807.data.viewModels.SettingsScreenViewModel
import com.jr2081.jianrong34693807.ui.pageUi.ClinicalLoginScreen
import com.jr2081.jianrong34693807.ui.pageUi.ClinicianDashboardScreen
import com.jr2081.jianrong34693807.ui.pageUi.FoodQualityScorePage
import com.jr2081.jianrong34693807.ui.pageUi.InsightScreenPage
import com.jr2081.jianrong34693807.ui.pageUi.NutriCoachScreenUI
import com.jr2081.jianrong34693807.ui.pageUi.SettingsScreenPage
import com.jr2081.jianrong34693807.ui.theme.JianRong34693807Theme


import androidx.compose.foundation.layout.size
import androidx.navigation.compose.currentBackStackEntryAsState
import com.jr2081.jianrong34693807.data.viewModels.RankingViewModel
import com.jr2081.jianrong34693807.ui.pageUi.RankScreenUI


class Dashboard : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JianRong34693807Theme {
                val navController = rememberNavController()

                val context = LocalContext.current

                // Generate All the View Models for each screen
                val foodQualityScoreViewModel: FoodQualityScoreViewModel = viewModel(
                    factory = FoodQualityScoreViewModel.FoodQualityScoreViewModelFactory(context)
                )

                val insightScreenViewModel: InsightScreenViewModel = viewModel(
                    factory = InsightScreenViewModel.InsightScreenViewModelFactory(context)
                )

                val settingsScreenViewModel: SettingsScreenViewModel = viewModel(
                    factory = SettingsScreenViewModel.SettingsScreenViewModelFactory(context)
                )

                val nutriCoachVIewModel: NutriCoachVIewModel = viewModel(
                    factory = NutriCoachVIewModel.NutriCoachVIewModelFactory(context)
                )

                val clinicianLoginViewModel: ClinicianLoginViewModel = viewModel(
                    factory = ClinicianLoginViewModel.ClinicianLoginViewModelFactory(context)
                )

                val clinicianDashboardViewModel: ClinicianDashboardViewModel = viewModel(
                    factory = ClinicianDashboardViewModel.ClinicianDashboardViewModelFactory(context)
                )

                val rankingViewModel: RankingViewModel = viewModel(
                    factory = RankingViewModel.RankingViewModelFactory(context)
                )


                Scaffold( modifier = Modifier.fillMaxSize(),
                    bottomBar = { NutriBottomAppBar(navController)}
                    ) { innerPadding ->


                    DashboardNavHostSelecter(innerPadding ,navController, foodQualityScoreViewModel, insightScreenViewModel,nutriCoachVIewModel, settingsScreenViewModel, clinicianLoginViewModel,clinicianDashboardViewModel, rankingViewModel ,  modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}



@Composable
fun DashboardNavHostSelecter(
    innerPaddingValues: PaddingValues,
    navController: NavHostController,  // Navigation controller to handle screen transitions
    foodQualityScoreViewModel: FoodQualityScoreViewModel,
    insightScreenViewModel : InsightScreenViewModel,
    nutriCoachVIewModel: NutriCoachVIewModel,
    settingsScreenViewModel: SettingsScreenViewModel,
    clinicianLoginViewModel: ClinicianLoginViewModel,
    clinicianDashboardViewModel: ClinicianDashboardViewModel,
    rankingViewModel: RankingViewModel,
    modifier: Modifier = Modifier  // Optional modifier for customizing layout
) {

    NavHost(
        navController = navController,
        startDestination = "Home",
        modifier = modifier
    ) {
        composable("Home") {
            FoodQualityScorePage(innerPaddingValues, navController, foodQualityScoreViewModel)
        }
        composable("Insights") {
            InsightScreenPage(navController, insightScreenViewModel)
        }
        composable("NutriCoach") {
            NutriCoachScreenUI(navController, nutriCoachVIewModel)
        }

        composable("Ranking") {
            RankScreenUI(navController, rankingViewModel)
        }

        navigation(
            startDestination = "SettingsMain",
            route = "Settings"
        ) {
            composable("SettingsMain") {
                SettingsScreenPage(navController, settingsScreenViewModel)
            }
            composable("ClinicalLogin") {
                ClinicalLoginScreen(navController, clinicianLoginViewModel) // Your clinical login screen
            }

            composable("ClinicalView") {
                ClinicianDashboardScreen(navController, clinicianDashboardViewModel) // Your clinical view screen
            }
        }

    }


}



data class BottomNavItem(
    val title: String,
    val iconVector: ImageVector? = null,
    val iconRes: Int? = null // for R.drawable resource
)


@Composable
fun NutriBottomAppBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val items = listOf(
        BottomNavItem("Home", iconVector = Icons.Filled.Home),
        BottomNavItem("Insights", iconRes = R.drawable.baseline_insights_24),
        BottomNavItem("NutriCoach", iconRes = R.drawable.baseline_person),
        BottomNavItem("Ranking", iconRes = R.drawable.baseline_analytics_24),
        BottomNavItem("Settings", iconVector = Icons.Filled.Settings)
    )

    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                icon = {
                    when {
                        item.iconVector != null -> Icon(
                            imageVector = item.iconVector,
                            contentDescription = item.title,
                            modifier = Modifier.size(24.dp)
                        )
                        item.iconRes != null -> Icon(
                            painter = painterResource(id = item.iconRes),
                            contentDescription = item.title,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                },
                label = { Text(item.title) },
                selected = currentRoute == item.title,
                onClick = {
                    // Avoid re-navigation if already selected
                    if (currentRoute != item.title) {
                        navController.navigate(item.title)
                    }
                }
            )
        }
    }
}





