package com.example.mission365.Screens.navigation

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mission365.Screens.AgeCalenderScreen
import com.example.mission365.Screens.CustomCalenderScreen
import com.example.mission365.Screens.HomeScreen
import com.example.mission365.Screens.YearCalenderScreen
import com.example.mission365.veiwModel

@Composable
fun navApp(appVeiwModel : veiwModel,){
    var navController = rememberNavController()
    Scaffold() { pa->
    NavHost(navController, startDestination = Routes.HomeScreen){
        composable <Routes.HomeScreen>{
            HomeScreen(appVeiwModel,navController,pa)


        }
        composable <Routes.AgeScreen>{
            AgeCalenderScreen(appVeiwModel,navController)

        }
        composable <Routes.YearScreen>{
            YearCalenderScreen(appVeiwModel,navController)

        }
        composable <Routes.CustomScreen>{
            CustomCalenderScreen(appVeiwModel,navController)

        }
    }
    }
}