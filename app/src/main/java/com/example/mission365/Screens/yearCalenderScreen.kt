package com.example.mission365.Screens

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.mission365.BannerAdId
import com.example.mission365.BannerUtils.BannerAd
import com.example.mission365.CreateWallpaper
import com.example.mission365.Status
import com.example.mission365.veiwModel
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

@Composable
fun YearCalenderScreen(viewModel: veiwModel, navController: NavHostController){
    //add

    val adView = remember { 
        AdView(viewModel.a).apply {
            adUnitId = "ca-app-pub-3940256099942544/9214589741"
            setAdSize(AdSize.BANNER)
            loadAd(AdRequest.Builder().build())
        }
    }
    
    var interstitialAd: InterstitialAd?=null

    // AD2
    InterstitialAd.load(
        /* context = */ viewModel.a,
        /* adUnitId = */ "ca-app-pub-3940256099942544/1033173712",
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





    var image: ImageBitmap=CreateWallpaper(viewModel.a)
    val workerStatus by viewModel.WorkerYearStatus.collectAsState()
    var context =LocalContext.current

    LaunchedEffect(workerStatus) {

        when (workerStatus) {

            Status.SUCCESS  -> {
                Toast.makeText(context, "Done", Toast.LENGTH_LONG).show()

                viewModel.ResetRemoveYearWorkerStatus()
                navController.popBackStack()
            }

            Status.ERROR  -> {
                Toast.makeText(context, "Some error occurred", Toast.LENGTH_LONG).show()
                viewModel.ResetRemoveYearWorkerStatus()
            }

            Status.IDLE -> {}
        }
    }
    androidx.compose.foundation.Image(image,"null")
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally,) {
        BannerAd(adView, Modifier.border(2.dp, Color.White))
        Spacer(Modifier.fillMaxHeight(0.7f))
        Button(onClick = {
            viewModel.addYearHome(image.asAndroidBitmap())
            interstitialAd?.show(viewModel.AddContext)

        }) {
            Text("Apply HomeScreen")
        }

        Button(onClick = {
            viewModel.addYearLock(image.asAndroidBitmap())
            interstitialAd?.show(viewModel.AddContext)

        }) {
            Text("Apply LockScreen")
        }
    }
}