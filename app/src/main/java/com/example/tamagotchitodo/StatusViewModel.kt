package com.example.tamagotchitodo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class StatusViewModel: ViewModel() {
    private var _listOfTasks = MutableLiveData(mutableListOf<Task>())
    val listOfTasks: LiveData<MutableList<Task>>
        get() = _listOfTasks
    var weekday = "Sunday"
    var listOfTaskKeys = mutableListOf<String>()
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
            Log.i("StatusViewModel", "taskName is $taskName")
            if (task.taskName == taskName) {
                listOfTasks.value?.remove(task)
            }
        }
    }
    fun removeTaskWithKey(taskKey: String) {
        for (task in listOfTaskKeys) {
            if (task == taskKey) {
                listOfTaskKeys.remove(task)
            }
        }
    }
}