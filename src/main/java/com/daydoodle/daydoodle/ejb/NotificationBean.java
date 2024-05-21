package com.daydoodle.daydoodle.ejb;

import com.daydoodle.daydoodle.common.NotificationDto;
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

    private static final Logger log = Logger.getLogger(NotificationBean.class.getName());
    @PersistenceContext
    EntityManager entityManager;

    public List<NotificationDto> findAllNotifications(){
        log.info("\n Entered findAllNotifications \n");
        try {
            TypedQuery<Notification> typedQuery = entityManager.createQuery("SELECT notification FROM Notification notification", Notification.class);
            List<Notification> notifications = typedQuery.getResultList();
            Collections.sort(notifications, Comparator.comparing(Notification::getCreatedAt).reversed());
            log.info("\n** Exited findAllNotifications method **\n");
            return copyNotificationsToDto(notifications);
        } catch (Exception ex) {
            log.info("\n Error in findAllNotifications method! " + ex.getMessage() + " \n");
            throw new EJBException(ex);
        }
    }

    private List<NotificationDto> copyNotificationsToDto(List<Notification> notifications) {
        log.info("\n Entered copyNotificationsToDto \n");
        log.info("\n Entered copyNotificationsToDto method with list size of: " + notifications.size() + " \n");
        List<NotificationDto> listToReturn = new ArrayList<>();
        for (Notification notif : notifications) {
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
        log.info("Persisted FriendshipRequestNotification with ID: " + notification.getId());
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
        notification.setMessage(usernameFrom + " has left a comment on your post! Click here to see their comment.");
        userTo.getNotifications().add(notification);
        notification.setPost(post);
        entityManager.merge(userTo);
        entityManager.persist(notification);
        log.info("Persisted CommentOnPostNotification with ID: " + notification.getId());
        log.info("\n Exited sendCommentOnPostNotification \n");
    }

    public void sendReactOnPostNotification(String usernameFrom, String usernameTo, Long postId){
        log.info("\n Entered sendReactOnPostNotification \n");
        User userFrom = entityManager.find(User.class, usernameFrom);
        User userTo = entityManager.find(User.class, usernameTo);
        Post post = entityManager.find(Post.class, postId);
        Notification notification = new Notification();
        notification.setRecipient(userTo);
        notification.setCreatedAt(LocalDateTime.now());
        notification.setSeen(false);
        notification.setType(Notification.NotificationType.REACTION_TO_POST);
        notification.setMessage(usernameFrom + " has reacted to your post! Click here to see their reaction.");
        userTo.getNotifications().add(notification);
        notification.setPost(post);
        entityManager.merge(userTo);
        entityManager.persist(notification);
        log.info("Persisted ReactOnPostNotification with ID: " + notification.getId());
        log.info("\n Exited sendReactOnPostNotification \n");
    }

    public List<NotificationDto> findAllNotificationsByUsername(String username) {
        log.info("\n Entered findAllNotificationsByUsername \n");
        try {
            TypedQuery<Notification> typedQuery = entityManager.createQuery(
                    "SELECT n FROM Notification n WHERE n.recipient.username = :username", Notification.class);
            typedQuery.setParameter("username", username);
            List<Notification> notifications = typedQuery.getResultList();
            Collections.sort(notifications, Comparator.comparing(Notification::getCreatedAt).reversed());
            log.info("Fetched " + notifications.size() + " notifications for username: " + username);
            return copyNotificationsToDto(notifications);
        } catch (Exception ex) {
            log.info("\n Error in findAllNotificationsByUsername method! " + ex.getMessage() + " \n");
            throw new EJBException(ex);
        }
    }

    public List<NotificationDto> findUnreadNotificationsByUsername(String username) {
        log.info("\n Entered findUnreadNotificationsByUsername \n");
        List<NotificationDto> allNotifications = findAllNotificationsByUsername(username);
        List<NotificationDto> unreadNotifications = new ArrayList<>();
        for (NotificationDto notificationDto : allNotifications) {
            if (!notificationDto.isSeen()) {
                unreadNotifications.add(notificationDto);
            }
        }
        log.info("Found " + unreadNotifications.size() + " unread notifications for username: " + username);
        log.info("\n Exited findUnreadNotificationsByUsername \n");
        return unreadNotifications;
    }

    public List<NotificationDto> findReadNotificationsByUsername(String username) {
        log.info("\n Entered findReadNotificationsByUsername \n");
        List<NotificationDto> allNotifications = findAllNotificationsByUsername(username);
        List<NotificationDto> readNotifications = new ArrayList<>();
        for (NotificationDto notificationDto : allNotifications) {
            if (notificationDto.isSeen()) {
                readNotifications.add(notificationDto);
            }
        }
        log.info("Found " + readNotifications.size() + " read notifications for username: " + username);
        log.info("\n Exited findReadNotificationsByUsername \n");
        return readNotifications;
    }

    /**
     * Finds a notification by its id.
     */
    public NotificationDto findNotificationById(Long id) {
        log.info("\n Entered findNotificationById \n");
        Notification notification = entityManager.find(Notification.class, id);
        NotificationDto notificationDto = new NotificationDto(notification.getId(),notification.getMessage(),notification.isSeen(),notification.getCreatedAt(),notification.getRecipient(),notification.getType(),notification.getPost());
        log.info("\n Exited findNotificationById \n");
        return notificationDto;

    }

    /**
     * Marks a notification as seen
     */
    public void markNotificationAsSeen(Long notificationId) {
        log.info("\n Entered markNotificationAsSeen \n");
        Notification notification = entityManager.find(Notification.class, notificationId);
        notification.setSeen(true);
        entityManager.merge(notification);
        log.info("\n Exited markNotificationAsSeen \n");
    }
}
