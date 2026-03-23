package com.example.mission365

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.mission365.Screens.HomeScreen
import com.example.mission365.Screens.navigation.navApp

import com.example.mission365.ui.theme.Mission365Theme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        var viewModel = veiwModel(this.applicationContext)
        var a = this.applicationContext

        enableEdgeToEdge()
        setContent {

            Mission365Theme {



                Scaffold() {it->

                    navApp(viewModel, Modifier.padding(it))


                }

//                Box (){
//                    Image(CreateWallpaper(a),"null")
//                }
                //CreateWallpaper(this.applicationContext)
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//
//
//                    //RemovableScreen(viewModel)
//                    //AgeCalenderScreen(viewModel)
//                    //CustomCalenderScreen(viewModel)
//                    //HomeScreen(viewModel)
//                }
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
//@Composable
//fun AppBackground(content: @Composable () -> Unit) {
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(
//                brush = Brush.verticalGradient(
//                    colors = listOf(
//                        Color(0xFF8B0000),
//                        Color(0xFF862020),
//                        Color.Black
//                    )
//                )
//            )
//    ) {
//        content()
//    }
//}


@Composable
fun AppButton(
    text: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor =  Color(0xFF8B0000),
            contentColor=Color.White
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Text(text)
    }
}
//object AppButtonDefaults {
//
//    @Composable
//    fun primaryButtonColors() = ButtonDefaults.buttonColors(
//        containerColor = MaterialTheme.colorScheme.primary,
//        contentColor = MaterialTheme.colorScheme.onPrimary
//    )
//}
//val RedBlackColorScheme = lightColorScheme(
//    primary = Color(0xFF8B0000),     // dark red
//    onPrimary = Color.White
//)