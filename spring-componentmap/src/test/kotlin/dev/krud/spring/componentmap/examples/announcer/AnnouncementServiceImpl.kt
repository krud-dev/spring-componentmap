package dev.krud.spring.componentmap.examples.announcer

import dev.krud.spring.componentmap.ComponentMap

internal class AnnouncementServiceImpl : AnnouncementService {
    @ComponentMap
    private lateinit var subscribers: Map<AnnouncementType, List<AnnouncementSubscriber>>

    override fun announce(announcement: Announcement): Long {
        return subscribers[announcement.type]?.sumOf {
            it.onAnnouncement(announcement)
            1L
        } ?: 0L
    }
}