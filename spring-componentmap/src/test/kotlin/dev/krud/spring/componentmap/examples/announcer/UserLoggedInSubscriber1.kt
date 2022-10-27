package dev.krud.spring.componentmap.examples.announcer

internal class UserLoggedInSubscriber1 : AnnouncementSubscriber {
    override val type: AnnouncementType = AnnouncementType.USER_LOGGED_IN
    override fun onAnnouncement(announcement: Announcement) {
        println("UserLoggedInSubscriber1 received announcement: $announcement")
    }
}