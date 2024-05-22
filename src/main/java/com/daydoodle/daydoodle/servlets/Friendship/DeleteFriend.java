package com.daydoodle.daydoodle.servlets.Friendship;

import java.io.IOException;

import com.daydoodle.daydoodle.common.FriendshipRequestDto;
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

@WebServlet(name = "DeleteFriend", value = "/DeleteFriend")
public class DeleteFriend extends HttpServlet {

    private static final Logger log = Logger.getLogger(DeleteFriend.class.getName());

    @Inject
    FriendshipBean friendshipBean;
    @Inject
    FriendshipRequestBean friendshipRequestBean;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        String friendUsername=req.getParameter("username");
        String friendshipIdStr=req.getParameter("friendshipId");
        Long friendshipId=Long.parseLong(friendshipIdStr);
        friendshipBean.removeFriend(friendshipId,user.getUsername(),friendUsername);
        FriendshipRequestDto frq=friendshipRequestBean.findFriendshipRequestFromTo(user.getUsername(),friendUsername);
        if(frq!=null) {
            friendshipRequestBean.removeFriendshipRequest(frq);
        }else{
            frq=friendshipRequestBean.findFriendshipRequestFromTo(friendUsername,user.getUsername());
            friendshipRequestBean.removeFriendshipRequest(frq);
        }
        resp.sendRedirect(req.getContextPath() + "/ViewFriends");
    }
}