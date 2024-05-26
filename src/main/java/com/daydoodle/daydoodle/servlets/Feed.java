package com.daydoodle.daydoodle.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.daydoodle.daydoodle.common.*;
import com.daydoodle.daydoodle.ejb.*;
import com.daydoodle.daydoodle.entities.Picture;
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

@WebServlet(name = "Feed", value = "/Feed")
public class Feed extends HttpServlet {

    private static final Logger log = Logger.getLogger(Feed.class.getName());

    @Inject
    PostBean postBean;
    @Inject
    FriendshipBean friendshipBean;
    @Inject
    FunFactBean funFactBean;
    @Inject
    UserDetailsBean userDetailsBean;
    @Inject
    PictureBean pictureBean;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        List<PostDto> allPosts = postBean.findAllPosts();

        List<FriendshipDto> userFriendships = friendshipBean.findFriendshipsByUser(user.getUsername(), friendshipBean.findAllFriendships());
        List<String> userFriends = friendshipBean.findUserFriends(user.getUsername(), userFriendships);

        List<PostDto> friendsPosts = postBean.findFriendsPosts(userFriends, allPosts);
        List<UserDetailsDto> friendsUserDetails = userDetailsBean.findUserDetailsByUsernames(userFriends);

        // Create a map for friend's username to profile picture
        Map<String, Picture> friendsProfilePicturesMap = new HashMap<>();
        for (UserDetailsDto ud : friendsUserDetails) {
            friendsProfilePicturesMap.put(ud.getUsername(), ud.getProfilePicture());
        }

        FunFactDto funFact = funFactBean.getRandomFunFact();

        req.setAttribute("currentUser", user);
        req.setAttribute("friendsProfilePicturesMap", friendsProfilePicturesMap);
        req.setAttribute("funFact", funFact);
        req.setAttribute("posts", friendsPosts);
        req.getRequestDispatcher("/WEB-INF/userPages/feed.jsp").forward(req, resp);
    }
}
