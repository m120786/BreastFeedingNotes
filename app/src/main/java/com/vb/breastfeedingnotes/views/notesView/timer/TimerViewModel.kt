package com.vb.breastfeedingnotes.views.notesView.timer

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class TimerViewModel @Inject constructor(): ViewModel() {
    private val _running = MutableLiveData(false)
    val running: LiveData<Boolean> = _running

    private val _timeSec = MutableLiveData(0)
    val timeSec: LiveData<Int> = _timeSec

    private val _timeMin = MutableLiveData(0)
    val timeMin: LiveData<Int> = _timeMin

    private val _timeHours = MutableLiveData(0)
    val timeHours: LiveData<Int> = _timeHours

    private val _isRunning = MutableStateFlow(false)
    val isRunning = _isRunning.asStateFlow()

    val totalTime: Long = 60000000L

    private val timer = object : CountDownTimer(totalTime, 1000) {
        override fun onTick(millisUntilFinished: Long) {

            val timeHours = TimeUnit.MILLISECONDS.toHours((totalTime - millisUntilFinished)).toInt()
            _timeHours.postValue(timeHours)

            val timeMin = TimeUnit.MILLISECONDS.toMinutes((totalTime - millisUntilFinished)).toInt()
            _timeMin.postValue(timeMin)

            val timeSec = ((totalTime/1000 - millisUntilFinished/1000) % 60).toInt()
            _timeSec.postValue(timeSec)
        }

        override fun onFinish() {
            _isRunning.value = true
        }

    }

    fun stopTimer() {
        timer?.cancel()
        _isRunning.value = false
    }

    fun startTimer() {
        timer?.start()
        _isRunning.value = true
    }
}