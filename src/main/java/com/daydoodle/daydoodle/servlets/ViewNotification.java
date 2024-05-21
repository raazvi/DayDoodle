package com.daydoodle.daydoodle.servlets;

import java.io.IOException;

import com.daydoodle.daydoodle.common.NotificationDto;
import com.daydoodle.daydoodle.ejb.NotificationBean;
import com.daydoodle.daydoodle.entities.Notification;
import com.daydoodle.daydoodle.entities.User;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.logging.Logger;

@WebServlet(name = "ViewNotification", value = "/ViewNotification")
public class ViewNotification extends HttpServlet {

    private static final Logger log = Logger.getLogger(ViewNotification.class.getName());

    @Inject
    NotificationBean notificationBean;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        String notificationIdString=req.getParameter("notificationId");
        Long notificationId=Long.parseLong(notificationIdString);
        NotificationDto thisNotification = notificationBean.findNotificationById(notificationId);
        if(thisNotification!=null) {
            if(thisNotification.getType().equals(Notification.NotificationType.FRIEND_REQUEST_RECEIVED)){
                notificationBean.markNotificationAsSeen(notificationId);
                resp.sendRedirect(req.getContextPath()+"/ViewFriends");
            }else{
                notificationBean.markNotificationAsSeen(notificationId);
                resp.sendRedirect(req.getContextPath()+"/ViewPost?postId="+thisNotification.getPost().getId());
            }
        }

    }
}