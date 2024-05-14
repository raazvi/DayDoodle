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

@WebServlet(name = "RespondFriendRequest", value = "/RespondFriendRequest")
public class RespondFriendRequest extends HttpServlet {

    private static final Logger log = Logger.getLogger(RespondFriendRequest.class.getName());
    @Inject
    FriendshipBean friendshipBean;
    @Inject
    FriendshipRequestBean friendshipRequestBean;

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

            String reply = req.getParameter("response");
            Long reqId = null;
            String otherUserUsername = null;
            try {
                reqId = Long.parseLong(req.getParameter("frReqId"));
                otherUserUsername = req.getParameter("username");
            } catch (NumberFormatException e) {
                log.warning("Error parsing request ID: " + e.getMessage());
                return;
            }

            log.info("\n Entered RespondFriendRequest.doGet with the reply: " + reply + " " + reqId + " " + otherUserUsername + " \n");

            HttpSession session = req.getSession();
            User user = (User) session.getAttribute("user");
            if (user == null) {
                log.warning("User not found in session.");
                return;
            }

            if ("accept".equals(reply)) {
                friendshipRequestBean.setFriendshipStatusAccepted(reqId);
                friendshipBean.createFriendship(user.getUsername(), otherUserUsername);
            } else {
                friendshipRequestBean.setFriendshipStatusDeclined(reqId);
            }

            resp.sendRedirect(req.getContextPath() + "/ViewFriends");
        }
}
