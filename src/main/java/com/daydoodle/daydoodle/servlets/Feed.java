package com.daydoodle.daydoodle.servlets;

import java.io.IOException;

import com.daydoodle.daydoodle.common.PostDto;
import com.daydoodle.daydoodle.ejb.ActivityBean;
import com.daydoodle.daydoodle.ejb.CustomActivityBean;
import com.daydoodle.daydoodle.ejb.PostBean;
import com.daydoodle.daydoodle.entities.CustomActivity;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.logging.Logger;

@WebServlet(name = "Feed", value = "/Feed")
public class Feed extends HttpServlet {

    private static final Logger log = Logger.getLogger(Feed.class.getName());

    @Inject
    PostBean postBean;
//    @Inject
//    FriendshipBean friendshipBean;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<PostDto> allPosts = postBean.findAllPosts();
        //List<FriendshipDto> allFriendships = friendshipBean.findAllFriendships();

        //... days ago la postari.......

        //List<FriendshipDto> userFriends=friendshipBean.findFriendshipsByUser("razvi",allFriendships);
        //List<PostDto> friendsPosts = postBean.findFriendsPosts(userFriends,allPosts);

        req.setAttribute("posts",allPosts);
        req.getRequestDispatcher("/WEB-INF/userPages/feed.jsp").forward(req,resp);
    }
}