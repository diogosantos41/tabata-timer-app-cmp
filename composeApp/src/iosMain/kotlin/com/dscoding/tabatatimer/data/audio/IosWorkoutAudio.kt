@file:OptIn(ExperimentalForeignApi::class)

package com.dscoding.tabatatimer.data.audio

import com.dscoding.tabatatimer.core.domain.WorkoutPreferences
import com.dscoding.tabatatimer.workout.data.audio.WorkoutAudioDefaults.SFX_VOLUME
import com.dscoding.tabatatimer.workout.domain.audio.WorkoutAudio
import com.dscoding.tabatatimer.workout.domain.audio.models.SoundEffect
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import platform.AVFAudio.AVAudioPlayer
import platform.Foundation.NSBundle

class IosWorkoutAudio(
    preferences: WorkoutPreferences,
    applicationScope: CoroutineScope
) : WorkoutAudio {

    private val isSoundEnabled = preferences
        .observeSoundEnabled()
        .stateIn(
            scope = applicationScope,
            started = SharingStarted.Eagerly,
            initialValue = true
        )


    private val sfxPlayers = mutableMapOf<SoundEffect, AVAudioPlayer>()

    override fun playSoundEffect(effect: SoundEffect) {
        if (!isSoundEnabled.value) return

        val fileName = when (effect) {
            SoundEffect.WHISTLE -> "whistle"
        }

        val url = NSBundle.mainBundle.URLForResource(
            name = fileName,
            withExtension = "mp3"
        ) ?: return

        val player = sfxPlayers.getOrPut(effect) {
            AVAudioPlayer(contentsOfURL = url, error = null).apply {
                volume = SFX_VOLUME
                prepareToPlay()
            }
        }

        player.stop()
        player.currentTime = 0.0
        player.play()
    }
}