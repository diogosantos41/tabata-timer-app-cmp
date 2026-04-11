package com.dscoding.tabatatimer.workout.domain.session

data class SessionEngineState(
    val currentIndex: Int = 0,
    val remainingMillis: Long = 0L,
    val playState: SessionPlayState = SessionPlayState.Running,
)
