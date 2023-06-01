package com.example.tamagotchitodo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.Calendar

class StatusViewModel: ViewModel() {
    private var _listOfTasks = MutableLiveData(mutableListOf<Task>())
    val listOfTasks: LiveData<MutableList<Task>>
        get() = _listOfTasks
    var weekday = "Sunday"
    lateinit var dbRef : DatabaseReference

    fun updateWeekday(newWeekday: String, petKey: String) {
        dbRef = Firebase.database.reference
        if (newWeekday == weekday && petKey != "") {
            weekday = newWeekday
            dbRef.child("pets").child(petKey).child("numOfTasksDone").setValue(0)
        }
    }
    fun removeTaskWithName(taskName: String) {
        for (task in listOfTasks.value?: mutableListOf()) {
            if (task.taskName == taskName) {
                listOfTasks.value?.remove(task)
            }
        }
    }
}