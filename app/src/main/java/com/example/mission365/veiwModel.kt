package com.example.mission365

import android.app.WallpaperManager
import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.WorkManager
import androidx.work.Worker
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import kotlin.coroutines.Continuation

var WorkerStatus= MutableStateFlow<Result<Any>?>(null)
var WorkerRemoveStatus= MutableStateFlow<Result<Any>?>(null)

class veiwModel(var a : Context) : ViewModel() {
    fun addYearLock(image : Bitmap) {
        viewModelScope.launch {
            try{
                scheduleWallpaperWorkerLock(a)
                WallpaperManager.getInstance(a).setBitmap(image,null,true, WallpaperManager.FLAG_LOCK)
            }
            catch (e: Exception){
                Log.d("YearLock",e.toString())

            }
        }

    }

    fun addYearHome(image : Bitmap) {
        viewModelScope.launch {
            try{
                scheduleWallpaperWorkerLock(a)
                WallpaperManager.getInstance(a).setBitmap(image,null,true, WallpaperManager.FLAG_SYSTEM)
            }
            catch (e: Exception){
                Log.d("YearHome",e.toString())

            }
        }

    }
    fun addLifeLock(birthDate : LocalDate,image : Bitmap) {
        viewModelScope.launch {
            try{
                scheduleYearWallpaperWorkerLock(a,birthDate)
                WallpaperManager.getInstance(a).setBitmap(image,null,true, WallpaperManager.FLAG_LOCK)
            }
            catch (e: Exception){
                Log.d("LifeLock",e.toString())

            }
        }
    }
    fun addLifeHome(birthDate : LocalDate,image : Bitmap) {
        viewModelScope.launch {
            try{
                scheduleYearWallpaperWorkerHome(a,birthDate)
                WallpaperManager.getInstance(a).setBitmap(image,null,true, WallpaperManager.FLAG_SYSTEM)
            }
            catch (e: Exception){
                Log.d("LifeHome",e.toString())

            }
        }
    }
    fun addCustomLock(StartDate: LocalDate,EndDate: LocalDate,row: Int,colums:Int,image : Bitmap) {
        viewModelScope.launch {
            try{
                scheduleCustomizedWallpaperWorkerLock(a,StartDate,EndDate,row,colums)
                WallpaperManager.getInstance(a).setBitmap(image,null,true, WallpaperManager.FLAG_LOCK)
            }
            catch (e: Exception){
                Log.d("CustomLock",e.toString())

            }
        }
    }
    fun addCustomHome(StartDate: LocalDate,EndDate: LocalDate,row: Int,colums:Int,image : Bitmap) {
        viewModelScope.launch {
            try{
                scheduleCustomizedWallpaperWorkerHome(a,StartDate,EndDate,row,colums)
                WallpaperManager.getInstance(a).setBitmap(image,null,true, WallpaperManager.FLAG_SYSTEM)
            }
            catch (e: Exception){
                Log.d("CustomHome",e.toString())

            }
        }
    }
    fun removeLock() {
        viewModelScope.launch {
            try {
                WorkManager.getInstance(a).cancelUniqueWork("Lock")

            } catch (e: Exception) {
                Log.d("RemoveLock",e.toString())


            }

        }
    }
    fun removeHome() {
        viewModelScope.launch {
            try {
                WorkManager.getInstance(a).cancelUniqueWork("Home")

            } catch (e: Exception) {

                Log.d("RemoveHome",e.toString())

            }

        }

    }
}

//data class