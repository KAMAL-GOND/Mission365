package com.example.mission365.Screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.BrushPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.mission365.AppButton
import com.example.mission365.IntesAdId
import com.example.mission365.R
import com.example.mission365.Screens.navigation.Routes
import com.example.mission365.Status
import com.example.mission365.veiwModel
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

@Composable
fun HomeScreen(viewModel: veiwModel, navController: NavHostController,paddingValues: PaddingValues){
    val context = LocalContext.current
    val workerStatus by viewModel.WorkerRemoveStatus.collectAsState()
    var interstitialAd: InterstitialAd?=null

    // AD2
    InterstitialAd.load(
        /* context = */ viewModel.a,
        /* adUnitId = */ IntesAdId,
        /* adRequest = */ AdRequest.Builder().build(),
        /* loadCallback = */
        object : InterstitialAdLoadCallback() {
            override fun onAdLoaded(ad: InterstitialAd) {
                Log.d("intesAdd", "Ad was loaded.")
                interstitialAd = ad
            }

            override fun onAdFailedToLoad(adError: LoadAdError) {
                Log.d("intesAddAFail", adError.message)
                interstitialAd = null
            }
        },
    )

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
    )).padding(paddingValues)) {

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxWidth().fillMaxHeight(0.80f),
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
            AppButton("Remove HomeScreen",{viewModel.removeHome();interstitialAd?.show(viewModel.AddContext)})
            AppButton("Remove LockScreen",{viewModel.removeLock();interstitialAd?.show(viewModel.AddContext)})

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
        Image(painter = painterResource(image),"null", modifier = Modifier.fillMaxWidth().fillMaxHeight(0.8f).clip(
            RoundedCornerShape(10.dp)
        ), contentScale = ContentScale.FillBounds)

        Text(name.toString())
    }

}