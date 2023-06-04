package com.example.tamagotchitodo

import androidx.lifecycle.ViewModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class StatusViewModel: ViewModel() {
    var weekday = "Sunday"
    lateinit var dbRef : DatabaseReference

    fun updateWeekday(newWeekday: String, petKey: String) {
        dbRef = Firebase.database.reference
        if (newWeekday == weekday && petKey != "") {
            weekday = newWeekday
            dbRef.child("pets").child(petKey).child("numOfTasksDone").setValue(0)
        }
    }
}