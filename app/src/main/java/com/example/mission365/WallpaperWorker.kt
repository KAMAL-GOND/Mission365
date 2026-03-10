package com.example.mission365

import android.app.WallpaperManager
import android.content.Context
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.temporal.ChronoUnit
import java.util.concurrent.TimeUnit

class WallpaperWorker(
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


fun delayUntilNextMidnight(): Long {

    val now = LocalDateTime.now()

    val nextMidnight = now
        .toLocalDate()
        .plusDays(1)
        .atTime(LocalTime.of(0, 5))

    return ChronoUnit.MILLIS.between(now,nextMidnight)
}

fun scheduleWallpaperWorker(context: Context) {

    val delay = delayUntilNextMidnight()

    val request = PeriodicWorkRequestBuilder<WallpaperWorker>(
        1, TimeUnit.DAYS
    )
        .setInitialDelay(delay, TimeUnit.MILLISECONDS)
        .build()

    WorkManager.getInstance(context)
        .enqueueUniquePeriodicWork(
            "age_wallpaper_job",
            ExistingPeriodicWorkPolicy.UPDATE,
            request
        )
}