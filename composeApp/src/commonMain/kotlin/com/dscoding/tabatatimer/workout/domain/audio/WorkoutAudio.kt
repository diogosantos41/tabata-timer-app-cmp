package com.dscoding.tabatatimer.workout.domain.audio

import com.dscoding.tabatatimer.workout.domain.audio.models.SoundEffect

interface WorkoutAudio {
    fun playSoundEffect(effect: SoundEffect)
}