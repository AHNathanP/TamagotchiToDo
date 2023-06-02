package com.example.tamagotchitodo

import androidx.lifecycle.ViewModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class StatusViewModel: ViewModel() {
    var weekday = "Sunday"
    var listOfTasks = mutableListOf<Task>()
    lateinit var dbRef : DatabaseReference

    fun updateWeekday(newWeekday: String, petKey: String) {
        dbRef = Firebase.database.reference
        if (newWeekday == weekday && petKey != "") {
            weekday = newWeekday
            dbRef.child("pets").child(petKey).child("numOfTasksDone").setValue(0)
        }
    }
    fun removeTask(taskKey: String) {
        for (task in listOfTasks) {
            if (task.key == taskKey) {
                listOfTasks.remove(task)
            }
        }
    }
}