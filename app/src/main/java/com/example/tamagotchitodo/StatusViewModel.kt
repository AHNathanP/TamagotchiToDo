package com.example.tamagotchitodo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.text.SimpleDateFormat
import java.util.Calendar

class StatusViewModel: ViewModel() {
    private var _numOfTasksDone = MutableLiveData(0)
    val numOfTasksDone: LiveData<Int>
        get() = _numOfTasksDone
    private var _listOfTasks = MutableLiveData(mutableListOf<Task>())
    val listOfTasks: LiveData<MutableList<Task>>
        get() = _listOfTasks
    var weekYear = 0

    fun updateWeekYear(newWeekYear: Int) {
        if (newWeekYear > weekYear) {
            weekYear = newWeekYear
            _numOfTasksDone.value = 0
        }
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