package com.example.tamagotchitodo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

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
}