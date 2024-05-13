package com.daydoodle.daydoodle.servlets.Friendship;

import java.io.IOException;

import com.daydoodle.daydoodle.common.FriendshipDto;
import com.daydoodle.daydoodle.common.FriendshipRequestDto;
import com.daydoodle.daydoodle.common.UserDto;
import com.daydoodle.daydoodle.ejb.FriendshipBean;
import com.daydoodle.daydoodle.ejb.FriendshipRequestBean;
import com.daydoodle.daydoodle.ejb.UserBean;
import com.daydoodle.daydoodle.entities.User;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@WebServlet(name = "ViewFriends", value = "/ViewFriends")
public class ViewFriends extends HttpServlet {

    private static final Logger log = Logger.getLogger(ViewFriends.class.getName());

    @Inject
    FriendshipBean friendshipBean;
    @Inject
    UserBean userBean;
    @Inject
    FriendshipRequestBean friendshipRequestBean;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        List<FriendshipDto> allFriendship=friendshipBean.findFriendshipsByUser(user.getUsername(),friendshipBean.findAllFriendships());
        List<UserDto> allUsers=userBean.findAllUsers();
        List<String> allUsernames=new ArrayList<>();

        List<FriendshipRequestDto> friendshipRequestsReceived=new ArrayList<>();
        friendshipRequestsReceived=friendshipRequestBean.getAllReceivedRequestsByUser(user.getUsername(),friendshipRequestBean.findAllFriendshipRequests());

        req.setAttribute("friendRequestsReceived",friendshipRequestsReceived);

        for(UserDto userDto:allUsers){
            allUsernames.add(userDto.getUsername());
        }

        req.setAttribute("allUsernames",allUsernames);
        req.setAttribute("friends",allFriendship);

        log.info("\n Exited ViewFriends.doGet forwarding to viewFriends.jsp \n");
        req.getRequestDispatcher("/WEB-INF/userPages/friendship/viewFriends.jsp").forward(req, resp);
    }
}