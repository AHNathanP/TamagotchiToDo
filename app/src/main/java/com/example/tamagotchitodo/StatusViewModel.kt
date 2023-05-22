package com.example.tamagotchitodo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.text.SimpleDateFormat
import java.util.Calendar

class StatusViewModel: ViewModel() {
    private var _numOfTasksDone = MutableLiveData(0)
    val numOfTasksDone: LiveData<Int>
        get() = _numOfTasksDone
    private var _petImageKey = MutableLiveData(0)
    val petImageKey: LiveData<Int>
        get() = _petImageKey
    private var _petName = MutableLiveData("")
    val petName: LiveData<String>
        get() = _petName
    private var _listOfTasks = MutableLiveData(mutableListOf<Task>())
    val listOfTasks: LiveData<MutableList<Task>>
        get() = _listOfTasks
    private var _taskKey = MutableLiveData(0)
    val taskKey: LiveData<Int>
        get() = _taskKey
    private var _weekYear = MutableLiveData(0)
    val weekYear: LiveData<Int>
        get() = _weekYear
    private var _petStatus = MutableLiveData("")
    val petStatus: LiveData<String>
        get() = _petStatus
    var taskRemoved = false

    fun updateTasksDone(taskDone: Int) {
        val currentTasksDone = numOfTasksDone.value?:0
        _numOfTasksDone.value = currentTasksDone + taskDone
    }
    fun addTask(task: Task) {
        _listOfTasks.value?.add(task)
    }
    fun setTaskKey(index: Int) {
        _taskKey.value = index
    }
    fun setWeekYear(weekYearSet: Int) {
        if (weekYearSet > (_weekYear.value ?: 0)) {
            _weekYear.value = weekYearSet
            _numOfTasksDone.value = 0
        }
    }
    fun setPetStatus(status: String) {
        _petStatus.value = status
    }
    fun checkTime(monthOfDueDate: Int, dayOfDueDate: Int):Boolean {
        val calendar = Calendar.getInstance()
        var month : Int = SimpleDateFormat("L").format(calendar.time).toInt()
        var day : Int = SimpleDateFormat("d").format(calendar.time).toInt()

        if (monthOfDueDate < month) {
            return false
        }
        if (monthOfDueDate == month && dayOfDueDate < day) {
            return false
        }
        return true
    }
}