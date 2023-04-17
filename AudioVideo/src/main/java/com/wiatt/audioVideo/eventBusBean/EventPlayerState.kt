package com.wiatt.audioVideo.eventBusBean

import com.wiatt.audioVideo.player.Player
import com.wiatt.audioVideo.player.PlayerState

class EventPlayerState constructor(var player: Player, var newState: PlayerState, var lastState: PlayerState)