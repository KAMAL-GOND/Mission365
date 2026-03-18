package com.example.mission365.Screens

import android.media.Image
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.content.MediaType.Companion.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.unit.dp
import com.example.mission365.R
import com.example.mission365.Status
import com.example.mission365.veiwModel

@Composable
fun HomeScreen(viewModel: veiwModel){
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
    Column(modifier = Modifier.fillMaxSize()) {

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxWidth().fillMaxHeight(0.85f),
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                item { HomeScreenObject(viewModel,R.drawable.year,"Year Wallpaper",{}) }
                item { HomeScreenObject(viewModel,R.drawable.age,"Age Wallpaper",{}) }
                item { HomeScreenObject(viewModel,R.drawable.custom,"Custom Wallpaper",{}) }



            }

        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally){



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

//data class HomeScreenObject(
//    var name : String,
//
//)

@Composable
fun HomeScreenObject(veiwModel: veiwModel, image: Int, name:String, Click:()->Unit){
    Column(modifier = Modifier.width(200.dp).height(300.dp).clickable(onClick={Click}), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceEvenly) {
        Image(painter = painterResource(image),"null", modifier = Modifier.fillMaxWidth().fillMaxHeight(0.8f), contentScale = ContentScale.FillBounds)

        Text(name.toString())
    }

}