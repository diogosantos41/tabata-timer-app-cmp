package com.dscoding.tabatatimer.data.audio

import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool
import android.util.Log
import com.dscoding.tabatatimer.R
import com.dscoding.tabatatimer.core.domain.WorkoutPreferences
import com.dscoding.tabatatimer.workout.data.audio.WorkoutAudioDefaults.SFX_VOLUME
import com.dscoding.tabatatimer.workout.domain.audio.WorkoutAudio
import com.dscoding.tabatatimer.workout.domain.audio.models.SoundEffect
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class AndroidWorkoutAudio(
    context: Context,
    preferences: WorkoutPreferences,
    applicationScope: CoroutineScope
) : WorkoutAudio {

    private val appContext = context.applicationContext

    private val soundPool: SoundPool
    private val sfxMap: Map<SoundEffect, Int>
    private val loaded = mutableSetOf<Int>()



    private val isSoundEnabled = preferences
        .observeSoundEnabled()
        .stateIn(
            scope = applicationScope,
            started = SharingStarted.Eagerly,
            initialValue = true
        )

    init {
        val attrs = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_GAME)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()

        soundPool = SoundPool.Builder()
            .setMaxStreams(4)
            .setAudioAttributes(attrs)
            .build()

        soundPool.setOnLoadCompleteListener { _, soundId, status ->
            if (status == 0) loaded.add(soundId)
        }

        sfxMap = mapOf(
            SoundEffect.WHISTLE to soundPool.load(appContext, R.raw.whistle, 1),
        )
    }

    override fun playSoundEffect(effect: SoundEffect) {
        if(!isSoundEnabled.value) return

        val id = sfxMap[effect] ?: return
        if (id !in loaded) {
            Log.d("AndroidWorkoutAudio", "SFX not loaded yet: $effect")
            return
        }
        soundPool.play(id, SFX_VOLUME, SFX_VOLUME, 0, 0, 1f)
    }
}