package dev.krud.spring.componentmap.examples.announcer

internal class UserLoggedInSubscriber2 : AnnouncementSubscriber {
    override val type: AnnouncementType = AnnouncementType.USER_LOGGED_IN
    override fun onAnnouncement(announcement: Announcement) {
        println("UserLoggedInSubscriber2 received announcement: $announcement")
    }
}