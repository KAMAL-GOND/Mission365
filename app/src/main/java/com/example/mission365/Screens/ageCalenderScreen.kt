package com.example.mission365.Screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.work.impl.PRUNE_THRESHOLD_MILLIS
import com.example.mission365.AppButton
import com.example.mission365.Appid
import com.example.mission365.BannerAdId
import com.example.mission365.BannerUtils.BannerAd
import com.example.mission365.CreateAgeWallpaper
//import com.example.mission365.IntesAd
import com.example.mission365.IntesAdId
import com.example.mission365.Status
import com.example.mission365.veiwModel
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import kotlinx.coroutines.time.delay
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgeCalenderScreen(viewModel: veiwModel, navController: NavHostController){
    val adView = remember {
        AdView(viewModel.a).apply {
            adUnitId = BannerAdId
            setAdSize(AdSize.BANNER)
            loadAd(AdRequest.Builder().build())
        }
    }

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

    val workerStatus by viewModel.WorkerAgeStatus.collectAsState()
    var context =LocalContext.current
    var Date by remember { mutableStateOf<LocalDate?>(null)  }
    var CalenderState by remember { mutableStateOf(false) }

    LaunchedEffect(workerStatus) {

        when (workerStatus) {

            Status.SUCCESS  -> {
                Toast.makeText(context, "Done", Toast.LENGTH_LONG).show()
                viewModel.ResetRemoveAgeWorkerStatus()
                navController.popBackStack()
            }

            Status.ERROR  -> {
                Toast.makeText(context, "Some error occurred", Toast.LENGTH_LONG).show()
                viewModel.ResetRemoveAgeWorkerStatus()
                navController.popBackStack()
            }

            Status.IDLE -> {}
        }
    }

    if(Date != null){
        var image: ImageBitmap= CreateAgeWallpaper(viewModel.a,Date!!)
        androidx.compose.foundation.Image(image,"null")
        Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally,) {
            BannerAd(adView)

            Spacer(Modifier.fillMaxHeight(0.7f))
            Button(onClick = {
                viewModel.addLifeHome(Date!!,image.asAndroidBitmap())

                interstitialAd?.show(viewModel.AddContext)

            }) {
                Text("Apply HomeScreen")
            }

            Button(onClick = {
                viewModel.addLifeLock(Date!!,image.asAndroidBitmap())
                interstitialAd?.show(viewModel.AddContext)

            }) {
                Text("Apply LockScreen")
            }
        }
    }

    else{
        var SecondDate by remember { mutableStateOf<LocalDate?>(null)  }
        if(CalenderState==true){
            val datePickerState = rememberDatePickerState(

                initialDisplayMode = DisplayMode.Picker
            )


            DatePickerDialog(
                onDismissRequest = {CalenderState=false},
                confirmButton = {
                    TextButton(onClick = {
                        if(datePickerState.selectedDateMillis !=null){
                            SecondDate= Instant.ofEpochMilli(datePickerState.selectedDateMillis!!).atZone(
                                ZoneId.systemDefault()).toLocalDate();
                        }
                        else{Toast.makeText(context,"Please select date", Toast.LENGTH_LONG).show()}

                             CalenderState=false
                    }) {
                        Text("OK")
                    }
                },
                dismissButton = {
                    TextButton(onClick = {CalenderState= false}) {
                        Text("Cancel")
                    }
                }
            ) {
                DatePicker(state = datePickerState)
            }
        }
        Column(modifier = Modifier.fillMaxSize().background(Brush.verticalGradient(
            listOf(Color(0xFF626262),
                Color(0xFF2F2F2F)
                ,Color.Black)
        )), horizontalAlignment = Alignment.CenterHorizontally,){
            BannerAd(adView)
            Column(Modifier.fillMaxSize().padding(10.dp),horizontalAlignment=Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center){



            TextField(value = SecondDate.toString(), onValueChange = {}, readOnly = true, leadingIcon = {Icon(Icons.Default.DateRange,"null")},
                placeholder = {Text("Birth Date")}, label = {Text("Birth Date")}, enabled = false,modifier = Modifier.fillMaxWidth(0.7f).clickable(
                    onClick = {
                        CalenderState=true
                    }
                ))
            Spacer(modifier = Modifier.height(10.dp))

//            Button(onClick = {
//                if(SecondDate ==null){
//                Toast.makeText(context,"BirthDAte Can not be empty", Toast.LENGTH_LONG).show()
//            }
//            else{
//                Date=SecondDate
//
//            }}) {
//                Text("Generate")
//            }
            AppButton("Generate", onClick ={
                if(SecondDate ==null){
                    Toast.makeText(context,"BirthDAte Can not be empty", Toast.LENGTH_LONG).show()
                }
                else{
                    Date=SecondDate

                }} )
        }

    }}

}