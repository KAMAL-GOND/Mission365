package com.example.mission365

import android.R
import android.graphics.Bitmap
import android.provider.CalendarContract
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import java.time.LocalDate
import java.time.LocalDateTime

@Preview(showBackground = true, backgroundColor = android.graphics.Color.WHITE.toLong())
@Composable
fun CreateWallpaper(): ImageBitmap {
    var context = LocalContext
    var ScreenHeight = context.current.applicationContext.resources.displayMetrics.heightPixels
    var ScreenWidth =  context.current.applicationContext.resources.displayMetrics.widthPixels
    var UpperSpace = ScreenHeight *0.25
    var BottomSpace= ScreenHeight *0.10
    var GridHeight = ScreenHeight-UpperSpace-BottomSpace
    var GridWidth = ScreenWidth.toDouble()
    var CurrenDate= LocalDate.now()
    var todaysDate = CurrenDate.dayOfYear
    var TotalDays = CurrenDate.lengthOfYear()
    var Columns = 14
    var Rows = 27
    var cellSize =minOf(GridHeight/Rows , GridWidth /Columns)
    var paint = Paint().apply {
        color=Color.Gray

    }


    var image = Bitmap.createBitmap(ScreenWidth,ScreenHeight, Bitmap.Config.ARGB_8888 ).asImageBitmap()
    var canvas = Canvas(image)
    for(i in 1..TotalDays){
        var Rx = (i%Columns) * (GridWidth/Columns)
        var Ry = (i/Columns) *(GridHeight/Rows)

        when{
           i < todaysDate->{canvas.drawCircle(center = Offset(Rx.toFloat(),Ry.toFloat()), radius = cellSize.toFloat()*0.3f, paint = paint)}
            i == todaysDate->{canvas.drawCircle(center = Offset(Rx.toFloat(),Ry.toFloat()), radius = cellSize.toFloat()*0.3f, paint = Paint())}
            i > todaysDate->{canvas.drawCircle(center = Offset(Rx.toFloat(),Ry.toFloat()), radius = cellSize.toFloat()*0.3f, paint = Paint())}

        }

    }

    return image;





}