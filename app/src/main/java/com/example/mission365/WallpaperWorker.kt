package com.example.mission365

import android.app.WallpaperManager
import android.content.Context
import android.util.Log
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.work.Data
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.temporal.ChronoUnit
import java.util.concurrent.TimeUnit
import kotlin.time.Duration

class WallpaperYearWorkerLock(
    context: Context,
    params: WorkerParameters
) : Worker(context, params) {

    override fun doWork(): Result {

        try {

            val context = applicationContext

            // generate wallpaper
            val imageBitmap = CreateWallpaper(context)

            // convert to Android Bitmap
            val bitmap = imageBitmap.asAndroidBitmap()

            // set lockscreen wallpaper
            val manager = WallpaperManager.getInstance(context)

            manager.setBitmap(
                bitmap,
                null,
                true,
                WallpaperManager.FLAG_LOCK
            )

            return Result.success()

        } catch (e: Exception) {
            e.printStackTrace()
            return Result.failure()
        }
    }
}
class WallpaperYearWorkerHome(
    context: Context,
    params: WorkerParameters
) : Worker(context, params) {

    override fun doWork(): Result {

        try {

            val context = applicationContext

            // generate wallpaper
            val imageBitmap = CreateWallpaper(context)

            // convert to Android Bitmap
            val bitmap = imageBitmap.asAndroidBitmap()

            // set lockscreen wallpaper
            val manager = WallpaperManager.getInstance(context)

            manager.setBitmap(
                bitmap,
                null,
                true,
                WallpaperManager.FLAG_SYSTEM
            )

            return Result.success()

        } catch (e: Exception) {
            e.printStackTrace()
            return Result.failure()
        }
    }
}


fun delayUntilNextMidnight(): Long {

    val now = LocalDateTime.now()

    val nextMidnight = now
        .toLocalDate()
        .plusDays(1)
        .atTime(LocalTime.of(0, 5))

    return ChronoUnit.MILLIS.between(now,nextMidnight)
}

fun scheduleWallpaperWorkerLock(context: Context) {

    val delay = delayUntilNextMidnight()

    val request = PeriodicWorkRequestBuilder<WallpaperYearWorkerLock>(
        1, TimeUnit.DAYS
    )
        .setInitialDelay(delay, TimeUnit.MILLISECONDS)
        .build()

    WorkManager.getInstance(context)
        .enqueueUniquePeriodicWork(
            "age_wallpaper_job",
            ExistingPeriodicWorkPolicy.UPDATE,
            request,

        )
}

fun scheduleWallpaperWorkerHome(context: Context) {

    val delay = delayUntilNextMidnight()

    val request = PeriodicWorkRequestBuilder<WallpaperYearWorkerHome>(
        1, TimeUnit.DAYS
    )
        .setInitialDelay(delay, TimeUnit.MILLISECONDS)
        .build()

    WorkManager.getInstance(context)
        .enqueueUniquePeriodicWork(
            "age_wallpaper_job",
            ExistingPeriodicWorkPolicy.UPDATE,
            request,

            )
}

fun delayUntilNextWeekMidnight() : Long{
    val nextWeekStartingDate= 7 - LocalDateTime.now().dayOfWeek.value;
    val MondayMidNight= LocalDateTime.now().toLocalDate().plusDays(nextWeekStartingDate.toLong()).atTime(0,3)
    return ChronoUnit.MILLIS.between(LocalDateTime.now(),MondayMidNight)


}


class lifeWorkerLock(context: Context, params: WorkerParameters) : Worker(context, params){

    override fun doWork(): Result {
        val dateString = inputData.getString("birthDate") ?: return Result.failure()
        val date = LocalDate.parse(dateString)
 

        try{

            var context= applicationContext
            var image= CreateAgeWallpaper(applicationContext,date).asAndroidBitmap()
            var manager= WallpaperManager.getInstance(context)
            manager.setBitmap(image,null,true, WallpaperManager.FLAG_LOCK)
            return Result.success()


        }

        catch (e : Exception){

            Log.d("yearWallpaper",e.toString())
            return Result.failure()

        }
        //return TODO("Provide the return value")
    }
}
class lifeWorkerHome(context: Context, params: WorkerParameters) : Worker(context, params){

    override fun doWork(): Result {
        val dateString = inputData.getString("birthDate") ?: return Result.failure()
        val date = LocalDate.parse(dateString)


        try{

            var context= applicationContext
            var image= CreateAgeWallpaper(applicationContext,date).asAndroidBitmap()
            var manager= WallpaperManager.getInstance(context)
            manager.setBitmap(image,null,true, WallpaperManager.FLAG_SYSTEM)
            return Result.success()


        }

        catch (e : Exception){

            Log.d("yearWallpaper",e.toString())
            return Result.failure()

        }
        //return TODO("Provide the return value")
    }
}
fun scheduleYearWallpaperWorkerLock(context: Context, birthDate: LocalDate) {

    val delay = delayUntilNextWeekMidnight()
    val data = Data.Builder()
        .putString("birthDate", birthDate.toString())
        .build()

    val request = PeriodicWorkRequestBuilder<lifeWorkerLock>(
        7, TimeUnit.DAYS
    )
        .setInitialDelay(delay, TimeUnit.MILLISECONDS).setInputData(data)
        .build()



    WorkManager.getInstance(context).enqueueUniquePeriodicWork(
        "YearAge_wallpaper_job",
        ExistingPeriodicWorkPolicy.UPDATE,
        request,


        )
}
fun scheduleYearWallpaperWorkerHome(context: Context, birthDate: LocalDate) {

    val delay = delayUntilNextWeekMidnight()
    val data = Data.Builder()
        .putString("birthDate", birthDate.toString())
        .build()

    val request = PeriodicWorkRequestBuilder<lifeWorkerHome>(
        7, TimeUnit.DAYS
    )
        .setInitialDelay(delay, TimeUnit.MILLISECONDS).setInputData(data)
        .build()



    WorkManager.getInstance(context).enqueueUniquePeriodicWork(
        "YearAge_wallpaper_job",
        ExistingPeriodicWorkPolicy.UPDATE,
        request,


        )
}

//
class CustomizedCalenderWorkerHome(context: Context,params: WorkerParameters) : Worker(context,params){
    override
    fun doWork(): Result{
        var EndDate = inputData.keyValueMap.get("endDate")
        var endDate = LocalDate.parse(EndDate.toString())
        var StartDate=inputData.keyValueMap.get("startDate")
        var startDate= LocalDate.parse(StartDate.toString())
        var rows = inputData.keyValueMap.get("rows")
        var columns = inputData.keyValueMap.get("columns")

        try{
            var context=applicationContext
            var image = CustomizedImage(startDate as LocalDate,endDate as LocalDate,rows as Int,columns as Int,context)

            var manager = WallpaperManager.getInstance(context)
            manager.setBitmap(image.asAndroidBitmap(),null,true, WallpaperManager.FLAG_SYSTEM)



            return Result.success()
        }
        catch (e: Exception){
            return Result.failure()
        }
    }}

class CustomizedCalenderWorkerLock(context: Context,params: WorkerParameters) : Worker(context,params){
    override
    fun doWork(): Result{
        var EndDate = inputData.keyValueMap.get("endDate")
        var endDate = LocalDate.parse(EndDate.toString())
        var StartDate=inputData.keyValueMap.get("startDate")
        var startDate= LocalDate.parse(StartDate.toString())
        var rows = inputData.keyValueMap.get("rows")
        var columns = inputData.keyValueMap.get("columns")

        try{
            var context=applicationContext
            var image = CustomizedImage(startDate as LocalDate,endDate as LocalDate,rows as Int,columns as Int,context)

            var manager = WallpaperManager.getInstance(context)
            manager.setBitmap(image.asAndroidBitmap(),null,true, WallpaperManager.FLAG_LOCK)



            return Result.success()
        }
        catch (e: Exception){
            return Result.failure()
        }
    }}
    fun scheduleCustomizedWallpaperWorkerHome(context: Context, startDate: LocalDate,endDate: LocalDate,rows:Int, column:Int) {

        val delay = delayUntilNextMidnight()
        val data = Data.Builder().putString("starDate",startDate.toString()).putString("endDate",endDate.toString()).putInt("rows",rows).putInt("colums",column).build()

        val request = PeriodicWorkRequestBuilder<CustomizedCalenderWorkerHome>(
            1, TimeUnit.DAYS
        )
            .setInitialDelay(delay, TimeUnit.MILLISECONDS).setInputData(data)
            .build()



        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "YearAge_wallpaper_job",
            ExistingPeriodicWorkPolicy.UPDATE,
            request,


            )
    }

fun scheduleCustomizedWallpaperWorkerLock(context: Context, startDate: LocalDate,endDate: LocalDate,rows:Int, column:Int) {

    val delay = delayUntilNextMidnight()
    val data = Data.Builder().putString("starDate",startDate.toString()).putString("endDate",endDate.toString()).putInt("rows",rows).putInt("colums",column).build()

    val request = PeriodicWorkRequestBuilder<CustomizedCalenderWorkerLock>(
        1, TimeUnit.DAYS
    )
        .setInitialDelay(delay, TimeUnit.MILLISECONDS).setInputData(data)
        .build()



    WorkManager.getInstance(context).enqueueUniquePeriodicWork(
        "YearAge_wallpaper_job",
        ExistingPeriodicWorkPolicy.UPDATE,
        request,


        )
}