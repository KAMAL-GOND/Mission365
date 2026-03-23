package com.example.mission365.Screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.BrushPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.mission365.AppButton
import com.example.mission365.R
import com.example.mission365.Screens.navigation.Routes
import com.example.mission365.Status
import com.example.mission365.veiwModel

@Composable
fun HomeScreen(viewModel: veiwModel, navController: NavHostController){
    val context = LocalContext.current
    val workerStatus by viewModel.WorkerRemoveStatus.collectAsState()

    LaunchedEffect(workerStatus) {

        when (workerStatus) {

            Status.SUCCESS  -> {
                Toast.makeText(context, "Done", Toast.LENGTH_LONG).show()
                viewModel.ResetRemoveWorkerStatus()
            }

            Status.ERROR  -> {
                Toast.makeText(context, "Some error occurred", Toast.LENGTH_LONG).show()
                viewModel.ResetRemoveWorkerStatus()
            }

            Status.IDLE -> {}
        }
    }
    Column(modifier = Modifier.fillMaxSize().background(brush = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF8B0000),
            Color(0xFFA42828),
            Color.Black
        )
    ))) {

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxWidth().fillMaxHeight(0.75f),
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                item { HomeScreenObject(viewModel,R.drawable.year,"Year Wallpaper",{navController.navigate(Routes.YearScreen)}) }
                item { HomeScreenObject(viewModel,R.drawable.age,"Age Wallpaper",{navController.navigate(Routes.AgeScreen)}) }
                item { HomeScreenObject(viewModel,R.drawable.custom,"Custom Wallpaper",{navController.navigate(Routes.CustomScreen)}) }



            }

        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally){



//            Button(onClick = {
//                viewModel.removeHome()
//
//            }) {
//                Text("Remove HomeScreen")
//            }
            AppButton("Remove HomeScreen",{viewModel.removeHome()})
            AppButton("Remove LockScreen",{viewModel.removeLock()})

//            Button(onClick = {
//                viewModel.removeLock()
//
//            }) {
//                Text("Remove LockScreen")
//            }

        }

    }
}

//data class HomeScreenObject(
//    var name : String,
//
//)

@Composable
fun HomeScreenObject(veiwModel: veiwModel, image: Int, name:String, Click:()->Unit){
    Column(modifier = Modifier.width(200.dp).height(300.dp).clickable(onClick = Click).padding(horizontal = 5.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceEvenly) {
        Image(painter = painterResource(image),"null", modifier = Modifier.fillMaxWidth().fillMaxHeight(0.8f), contentScale = ContentScale.FillBounds)

        Text(name.toString())
    }

}