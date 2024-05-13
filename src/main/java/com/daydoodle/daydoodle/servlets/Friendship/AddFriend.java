package com.daydoodle.daydoodle.servlets.Friendship;

import java.io.IOException;

import com.daydoodle.daydoodle.ejb.FriendshipBean;
import com.daydoodle.daydoodle.ejb.FriendshipRequestBean;
import com.daydoodle.daydoodle.entities.User;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.logging.Logger;

@WebServlet(name = "AddFriend", value = "/AddFriend")
public class AddFriend extends HttpServlet {

    private static final Logger log = Logger.getLogger(AddFriend.class.getName());

    @Inject
    FriendshipBean friendshipBean;
    @Inject
    FriendshipRequestBean friendshipRequestBean;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        String otherUserUsername = req.getParameter("username");

        if (otherUserUsername != null && !otherUserUsername.isEmpty()) {

            friendshipRequestBean.createFriendshipRequest(user.getUsername(), otherUserUsername);
            req.setAttribute("friendshipRequestSent", true);
            resp.sendRedirect(req.getContextPath()+"/UserProfile?username="+otherUserUsername);
        } else {

            resp.sendRedirect(req.getContextPath() + "/yourProfilePage.jsp?error=Username cannot be empty");
        }
    }

}