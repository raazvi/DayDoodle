package com.daydoodle.daydoodle.servlets.Post;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

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

@WebServlet(name = "ViewPost", value = "/ViewPost")
public class ViewPost extends HttpServlet {

    private static final Logger log = Logger.getLogger(ViewPost.class.getName());

    @Inject
    PostBean postBean;
    @Inject
    PostReactionBean postReactionBean;
    @Inject
    PostCommentBean postCommentBean;
    @Inject
    FriendshipBean friendshipBean;
    @Inject
    UserDetailsBean userDetailsBean;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String postIdStr = req.getParameter("postId");
        Long postId = Long.parseLong(postIdStr);
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        log.info("\n Entered viewPostComments.doGet with postId: " + postIdStr);

        // Fetch the post, comments, and reactions
        PostDto post = postBean.findPostById(postId);
        List<PostReactionDto> allReactionsForPost = postReactionBean.findAllReactionsForPost(postId);
        List<PostCommentDto> allCommentsForPost = postCommentBean.findAllCommentsForPost(postId);

        // Fetch user details for reactions and comments
        Map<String, Picture> userPicturesMap = new HashMap<>();
        for (PostReactionDto reaction : allReactionsForPost) {
            UserDetailsDto userDetails = userDetailsBean.getUserDetailsByUsername(reaction.getUser().getUsername(),userDetailsBean.findAllUserDetails());
            if (userDetails != null) {
                userPicturesMap.put(reaction.getUser().getUsername(), userDetails.getProfilePicture());
            }
        }
        for (PostCommentDto comment : allCommentsForPost) {
            UserDetailsDto userDetails = userDetailsBean.getUserDetailsByUsername(comment.getUser().getUsername(),userDetailsBean.findAllUserDetails());
            if (userDetails != null) {
                userPicturesMap.put(comment.getUser().getUsername(), userDetails.getProfilePicture());
            }
        }

        // Fetch author details
        UserDetailsDto authorDetails = userDetailsBean.getUserDetailsByUsername(post.getAuthor().getUsername(),userDetailsBean.findAllUserDetails());
        Picture authorProfilePicture = authorDetails != null ? authorDetails.getProfilePicture() : null;

        // Set attributes to pass to JSP
        req.setAttribute("post", post);
        req.setAttribute("authorProfilePicture", authorProfilePicture);
        req.setAttribute("userPicturesMap", userPicturesMap);
        req.setAttribute("reactions", allReactionsForPost);
        req.setAttribute("comments", allCommentsForPost);

        log.info("\n Exited viewPostComments.doGet with the following: " + post.getId() + " comments: " + allCommentsForPost.size() + " reactions: " + allReactionsForPost.size());

        req.getRequestDispatcher("/WEB-INF/userPages/post/viewPost.jsp").forward(req, resp);
    }
}
