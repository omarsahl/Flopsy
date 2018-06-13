package com.os.flopsy

import com.airbnb.lottie.LottieAnimationView
import timber.log.Timber

/**
 * Created by Omar on 11-Jun-18 6:27 PM.
 */

class Bunn(private val animationView: LottieAnimationView) {
    companion object {
        const val FORWARD = 1.0f
        const val REVERSE = -1.0f

        const val TRACKING_START = 4.0f
        const val TRACKING_END = 22.0f
        const val TOTAL_FRAMES = 77.0f

        const val NEUTRAL = 0
        const val TRACKING = 1
        const val SHY = 2
        const val PEEK = 3

        const val NEUTRAL_TO_SHY = 101
        const val SHY_TO_NEUTRAL = 102

        const val NEUTRAL_TO_PEEK = 103
        const val PEEK_TO_NEUTRAL = 104

        const val PEEK_TO_SHY = 105
        const val SHY_TO_PEEK = 106

        const val NEUTRAL_TO_TRACKING = 107
        const val TRACKING_TO_NEUTRAL = 108
    }

    private var currentState = NEUTRAL
    private var playingDirection = FORWARD

    private val bunnConstants = mapOf(
            NEUTRAL_TO_TRACKING to arrayOf(4, 22),
            TRACKING_TO_NEUTRAL to arrayOf(0, 0),

            NEUTRAL_TO_SHY to arrayOf(29, 39),
            SHY_TO_NEUTRAL to arrayOf(44, 54),

            SHY_TO_PEEK to arrayOf(59, 68),
            PEEK_TO_SHY to arrayOf(59, 68), // use negative speed

            NEUTRAL_TO_PEEK to arrayOf(68, 76), // use negative speed
            PEEK_TO_NEUTRAL to arrayOf(68, 76)
    )

    init {
        animationView.apply {
            setAnimation("bunn_new_mouth.json")
        }

        setNeutralState()
    }

    fun setEyesPosition(progress: Float) {
        if (currentState != TRACKING) return

        val value = map(progress, 0.0f, 1.0f, TRACKING_START / TOTAL_FRAMES, TRACKING_END / TOTAL_FRAMES)

        Timber.d("Progress mapped value = $value")
        animationView.progress = value
    }

    fun setNeutralState() {
        setForward()
        when (currentState) {
            NEUTRAL -> {
                return
            }
            SHY -> {
                setMinMaxFrame(SHY_TO_NEUTRAL)
            }
            PEEK -> {
                setMinMaxFrame(PEEK_TO_NEUTRAL)
            }
            TRACKING -> {
                setMinMaxFrame(TRACKING_TO_NEUTRAL)
            }
        }

        play()

        currentState = NEUTRAL
    }

    fun setShyState() {
        setForward()
        when (currentState) {
            SHY -> {
                return
            }
            NEUTRAL, TRACKING -> {
                setMinMaxFrame(NEUTRAL_TO_SHY)
            }
            PEEK -> {
                setReverse()
                setMinMaxFrame(PEEK_TO_SHY)
            }
        }

        play()

        currentState = SHY
    }

    fun setPeekState() {
        setForward()
        when (currentState) {
            PEEK -> {
                return
            }
            NEUTRAL, TRACKING -> {
                setReverse()
                setMinMaxFrame(NEUTRAL_TO_PEEK)
            }
            SHY -> {
                setMinMaxFrame(SHY_TO_PEEK)
            }
        }

        play()

        currentState = PEEK
    }

    fun setPreTrackingState() {
        setForward()
        when (currentState) {
            TRACKING -> {
                return
            }
            NEUTRAL -> {
                setMinMaxFrame(TRACKING_TO_NEUTRAL)
            }
            SHY -> {
                setMinMaxFrame(SHY_TO_NEUTRAL)
            }
            PEEK -> {
                setMinMaxFrame(PEEK_TO_NEUTRAL)
            }
        }

        play()

        currentState = TRACKING
    }

    fun startTracking() {
        if (currentState != TRACKING) {
            return
        }

        setMinMaxFrame(NEUTRAL_TO_TRACKING)
        currentState = TRACKING
    }

    private fun setMinMaxFrame(state: Int) {
        animationView.setMinAndMaxFrame(bunnConstants[state]!![0], bunnConstants[state]!![1])
    }

    private fun play() {
        animationView.playAnimation()
    }

    private fun setReverse() {
        animationView.speed = -1.0f
        playingDirection = REVERSE
    }

    private fun setForward() {
        animationView.speed = 1.0f
        playingDirection = FORWARD
    }
}
