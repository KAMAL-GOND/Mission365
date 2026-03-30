package com.example.mission365.Screens.navigation

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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
    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    appVeiwModel.a,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    appVeiwModel.AddContext,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    101
                )
            }
        }
    }
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