package com.daydoodle.daydoodle.servlets;

import java.io.IOException;

import com.daydoodle.daydoodle.common.FriendshipDto;
import com.daydoodle.daydoodle.common.PostDto;
import com.daydoodle.daydoodle.ejb.ActivityBean;
import com.daydoodle.daydoodle.ejb.CustomActivityBean;
import com.daydoodle.daydoodle.ejb.FriendshipBean;
import com.daydoodle.daydoodle.ejb.PostBean;
import com.daydoodle.daydoodle.entities.CustomActivity;
import com.daydoodle.daydoodle.entities.User;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.logging.Logger;

@WebServlet(name = "Feed", value = "/Feed")
public class Feed extends HttpServlet {

    private static final Logger log = Logger.getLogger(Feed.class.getName());

    @Inject
    PostBean postBean;
    @Inject
    FriendshipBean friendshipBean;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        List<PostDto> allPosts = postBean.findAllPosts();

        //... days ago la postari.......

        List<FriendshipDto> userFriendships = friendshipBean.findFriendshipsByUser(user.getUsername(),friendshipBean.findAllFriendships());
        List<String> userFriends=friendshipBean.findUserFriends(user.getUsername(),userFriendships);

        List<PostDto> friendsPosts=postBean.findFriendsPosts(userFriends,allPosts);

        req.setAttribute("posts",friendsPosts);
        req.getRequestDispatcher("/WEB-INF/userPages/feed.jsp").forward(req,resp);
    }
}