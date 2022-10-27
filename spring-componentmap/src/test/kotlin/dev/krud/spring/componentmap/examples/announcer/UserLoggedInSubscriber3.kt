package dev.krud.spring.componentmap.examples.announcer

internal class UserLoggedInSubscriber3 : AnnouncementSubscriber {
    override val type: AnnouncementType = AnnouncementType.USER_LOGGED_IN
    override fun onAnnouncement(announcement: Announcement) {
        println("UserLoggedInSubscriber3 received announcement: $announcement")
    }
}