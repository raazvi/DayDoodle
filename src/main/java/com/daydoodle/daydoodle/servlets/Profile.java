package com.daydoodle.daydoodle.servlets;

import java.io.IOException;

import com.daydoodle.daydoodle.common.PostDto;
import com.daydoodle.daydoodle.common.UserDetailsDto;
import com.daydoodle.daydoodle.ejb.CustomActivityBean;
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

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.logging.Logger;

@WebServlet(name = "Profile", value = "/Profile")
public class Profile extends HttpServlet {

    private static final Logger log = Logger.getLogger(Profile.class.getName());

    @Inject
    UserDetailsBean userDetailsBean;
    @Inject
    CustomActivityBean customActivityBean;
    @Inject
    PostBean postBean;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        log.info("\n Entered Profile.doGet \n");
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        UserDetailsDto thisUser = userDetailsBean.getUserDetailsByUsername(user.getUsername(),userDetailsBean.findAllUserDetails());

        LocalDate today= LocalDate.now();
        LocalDate birthday= thisUser.getBirthDate();

        LocalDate futureBirthday=LocalDate.of(today.getYear(),birthday.getMonth(),birthday.getDayOfMonth());

        if (futureBirthday.isBefore(today) || futureBirthday.isEqual(today)) {
            futureBirthday = futureBirthday.plusYears(1);
        }

        long daysTilBirthday= ChronoUnit.DAYS.between(today,futureBirthday);

        List<PostDto> allUserPosts=postBean.findPostsByUser(user.getUsername());

        req.setAttribute("customActivities", customActivityBean.findAllCustomActivitiesByUsername(user.getUsername(),customActivityBean.findAllCustomActivities()));
        req.setAttribute("user", thisUser);
        req.setAttribute("daysTilBirthday",daysTilBirthday);
        req.setAttribute("posts",allUserPosts);
        log.info("\n Exited Profile.doGet, redirecting to user's profile. Username: "+ thisUser.getUsername() +" \n");
        req.getRequestDispatcher("/WEB-INF/userPages/userProfile.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}