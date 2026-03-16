package com.example.mission365

import android.app.Application
import android.app.WallpaperManager
import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.ListenableWorker
import androidx.work.WorkManager
import androidx.work.Worker
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import kotlin.coroutines.Continuation



class veiwModel(var a : Context ) : ViewModel() {
    var WorkerStatus= MutableStateFlow<Status>(Status.IDLE)
    var WorkerRemoveStatus=  MutableStateFlow<Status>(Status.IDLE)
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
                WorkerRemoveStatus.value= Status.SUCCESS

            } catch (e: Exception) {
                Log.d("RemoveLock",e.toString())
                WorkerRemoveStatus.value= Status.ERROR


            }

        }
    }
    fun removeHome() {
        viewModelScope.launch {
            try {
                WorkManager.getInstance(a).cancelUniqueWork("Home")
                WorkerRemoveStatus.value=Status.SUCCESS

            } catch (e: Exception) {
                WorkerRemoveStatus.value= Status.ERROR

                Log.d("RemoveHome",e.toString())

            }

        }

    }

    fun ResetRemoveWorkerStatus(){
        WorkerRemoveStatus.value=Status.IDLE
    }
}

//data class
enum class Status {
    IDLE,
    SUCCESS,
    ERROR
}