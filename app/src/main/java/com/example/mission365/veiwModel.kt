package com.example.mission365

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.WorkManager
import androidx.work.Worker
import kotlinx.coroutines.launch
import java.time.LocalDate
import kotlin.coroutines.Continuation

class veiwModel(var a : Context) : ViewModel() {
    fun addYearLock() {
        viewModelScope.launch {
            try{
                scheduleWallpaperWorkerLock(a)
            }
            catch (e: Exception){
                Log.d("YearLock",e.toString())

            }
        }

    }

    fun addYearHome() {
        viewModelScope.launch {
            try{
                scheduleWallpaperWorkerLock(a)
            }
            catch (e: Exception){
                Log.d("YearHome",e.toString())

            }
        }

    }
    fun addLifeLock(birthDate : LocalDate) {
        viewModelScope.launch {
            try{
                scheduleYearWallpaperWorkerLock(a,birthDate)
            }
            catch (e: Exception){
                Log.d("LifeLock",e.toString())

            }
        }
    }
    fun addLifeHome(birthDate : LocalDate) {
        viewModelScope.launch {
            try{
                scheduleYearWallpaperWorkerHome(a,birthDate)
            }
            catch (e: Exception){
                Log.d("LifeHome",e.toString())

            }
        }
    }
    fun addCustomLock(StartDate: LocalDate,EndDate: LocalDate,row: Int,colums:Int) {
        viewModelScope.launch {
            try{
                scheduleCustomizedWallpaperWorkerLock(a,StartDate,EndDate,row,colums)
            }
            catch (e: Exception){
                Log.d("CustomLock",e.toString())

            }
        }
    }
    fun addCustomHome(StartDate: LocalDate,EndDate: LocalDate,row: Int,colums:Int) {
        viewModelScope.launch {
            try{
                scheduleCustomizedWallpaperWorkerHome(a,StartDate,EndDate,row,colums)
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