package com.daydoodle.daydoodle.servlets;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.logging.Logger;

import com.daydoodle.daydoodle.common.NotificationDto;
import com.daydoodle.daydoodle.common.PostDto;
import com.daydoodle.daydoodle.common.UserDetailsDto;
import com.daydoodle.daydoodle.ejb.CustomActivityBean;
import com.daydoodle.daydoodle.ejb.NotificationBean;
import com.daydoodle.daydoodle.ejb.PostBean;
import com.daydoodle.daydoodle.ejb.UserDetailsBean;
import com.daydoodle.daydoodle.entities.User;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "Profile", value = "/Profile")
public class Profile extends HttpServlet {

    private static final Logger log = Logger.getLogger(Profile.class.getName());

    @Inject
    UserDetailsBean userDetailsBean;
    @Inject
    CustomActivityBean customActivityBean;
    @Inject
    PostBean postBean;
    @Inject
    NotificationBean notificationBean;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        log.info("Entered Profile.doGet");
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            log.warning("User is not logged in");
            resp.sendRedirect("login.jsp");
            return;
        }

        UserDetailsDto thisUser = userDetailsBean.getUserDetailsByUsername(user.getUsername(), userDetailsBean.findAllUserDetails());

        LocalDate today = LocalDate.now();
        LocalDate birthday = thisUser.getBirthDate();

        LocalDate futureBirthday = LocalDate.of(today.getYear(), birthday.getMonth(), birthday.getDayOfMonth());

        if (futureBirthday.isBefore(today) || futureBirthday.isEqual(today)) {
            futureBirthday = futureBirthday.plusYears(1);
        }

        long daysTilBirthday = ChronoUnit.DAYS.between(today, futureBirthday);

        List<PostDto> allUserPosts = postBean.findPostsByUser(user.getUsername());

        List<NotificationDto> allReadNotifications = notificationBean.findReadNotificationsByUsername(user.getUsername());
        List<NotificationDto> allUnreadNotifications = notificationBean.findUnreadNotificationsByUsername(user.getUsername());

        log.info("Fetched " + allReadNotifications.size() + " read notifications and " + allUnreadNotifications.size() + " unread notifications");

        req.setAttribute("readNotifications", allReadNotifications);
        req.setAttribute("unreadNotifications", allUnreadNotifications);
        req.setAttribute("unreadNotificationsCount", allUnreadNotifications.size());
        req.setAttribute("customActivities", customActivityBean.findAllCustomActivitiesByUsername(user.getUsername(), customActivityBean.findAllCustomActivities()));
        req.setAttribute("user", thisUser);
        req.setAttribute("daysTilBirthday", daysTilBirthday);
        req.setAttribute("posts", allUserPosts);

        log.info("Exited Profile.doGet, redirecting to user's profile. Username: " + thisUser.getUsername());
        req.getRequestDispatcher("/WEB-INF/userPages/userProfile.jsp").forward(req, resp);
    }
}
