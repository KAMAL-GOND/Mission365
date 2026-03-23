package com.example.mission365

import android.app.Application
import android.app.WallpaperManager
import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.ui.graphics.asAndroidBitmap
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
    var WorkerYearStatus= MutableStateFlow<Status>(Status.IDLE)
    var WorkerRemoveStatus=  MutableStateFlow<Status>(Status.IDLE)

    var WorkerAgeStatus=  MutableStateFlow<Status>(Status.IDLE)
    var WorkerCoustomStatus=  MutableStateFlow<Status>(Status.IDLE)

    fun addYearLock(image : Bitmap) {
        viewModelScope.launch {
            try{
                scheduleWallpaperWorkerLock(a)
                WallpaperManager.getInstance(a).setBitmap(image,null,true, WallpaperManager.FLAG_LOCK)
                WorkerYearStatus.value= Status.SUCCESS
            }
            catch (e: Exception){
                Log.d("YearLock",e.toString())
                WorkerYearStatus.value= Status.ERROR

            }
        }

    }

    fun addYearHome(image : Bitmap) {
        viewModelScope.launch {
            try{
                scheduleWallpaperWorkerLock(a)
                WallpaperManager.getInstance(a).setBitmap(image,null,true, WallpaperManager.FLAG_SYSTEM)
                WorkerYearStatus.value= Status.SUCCESS
            }
            catch (e: Exception){
                Log.d("YearHome",e.toString())
                WorkerYearStatus.value= Status.SUCCESS

            }
        }

    }
    fun addLifeLock(birthDate : LocalDate,image : Bitmap) {
        viewModelScope.launch {
            try{
                scheduleYearWallpaperWorkerLock(a,birthDate)
                WallpaperManager.getInstance(a).setBitmap(image,null,true, WallpaperManager.FLAG_LOCK)
                WorkerAgeStatus.value= Status.SUCCESS
            }
            catch (e: Exception){
                Log.d("LifeLock",e.toString())
                WorkerAgeStatus.value= Status.ERROR

            }
        }
    }
    fun addLifeHome(birthDate : LocalDate,image : Bitmap) {
        viewModelScope.launch {
            try{
                scheduleYearWallpaperWorkerHome(a,birthDate)
                WallpaperManager.getInstance(a).setBitmap(image,null,true, WallpaperManager.FLAG_SYSTEM)
                WorkerAgeStatus.value= Status.SUCCESS
            }
            catch (e: Exception){
                Log.d("LifeHome",e.toString())
                WorkerAgeStatus.value= Status.ERROR

            }
        }
    }
    fun addCustomLock(StartDate: LocalDate,EndDate: LocalDate,row: Int,colums:Int,image : Bitmap) {
        viewModelScope.launch {
            try{
                scheduleCustomizedWallpaperWorkerLock(a,StartDate,EndDate,row,colums)
                WallpaperManager.getInstance(a).setBitmap(image,null,true, WallpaperManager.FLAG_LOCK)
                WorkerCoustomStatus.value= Status.SUCCESS
            }
            catch (e: Exception){
                Log.d("CustomLock",e.toString())
                WorkerCoustomStatus.value= Status.ERROR

            }
        }
    }
    fun addCustomHome(StartDate: LocalDate,EndDate: LocalDate,row: Int,colums:Int,image : Bitmap) {
        viewModelScope.launch {
            try{
                scheduleCustomizedWallpaperWorkerHome(a,StartDate,EndDate,row,colums)
                WallpaperManager.getInstance(a).setBitmap(image,null,true, WallpaperManager.FLAG_SYSTEM)
                WorkerCoustomStatus.value= Status.SUCCESS
            }
            catch (e: Exception){
                Log.d("CustomHome",e.toString())
                WorkerCoustomStatus.value= Status.ERROR

            }
        }
    }
    fun removeLock() {
        viewModelScope.launch {
            try {
                WorkManager.getInstance(a).cancelUniqueWork("Lock")
                var image= defaultImage(a).asAndroidBitmap()
                WallpaperManager.getInstance(a).setBitmap(image,null,true, WallpaperManager.FLAG_LOCK)
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
                var image= defaultImage(a).asAndroidBitmap()
                WallpaperManager.getInstance(a).setBitmap(image,null,true, WallpaperManager.FLAG_SYSTEM)
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

    fun ResetRemoveYearWorkerStatus(){
        WorkerYearStatus.value=Status.IDLE
    }
    fun ResetRemoveAgeWorkerStatus(){
        WorkerAgeStatus.value=Status.IDLE
    }
    fun ResetRemoveCustomWorkerStatus(){
        WorkerCoustomStatus.value=Status.IDLE
    }
}

//data class
enum class Status {
    IDLE,
    SUCCESS,
    ERROR
}