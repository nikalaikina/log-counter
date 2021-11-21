package com.github.nikalaikina.coral.domain

import java.time.Instant

case class Log(eventType: EventType, data: Word, timestamp: Instant)
