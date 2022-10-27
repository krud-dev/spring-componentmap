package dev.krud.spring.componentmap.examples.announcer

import dev.krud.spring.componentmap.ComponentMapKey

internal interface AnnouncementSubscriber {
    @get:ComponentMapKey
    val type: AnnouncementType
    fun onAnnouncement(announcement: Announcement)
}