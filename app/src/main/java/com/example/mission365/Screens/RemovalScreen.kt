package com.example.mission365.Screens

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.work.ListenableWorker
import androidx.work.ListenableWorker.Result.success
import com.example.mission365.Status
import com.example.mission365.veiwModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlin.Result.Companion.success


@Composable
fun RemovableScreen(viewModel: veiwModel) {

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

    Box(modifier = Modifier.fillMaxSize()) {

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.fillMaxHeight(0.7f))

            Button(onClick = {
                viewModel.removeHome()

            }) {
                Text("Remove HomeScreen")
            }

            Button(onClick = {
                viewModel.removeLock()

            }) {
                Text("Remove LockScreen")
            }
        }
    }
}