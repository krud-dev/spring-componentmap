package dev.krud.spring.componentmap.examples.announcer

internal interface AnnouncementService {
    /**
     * Announce an event to all subscribers and return the number of subscribers that received the announcement.
     */
    fun announce(announcement: Announcement): Long
}