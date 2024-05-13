package com.daydoodle.daydoodle.servlets.Friendship;

import java.io.IOException;

import com.daydoodle.daydoodle.common.UserDetailsDto;
import com.daydoodle.daydoodle.ejb.FriendshipBean;
import com.daydoodle.daydoodle.ejb.FriendshipRequestBean;
import com.daydoodle.daydoodle.ejb.UserDetailsBean;
import com.daydoodle.daydoodle.entities.User;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.logging.Logger;

@WebServlet(name = "UserProfile", value = "/UserProfile")
public class UserProfile extends HttpServlet {

    private static final Logger log = Logger.getLogger(UserProfile.class.getName());

    @Inject
    UserDetailsBean userDetailsBean;
    @Inject
    FriendshipBean friendshipBean;
    @Inject
    FriendshipRequestBean friendshipRequestBean;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        log.info("userProfile.doGet, the user clicked on is: "+req.getParameter("username"));
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        String otherUserUsername=req.getParameter("username");

        if(friendshipRequestBean.isFriendshipRequestPending(user.getUsername(), otherUserUsername)){
            req.setAttribute("friendshipRequestPending", true);
        }else{
            req.setAttribute("friendshipRequestPending", false);
        }

        if(friendshipRequestBean.userSentMeAFriendshipRequest(otherUserUsername, user.getUsername())){
            req.setAttribute("userSentMeAFriendshipRequest", true);
        }else{
            req.setAttribute("userSentMeAFriendshipRequest", false);
        }


        if(friendshipBean.isUserMyFriend(user.getUsername(),otherUserUsername)) {
            req.setAttribute("friends",true);
        }else{
            req.setAttribute("friends",false);
        }



        UserDetailsDto otherUserDetails=userDetailsBean.getUserDetailsByUsername(otherUserUsername,userDetailsBean.findAllUserDetails());

        req.setAttribute("userDetails",otherUserDetails);

        req.getRequestDispatcher("/WEB-INF/userPages/friendship/viewUserProfile.jsp").forward(req, resp);

    }
}