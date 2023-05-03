package com.example.tamagotchitodo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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
    private var weekYear = 0

    fun updateTasksDone(taskDone: Int) {
        val currentTasksDone = numOfTasksDone.value?:0
        _numOfTasksDone.value = currentTasksDone + taskDone
    }
    fun setPetImageKey(key: Int) {
        _petImageKey.value = key
    }
    fun getPetImageKey(): Int {
        return _petImageKey.value?:0
    }
    fun setPetName(name: String) {
        _petName.value = name
    }
    fun getPetName(): String {
        return _petName.value?:""
    }
    fun addTask(task: Task) {
        _listOfTasks.value?.add(task)
    }
    fun deleteTask(position: Int) {
       listOfTasks.value?.removeAt(position)
        updateTasksDone(1)
    }
    fun deleteTaskWithoutUpdate(position: Int) {
        listOfTasks.value?.removeAt(position)
    }
    fun setTaskKey(index: Int) {
        _taskKey.value = index
    }
    fun getTaskKey(): Int {
        return _taskKey.value?:0
    }
    fun checkTime(monthOfDueDate: Int, dayOfDueDate: Int):Boolean {
        val month = Calendar.MONTH
        val dayOfMonth = Calendar.DAY_OF_MONTH

        if (monthOfDueDate <= month) {
            return true
        }
        if (monthOfDueDate == month && dayOfDueDate < dayOfMonth) {
            return true
        }
        return false
    }
}