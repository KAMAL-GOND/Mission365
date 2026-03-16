package com.example.mission365

import android.app.WallpaperManager
import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.work.Worker
import com.example.mission365.Screens.RemovableScreen

import com.example.mission365.ui.theme.Mission365Theme
import java.time.LocalDate

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        var viewModel = veiwModel(this.applicationContext)

        enableEdgeToEdge()
        setContent {
            Mission365Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->


                    RemovableScreen(viewModel)
                }
            }
        }
    }
}
//
//@Composable
//fun Greeting(name: String, modifier: Modifier = Modifier,context: Context) {
//    var image = CreateWallpaper(context)
//    Image(image,null)
//    Column(modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center,) {
//        Button(onClick = {
//            scheduleWallpaperWorkerLock(context)
//        }, colors = ButtonColors(Color.Blue,Color.White, Color.Blue,Color.White)) { Text("Apply")}
//    }
//    WallpaperManager.getInstance(context).setBitmap(
//        image.asAndroidBitmap(),null,true,
//        WallpaperManager.FLAG_LOCK)
//
//}
//
//@Composable
//fun Greeting2( modifier: Modifier = Modifier,context: Context,date: LocalDate) {
//    var image = CreateAgeWallpaper(context,date)
//    Image(image,null)
//    Column(modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center,) {
//        Button(onClick = {
//            scheduleYearWallpaperWorkerHome(context,date)
//        }, colors = ButtonColors(Color.Blue,Color.White, Color.Blue,Color.White)) { Text("Apply")}
//    }
//    WallpaperManager.getInstance(context).setBitmap(
//        image.asAndroidBitmap(),null,true,
//        WallpaperManager.FLAG_SYSTEM)
//
//}
//@Composable
//fun Greeting3( modifier: Modifier = Modifier,context: Context) {
//    var image = CustomizedImage(LocalDate.now().minusDays(10), LocalDate.now().plusDays(20),10,10,context)
//    Image(image,null)
//    Column(modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center,) {
//        Button(onClick = {
//            scheduleCustomizedWallpaperWorkerHome(context,LocalDate.now().minusDays(10), LocalDate.now().plusDays(20),10,10)
//            WallpaperManager.getInstance(context).setBitmap(
//                image.asAndroidBitmap(),null,true,
//                WallpaperManager.FLAG_SYSTEM)
//        }, colors = ButtonColors(Color.Blue,Color.White, Color.Blue,Color.White)) { Text("Apply")}
//    }}
//


