package com.example.mission365

import android.R
import android.R.attr.textSize
import android.R.attr.typeface
import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Typeface
import android.media.Image
import android.os.Build
import android.provider.CalendarContract
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toColorLong
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextPainter
import androidx.compose.ui.text.TextPainter.paint
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.NotificationCompat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

//@Preview(showBackground = true, showSystemUi = true, )

@RequiresApi(Build.VERSION_CODES.Q)
fun CreateWallpaper(context : Context): ImageBitmap {
    //var context = LocalContext
    var ScreenHeight = context.resources.displayMetrics.heightPixels
    var ScreenWidth =  context.resources.displayMetrics.widthPixels
    var UpperSpace = ScreenHeight *0.25
    var BottomSpace= ScreenHeight *0.12
    var GridHeight = ScreenHeight-UpperSpace-BottomSpace
    var GridWidth = ScreenWidth.toDouble() *0.95
    var CurrenDate= LocalDate.now()
    var todaysDate = CurrenDate.dayOfYear
    var TotalDays = CurrenDate.lengthOfYear()
    var Columns = 14
    var Rows = 27
    var cellSize =minOf(GridHeight/Rows , GridWidth /Columns)
    var paint = Paint().apply {
        color= Color.White

    }
    var leftDaysPaint = Paint().apply { color= Color.White ; strokeWidth=2f;style= PaintingStyle.Stroke }
    var CurrentDaysPaint = Paint().apply { color= Color.Red ;style= PaintingStyle.Fill }



    var image = Bitmap.createBitmap(ScreenWidth,ScreenHeight, Bitmap.Config.ARGB_8888 ).asImageBitmap()
    var canvas = Canvas(image)
    canvas.nativeCanvas.drawColor(Color.Black.toColorLong())

    for(i in 1..TotalDays){
        //var i=j+1
        var Rx = ((i-1)%Columns) * (GridWidth/Columns) +(GridWidth/Columns)/2 + 10f //
        var Ry = ((i-1)/Columns) *(GridHeight/Rows) + (GridHeight/Rows)/2 + UpperSpace//
        //canvas.drawCircle(Rx.toFloat(),Ry.toFloat(),  cellSize.toFloat()*0.3f , paint)

        when{
           i < todaysDate->{canvas.drawCircle(Offset(Rx.toFloat(),Ry.toFloat()), cellSize.toFloat()*0.35f,  paint)}
            i == todaysDate->{canvas.drawCircle( Offset(Rx.toFloat(),Ry.toFloat()), cellSize.toFloat()*0.35f,  CurrentDaysPaint)}
            i > todaysDate->{canvas.drawCircle( Offset(Rx.toFloat(),Ry.toFloat()),  cellSize.toFloat()*0.35f, leftDaysPaint)}

        }

    }
    var text = (TotalDays-todaysDate).toString()+" days left"

    var textPaint = android.graphics.Paint().apply { color = android.graphics.Color.parseColor("#FFA500") }
    canvas.nativeCanvas.drawText(text,ScreenWidth/2.0f,ScreenHeight*0.9f,textPaint)


    //canvas.drawImage()

    return image;






}

@RequiresApi(Build.VERSION_CODES.Q)
fun CreateAgeWallpaper(context: Context, BirthDate : LocalDate): ImageBitmap {
    //var context = LocalContext
    var ScreenHeight = context.resources.displayMetrics.heightPixels
    var ScreenWidth =  context.resources.displayMetrics.widthPixels
    var UpperSpace = ScreenHeight *0.20
    var BottomSpace= ScreenHeight *0.03
    var GridHeight = ScreenHeight-UpperSpace-BottomSpace
    var GridWidth = ScreenWidth.toDouble() *0.95
//    var CurrenDate= LocalDate.now()
//    var todaysDate = CurrenDate.dayOfYear
//    var TotalDays = CurrenDate.lengthOfYear()
    var Totalweeks = 3650
    var livedWeeks= livingWeeks(BirthDate)
    var Columns = 53
    var Rows = 81
    var cellSize =minOf(GridHeight/Rows , GridWidth /Columns)
    var paint = Paint().apply {
        color= Color.White

    }
    var leftDaysPaint = Paint().apply { color= Color.White ; strokeWidth=2f;style= PaintingStyle.Stroke }
    var CurrentDaysPaint = Paint().apply { color= Color.Red ;style= PaintingStyle.Fill }


    var image = Bitmap.createBitmap(ScreenWidth,ScreenHeight, Bitmap.Config.ARGB_8888 ).asImageBitmap()
    var canvas = Canvas(image)
    canvas.nativeCanvas.drawColor(Color.Black.toColorLong())
    for(i in 1..Totalweeks){

        var Rx = ((i-1)%Columns) * (GridWidth/Columns) +(GridWidth/Columns)/2 + 10f//
        var Ry = ((i-1)/Columns) *(GridHeight/Rows) + (GridHeight/Rows)/2 + UpperSpace//
        //canvas.drawCircle(Rx.toFloat(),Ry.toFloat(),  cellSize.toFloat()*0.3f , paint)


        when{
            i < livedWeeks->{canvas.drawCircle(Offset(Rx.toFloat(),Ry.toFloat()), cellSize.toFloat()*0.35f,  paint)}
            i == livedWeeks->{canvas.drawCircle( Offset(Rx.toFloat(),Ry.toFloat()), cellSize.toFloat()*0.35f,  CurrentDaysPaint)}
            i > livedWeeks->{canvas.drawCircle( Offset(Rx.toFloat(),Ry.toFloat()),  cellSize.toFloat()*0.35f, leftDaysPaint)}

        }

    }
    var text = (((Totalweeks-livedWeeks)/Totalweeks)).toString()+" % life left"

    var textPaint = android.graphics.Paint().apply { color = android.graphics.Color.parseColor("#FFA500") }
    canvas.nativeCanvas.drawText(text,ScreenWidth/2.0f,ScreenHeight*0.17f,textPaint)


    //canvas.drawImage()




    //canvas.drawImage()

    return image;






}
// function to calculate weeks
//fun LivingWeeks(date: LocalDate): Int{
//    var todayDate= LocalDate.now()
//    var year = (todayDate.year-date.year)*365.25
//    var month = (todayDate.monthValue-date.monthValue) *365.25/12
//    var DAY = (todayDate.dayOfMonth-date.dayOfMonth)
//
//    return ((year+month+DAY.toDouble())/7.0).toInt();
//
//
//}
fun livingWeeks(birthDate: LocalDate): Int {
    return ChronoUnit.WEEKS.between(birthDate, LocalDate.now()).toInt()
}

@RequiresApi(Build.VERSION_CODES.Q)
fun CustomizedImage(startDate : LocalDate, endDate : LocalDate, Rows:Int, Column:Int, context: Context): ImageBitmap{
    var ScreenHeight = context.resources.displayMetrics.heightPixels
    var ScreenWidth =  context.resources.displayMetrics.widthPixels
    var UpperSpace = ScreenHeight *0.25
    var BottomSpace= ScreenHeight *0.10
    var GridHeight = ScreenHeight-UpperSpace-BottomSpace
    var GridWidth = ScreenWidth.toDouble() *0.95
//    var CurrenDate= LocalDate.now()
//    var todaysDate = CurrenDate.dayOfYear
//    var TotalDays = CurrenDate.lengthOfYear()
    var TotalDays = ChronoUnit.DAYS.between(startDate, endDate).toInt()
    var livedDays = ChronoUnit.DAYS.between(startDate, LocalDate.now()).toInt()
    var Columns = Column
    var Rows =Rows
    var cellSize =minOf(GridHeight/Rows , GridWidth /Columns)
    var paint = Paint().apply {
        color= Color.White

    }
    var leftDaysPaint = Paint().apply { color= Color.White ; strokeWidth=2f;style= PaintingStyle.Stroke }
    var CurrentDaysPaint = Paint().apply { color= Color.Red ;style= PaintingStyle.Fill }


    var image = Bitmap.createBitmap(ScreenWidth,ScreenHeight, Bitmap.Config.ARGB_8888 ).asImageBitmap()
    var canvas = Canvas(image)
    canvas.nativeCanvas.drawColor(Color.Black.toColorLong())
    for(i in 1..TotalDays){

        var Rx = ((i-1)%Columns) * (cellSize) +(cellSize)/2 + 10f//
        var Ry = ((i-1)/Columns) *(cellSize) + (cellSize)/2 + UpperSpace //
        //canvas.drawCircle(Rx.toFloat(),Ry.toFloat(),  cellSize.toFloat()*0.3f , paint)


        when{
            i < livedDays->{canvas.drawCircle(Offset(Rx.toFloat(),Ry.toFloat()), cellSize.toFloat()*0.35f,  paint)}
            i == livedDays->{canvas.drawCircle( Offset(Rx.toFloat(),Ry.toFloat()), cellSize.toFloat()*0.35f,  CurrentDaysPaint)}
            i > livedDays->{canvas.drawCircle( Offset(Rx.toFloat(),Ry.toFloat()),  cellSize.toFloat()*0.35f, leftDaysPaint)}

        }

    }
    var text = (TotalDays-livedDays).toString()+" days left"

    var textPaint = android.graphics.Paint().apply { color = android.graphics.Color.parseColor("#FFA500") }
    canvas.nativeCanvas.drawText(text,ScreenWidth/2.0f,ScreenHeight*0.91f,textPaint)


    //canvas.drawImage()

    return image;


    //canvas.drawImage()


    return image
}

fun CalculatRowsColumn(Days :Int, context: Context): Int {
    var width  = context.resources.displayMetrics.widthPixels
    var height  = context.resources.displayMetrics.heightPixels
    var Area = (width*height)/Days
    var Columns = width/ Math.sqrt(Area.toDouble())
    return Columns.toInt() +1
}
fun calculateGrid(days: Int, context: Context): Pair<Int, Int> {

    val width = context.resources.displayMetrics.widthPixels.toFloat()*0.95
    val height = context.resources.displayMetrics.heightPixels.toFloat()*0.65

    val aspectRatio = width / height

    // Estimate columns using aspect ratio
    val columns = kotlin.math.ceil(kotlin.math.sqrt(days * aspectRatio)).toInt()

    val rows = kotlin.math.ceil(days.toDouble() / columns).toInt()

    return Pair(rows, columns)
}

@RequiresApi(Build.VERSION_CODES.Q)
fun defaultImage(context: Context) : ImageBitmap {
    var width= context.resources.displayMetrics.widthPixels
    var hieght = context.resources.displayMetrics.heightPixels
    var image = Bitmap.createBitmap(width,hieght, Bitmap.Config.ARGB_8888 ).asImageBitmap()
    var canvas = Canvas(image)


    canvas.nativeCanvas.drawColor(Color.Black.toColorLong())
    return image
}