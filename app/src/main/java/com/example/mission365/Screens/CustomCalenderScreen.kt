package com.example.mission365.Screens

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
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
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.mission365.CalculatRowsColumn
import com.example.mission365.CreateAgeWallpaper
import com.example.mission365.CustomizedImage
import com.example.mission365.Status
import com.example.mission365.calculateGrid
import com.example.mission365.veiwModel
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.temporal.ChronoUnit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomCalenderScreen(viewModel: veiwModel){

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
                viewModel.ResetRemoveAgeWorkerStatus()
            }

            Status.ERROR  -> {
                Toast.makeText(context, "Some error occurred", Toast.LENGTH_LONG).show()
                viewModel.ResetRemoveAgeWorkerStatus()
            }

            Status.IDLE -> {}
        }
    }

    if(startDate != null || endDate != null){
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
            Spacer(Modifier.fillMaxHeight(0.7f))
            Button(onClick = {
                viewModel.addCustomHome(StartDate=startDate!!,EndDate=endDate!!,a.first,a.second,
                    image.asAndroidBitmap())

            }) {
                Text("Apply HomeScreen")
            }

            Button(onClick = {
                viewModel.addCustomLock(StartDate=startDate!!,EndDate=endDate!!,lines,(days/lines).toInt()+1,
                    image.asAndroidBitmap())

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
        Column(modifier = Modifier.fillMaxSize().padding(10.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center){

            TextField(value = SecondDate1.toString(), onValueChange = {}, readOnly = true, leadingIcon = {Icon(Icons.Default.DateRange,"null")},
                placeholder = {Text("Start Date")}, label = {Text("start Date")}, enabled = false,modifier = Modifier.fillMaxWidth(0.5f).clickable(
                    onClick = {
                        CalenderState1=true
                    }
                ))
            TextField(value = SecondDate2.toString(), onValueChange = {}, readOnly = true, leadingIcon = {Icon(Icons.Default.DateRange,"null")},
                placeholder = {Text("end Date")}, label = {Text("end Date")}, enabled = false,modifier = Modifier.fillMaxWidth(0.5f).clickable(
                    onClick = {
                        CalenderState2=true
                    }
                ))
//            TextField(value = lines.toString(), onValueChange = {lines = it.toInt()}, leadingIcon = {Icon(Icons.Default.Create,"null")}, readOnly = true,
//                placeholder = {Text("Lines")}, label = {Text("Lines")}, enabled = true, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number) ,modifier = Modifier.fillMaxWidth(0.7f))

            Button(onClick = {
                if(SecondDate1 ==null || SecondDate2 == null){
                    Toast.makeText(context,"Dates Can not be empty", Toast.LENGTH_LONG).show()
                }
                else{
                    startDate=SecondDate1
                    endDate=SecondDate2

                }}) {
                Text("Generate")
            }
        }

    }

}