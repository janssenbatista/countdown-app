package com.example.countdownapp.utils

class TimeFormatter {
    companion object {
        fun format(time: Long): String {
            val minutes = time / 60 / 1000
            val seconds = (time / 1000) % 60
            val secondsInText: String = if (seconds < 10) "0$seconds" else seconds.toString()
            return "$minutes:$secondsInText"
        }
    }
}