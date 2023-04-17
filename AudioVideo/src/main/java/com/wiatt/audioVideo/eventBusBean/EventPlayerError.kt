package com.wiatt.audioVideo.eventBusBean

import com.wiatt.audioVideo.player.Player

class EventPlayerError(var player: Player, var errorType: Int)