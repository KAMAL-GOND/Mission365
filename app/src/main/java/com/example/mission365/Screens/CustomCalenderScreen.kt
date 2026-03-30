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
import androidx.compose.runtime.mutableIntStateOf
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
import com.example.mission365.AppButton
import com.example.mission365.BannerAdId
import com.example.mission365.BannerUtils.BannerAd
import com.example.mission365.CustomizedImage
import com.example.mission365.IntesAdId
import com.example.mission365.Status
import com.example.mission365.calculateGrid
import com.example.mission365.veiwModel
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.temporal.ChronoUnit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomCalenderScreen(viewModel: veiwModel, navController: NavHostController){
    val adView = remember {
        AdView(viewModel.a).apply {
            adUnitId = BannerAdId
            setAdSize(AdSize.BANNER)
            loadAd(AdRequest.Builder().build())
        }
    }
    var interstitialAd: InterstitialAd?=null
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

    val workerStatus by viewModel.WorkerCoustomStatus.collectAsState()
    var context =LocalContext.current
    var startDate by remember { mutableStateOf<LocalDate?>(null)  }
    var endDate by remember { mutableStateOf<LocalDate?>(null)  }
    var CalenderState1 by remember { mutableStateOf(false) }
    var CalenderState2 by remember { mutableStateOf(false) }

    var lines by remember { mutableIntStateOf(5) }




    LaunchedEffect(workerStatus) {

        when (workerStatus) {

            Status.SUCCESS  -> {
                Toast.makeText(context, "Done", Toast.LENGTH_LONG).show()
                viewModel.ResetRemoveCustomWorkerStatus()
                navController.popBackStack()
            }

            Status.ERROR  -> {
                Toast.makeText(context, "Some error occurred", Toast.LENGTH_LONG).show()
                viewModel.ResetRemoveCustomWorkerStatus()
                navController.popBackStack()
            }

            Status.IDLE -> {}
        }
    }

    if(startDate != null && endDate != null){
        if(startDate!!>=endDate!!){
            Toast.makeText(context,"End Date Should be bigger then start date", Toast.LENGTH_LONG).show()
        }
        else{
        var days = ChronoUnit.DAYS.between(startDate,endDate)
         var a = calculateGrid(days.toInt(),viewModel.a)

        var image: ImageBitmap= CustomizedImage(startDate=startDate!!,endDate=endDate!!,a.first,a.second,
            viewModel.a)
        androidx.compose.foundation.Image(image,"null")
        Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally,) {
            BannerAd(adView)
            Spacer(Modifier.fillMaxHeight(0.7f))
            Button(onClick = {
                viewModel.addCustomHome(StartDate=startDate!!,EndDate=endDate!!,a.first,a.second,
                    image.asAndroidBitmap())
                interstitialAd?.show(viewModel.AddContext)


            }) {
                Text("Apply HomeScreen")
            }

            Button(onClick = {
                viewModel.addCustomLock(StartDate=startDate!!,EndDate=endDate!!,lines,(days/lines).toInt()+1,
                    image.asAndroidBitmap())
                interstitialAd?.show(viewModel.AddContext)

            }) {
                Text("Apply LockScreen")
            }
        }}
    }

    else{
        var SecondDate1 by remember { mutableStateOf<LocalDate?>(null)  }
        var SecondDate2 by remember { mutableStateOf<LocalDate?>(null)  }
        if(CalenderState1==true){
            val datePickerState = rememberDatePickerState()


            DatePickerDialog(
                onDismissRequest = {CalenderState1=false},
                confirmButton = {
                    TextButton(onClick = {
                        SecondDate1= Instant.ofEpochMilli(datePickerState.selectedDateMillis!!).atZone(
                            ZoneId.systemDefault()).toLocalDate()
                        CalenderState1=false
                    }) {
                        Text("OK")
                    }
                },
                dismissButton = {
                    TextButton(onClick = {CalenderState1= false}) {
                        Text("Cancel")
                    }
                }
            ) {
                DatePicker(state = datePickerState)
            }
        }
        if(CalenderState2==true){
            val datePickerState = rememberDatePickerState()


            DatePickerDialog(
                onDismissRequest = {CalenderState2=false},
                confirmButton = {
                    TextButton(onClick = {
                        SecondDate2= Instant.ofEpochMilli(datePickerState.selectedDateMillis!!).atZone(
                            ZoneId.systemDefault()).toLocalDate()
                        CalenderState2=false
                    }) {
                        Text("OK")
                    }
                },
                dismissButton = {
                    TextButton(onClick = {CalenderState2= false}) {
                        Text("Cancel")
                    }
                }
            ) {
                DatePicker(state = datePickerState)
            }
        }
        Column(modifier = Modifier.fillMaxSize().padding(10.dp).background(Brush.verticalGradient(
            listOf(Color(1111)
                ,Color(2222)
                ,Color.Black)
        )), horizontalAlignment = Alignment.CenterHorizontally,){
            BannerAd(adView)
            var height= viewModel.a.resources.displayMetrics.heightPixels
            Spacer(Modifier.height((height*0.4).dp))

            TextField(value = SecondDate1.toString(), onValueChange = {}, readOnly = true, leadingIcon = {Icon(Icons.Default.DateRange,"null")},
                placeholder = {Text("Start Date")}, label = {Text("start Date")}, enabled = false,modifier = Modifier.fillMaxWidth(0.5f).clickable(
                    onClick = {
                        CalenderState1=true
                    }
                ))
            Spacer(modifier = Modifier.height(10.dp))
            TextField(value = SecondDate2.toString(), onValueChange = {}, readOnly = true, leadingIcon = {Icon(Icons.Default.DateRange,"null")},
                placeholder = {Text("end Date")}, label = {Text("end Date")}, enabled = false,modifier = Modifier.fillMaxWidth(0.5f).clickable(
                    onClick = {
                        CalenderState2=true
                    }
                ))
            Spacer(modifier = Modifier.height(10.dp))
//            TextField(value = lines.toString(), onValueChange = {lines = it.toInt()}, leadingIcon = {Icon(Icons.Default.Create,"null")}, readOnly = true,
//                placeholder = {Text("Lines")}, label = {Text("Lines")}, enabled = true, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number) ,modifier = Modifier.fillMaxWidth(0.7f))


            AppButton("Generate" ,{
                if(SecondDate1 ==null || SecondDate2 == null){
                Toast.makeText(context,"Dates Can not be empty", Toast.LENGTH_LONG).show()
            }
                else{
                startDate=SecondDate1
                endDate=SecondDate2

            }})

        }

    }

}