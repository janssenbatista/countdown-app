package com.example.countdownapp

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.countdownapp.utils.TimeFormatter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private var time: Long = 25 * 60 * 1000 // 25 minutes
    private var isRunning = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvTimer.text = TimeFormatter.format(time)

        playButton.setOnClickListener {
            hidePlayButton()
            CoroutineScope(Dispatchers.Main).launch {
                runTimer()
            }
        }

        stopButton.setOnClickListener {
            isRunning = false
            time = 25 * 60 * 1000 // 25 minutes
            tvTimer.text = TimeFormatter.format(time)
            translateXView(stopButton, 0f)
            translateXView(pauseButton, 0f)
            translateXView(playButton, 0f)
            showPlayButton()
        }

        pauseButton.setOnClickListener {
            isRunning = false
            showPlayButton()
            translateXView(playButton, -160f)
        }
    }

    private suspend fun runTimer() {
        isRunning = true
        while (isRunning && time > 0) {
            delay(1_000)
            time -= 1_000
            tvTimer.text = TimeFormatter.format(time)
        }
    }

    private fun hidePlayButton() {
        val hideAnimator = ObjectAnimator.ofFloat(playButton, View.ALPHA, 0f)
        hideAnimator.duration = 300
        hideAnimator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                playButton.visibility = View.GONE
                translateXView(stopButton, 160f)
                translateXView(pauseButton, -160f)
            }
        })
        hideAnimator.start()
    }

    private fun showPlayButton() {
        val showAnimator = ObjectAnimator.ofFloat(playButton, View.ALPHA, 1f)
        showAnimator.duration = 400
        showAnimator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?, isReverse: Boolean) {
                playButton.visibility = View.VISIBLE
            }
        })
        showAnimator.start()
    }

    private fun translateXView(view: View, position: Float) {
        val translateAnimator = ObjectAnimator.ofFloat(view, View.TRANSLATION_X, position)
        translateAnimator.duration = 300
        translateAnimator.start()
    }
}