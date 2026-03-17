package com.example.mission365.Screens

import android.graphics.Bitmap
import android.media.Image
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.platform.LocalContext
import com.example.mission365.CreateWallpaper
import com.example.mission365.Status
import com.example.mission365.veiwModel

@Composable
fun YearCalenderScreen(viewModel: veiwModel){
    var image: ImageBitmap=CreateWallpaper(viewModel.a)
    val workerStatus by viewModel.WorkerYearStatus.collectAsState()
    var context =LocalContext.current

    LaunchedEffect(workerStatus) {

        when (workerStatus) {

            Status.SUCCESS  -> {
                Toast.makeText(context, "Done", Toast.LENGTH_LONG).show()
                viewModel.ResetRemoveYearWorkerStatus()
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
        Spacer(Modifier.fillMaxHeight(0.7f))
        Button(onClick = {
            viewModel.addYearHome(image.asAndroidBitmap())

        }) {
            Text("Apply HomeScreen")
        }

        Button(onClick = {
            viewModel.addYearLock(image.asAndroidBitmap())

        }) {
            Text("Apply LockScreen")
        }
    }
}