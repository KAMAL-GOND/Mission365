package com.example.mission365.Screens

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.unit.dp
import com.example.mission365.CreateAgeWallpaper
import com.example.mission365.CreateWallpaper
import com.example.mission365.Status
import com.example.mission365.veiwModel
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgeCalenderScreen(viewModel: veiwModel){

    val workerStatus by viewModel.WorkerAgeStatus.collectAsState()
    var context =LocalContext.current
    var Date by remember { mutableStateOf<LocalDate?>(null)  }
    var CalenderState by remember { mutableStateOf(false) }

    LaunchedEffect(workerStatus) {

        when (workerStatus) {

            Status.SUCCESS  -> {
                Toast.makeText(context, "Done", Toast.LENGTH_LONG).show()
                viewModel.ResetRemoveAgeWorkerStatus()
            }

            Status.ERROR  -> {
                Toast.makeText(context, "Some error occurred", Toast.LENGTH_LONG).show()
                viewModel.ResetRemoveAgeWorkerStatus()
            }

            Status.IDLE -> {}
        }
    }

    if(Date != null){
        var image: ImageBitmap= CreateAgeWallpaper(viewModel.a,Date!!)
        androidx.compose.foundation.Image(image,"null")
        Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally,) {
            Spacer(Modifier.fillMaxHeight(0.7f))
            Button(onClick = {
                viewModel.addLifeHome(Date!!,image.asAndroidBitmap())

            }) {
                Text("Apply HomeScreen")
            }

            Button(onClick = {
                viewModel.addLifeLock(Date!!,image.asAndroidBitmap())

            }) {
                Text("Apply LockScreen")
            }
        }
    }

    else{
        var SecondDate by remember { mutableStateOf<LocalDate?>(null)  }
        if(CalenderState==true){
            val datePickerState = rememberDatePickerState()


            DatePickerDialog(
                onDismissRequest = {CalenderState=false},
                confirmButton = {
                    TextButton(onClick = {
                        SecondDate= Instant.ofEpochMilli(datePickerState.selectedDateMillis!!).atZone(
                            ZoneId.systemDefault()).toLocalDate()
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
        Column(modifier = Modifier.fillMaxSize().padding(10.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center){

            TextField(value = SecondDate.toString(), onValueChange = {}, readOnly = true, leadingIcon = {Icon(Icons.Default.DateRange,"null")},
                placeholder = {Text("Birth Date")}, label = {Text("Birth Date")}, enabled = false,modifier = Modifier.fillMaxWidth(0.5f).clickable(
                    onClick = {
                        CalenderState=true
                    }
                ))

            Button(onClick = {
                if(SecondDate ==null){
                Toast.makeText(context,"BirthDAte Can not be empty", Toast.LENGTH_LONG).show()
            }
            else{
                Date=SecondDate

            }}) {
                Text("Generate")
            }
        }

    }

}