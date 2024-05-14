package com.daydoodle.daydoodle.ejb;

import com.daydoodle.daydoodle.common.NotificationDto;
import com.daydoodle.daydoodle.ejb.FriendshipBean;
import com.daydoodle.daydoodle.entities.Notification;
import com.daydoodle.daydoodle.entities.Post;
import com.daydoodle.daydoodle.entities.User;
import jakarta.ejb.EJBException;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;


@Stateless
public class NotificationBean {

    private static final Logger log = Logger.getLogger(FriendshipBean.class.getName());
    @PersistenceContext
    EntityManager entityManager;

    /**
     * Executes a SELECT query on the database in order to fetch all the Notification objects in the table.
     */
    public List<NotificationDto> findAllNotifications(){

        log.info("\n Entered findAllNotifications \n");
        try {
            TypedQuery<Notification> typedQuery = entityManager.createQuery("SELECT notification FROM Notification notification", Notification.class);
            List<Notification> notifications = typedQuery.getResultList();

            Collections.sort(notifications, Comparator.comparing(Notification::getCreatedAt).reversed());

            log.info("\n** Exited NotificationDto method **\n");
            return copyNotificationsToDto(notifications);

        } catch (Exception ex) {
            log.info("\n Error in NotificationDto method! " + ex.getMessage() + " \n");
            throw new EJBException(ex);
        }

    }
    private List<NotificationDto> copyNotificationsToDto(List<Notification> notifications) {
        log.info("\n Entered copyNotificationsToDto \n");

        log.info("\n Entered copyNotificationsToDto method with list size of: " + notifications.size() + " \n");
        List<NotificationDto> listToReturn = new ArrayList<>();

        for (Notification notif : notifications) {
            // Use LocalDateTime directly
            NotificationDto notificationDto = new NotificationDto(notif.getId(), notif.getMessage(), notif.isSeen(), notif.getCreatedAt(), notif.getRecipient(), notif.getType(), notif.getPost());
            listToReturn.add(notificationDto);
        }

        log.info("\n Exited copyNotificationsToDto method. \n");
        return listToReturn;
    }

    public void sendFriendshipRequestNotification(String usernameFrom, String usernameTo){
        log.info("\n Entered sendFriendshipRequestNotification \n");
        User userFrom = entityManager.find(User.class, usernameFrom);
        User userTo = entityManager.find(User.class, usernameTo);
        Notification notification = new Notification();
        notification.setRecipient(userTo);
        notification.setCreatedAt(LocalDateTime.now());
        notification.setSeen(false);
        notification.setType(Notification.NotificationType.FRIEND_REQUEST_RECEIVED);
        notification.setMessage(userFrom.getUsername() + " has requested your friendship! Click here to see your friend requests.");
        userTo.getNotifications().add(notification);
        entityManager.merge(userTo);
        entityManager.persist(notification);
        log.info("\n Exited sendFriendshipRequestNotification \n");
    }
    public void sendCommentOnPostNotification(String usernameFrom, String usernameTo, Long postId){
        log.info("\n Entered sendCommentOnPostNotification \n");
        User userFrom = entityManager.find(User.class, usernameFrom);
        User userTo = entityManager.find(User.class, usernameTo);
        Post post = entityManager.find(Post.class, postId);
        Notification notification = new Notification();
        notification.setRecipient(userTo);
        notification.setCreatedAt(LocalDateTime.now());
        notification.setSeen(false);
        notification.setType(Notification.NotificationType.COMMENT_RECEIVED);
        notification.setMessage(usernameFrom+" has left a comment on your post! Click here to see their comment. ");
        userTo.getNotifications().add(notification);
        notification.setPost(post);
        entityManager.merge(userTo);
        entityManager.persist(notification);
        log.info("\n Exited sendCommentOnPostNotification \n");

    }
    public void sendReactOnPostNotification(String usernameFrom, String usernameTo, Long postId){
        log.info("\n Entered sendCommentOnPostNotification \n");
        User userFrom = entityManager.find(User.class, usernameFrom);
        User userTo = entityManager.find(User.class, usernameTo);
        Post post = entityManager.find(Post.class, postId);
        Notification notification = new Notification();
        notification.setRecipient(userTo);
        notification.setCreatedAt(LocalDateTime.now());
        notification.setSeen(false);
        notification.setType(Notification.NotificationType.REACTION_TO_POST);
        notification.setMessage(usernameFrom+" has reacted to your post! Click here to see their reaction. ");
        userTo.getNotifications().add(notification);
        notification.setPost(post);
        entityManager.merge(userTo);
        entityManager.persist(notification);
        log.info("\n Exited sendCommentOnPostNotification \n");

    }

    /**
     * Finds the notifications of a user.
     */
    public List<NotificationDto> findAllNotificationsByUsername(String username){
        log.info("\n Entered findAllNotificationsByUsername \n");
        List<NotificationDto> allNotifications = findAllNotifications();
        List<NotificationDto> filteredNotifications = new ArrayList<>();
        for (NotificationDto notificationDto : allNotifications) {
            if(notificationDto.getRecipient().equals(username)){
                filteredNotifications.add(notificationDto);
            }
        }
        log.info("\n Exited findAllNotificationsByUsername \n");
        return filteredNotifications;
    }

    /**
     * Checks if user has new notifications.
     */
    public boolean doesUserHaveNewNotification(String username){
        log.info("\n Entered doesUserHaveNewNotification \n");
        User userFrom = entityManager.find(User.class, username);
        List<NotificationDto> allNotifications = findAllNotificationsByUsername(username);
        for (NotificationDto notificationDto : allNotifications) {
            if(!notificationDto.isSeen()){
                return true;
            }
        }

        return false;
    }

    /**
     * Counts the unread notifications
     */
    public int getUnreadNotificationCount(String username) {
        // Query the database to count unread notifications for the user
        List<NotificationDto> notifications = findAllNotificationsByUsername(username);
        int unreadCount = 0;
        for (NotificationDto notification : notifications) {
            if (!notification.isSeen()) {
                unreadCount++;
            }
        }
        return unreadCount;
    }
}
