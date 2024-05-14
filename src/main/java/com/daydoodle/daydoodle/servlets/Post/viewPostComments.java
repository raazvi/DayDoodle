package com.daydoodle.daydoodle.servlets.Post;

import java.io.IOException;

import com.daydoodle.daydoodle.common.PostCommentDto;
import com.daydoodle.daydoodle.common.PostDto;
import com.daydoodle.daydoodle.common.PostReactionDto;
import com.daydoodle.daydoodle.ejb.PostBean;
import com.daydoodle.daydoodle.ejb.PostCommentBean;
import com.daydoodle.daydoodle.ejb.PostReactionBean;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.logging.Logger;

@WebServlet(name = "viewPostComments", value = "/viewPostComments")
public class viewPostComments extends HttpServlet {

    private static final Logger log = Logger.getLogger(viewPostComments.class.getName());

    @Inject
    PostBean postBean;
    @Inject
    PostReactionBean postReactionBean;
    @Inject
    PostCommentBean postCommentBean;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String postIdStr = req.getParameter("postId");
        Long postId = Long.parseLong(postIdStr);
        log.info("\n Entered viewPostComments.doGet with postId: " + postIdStr);

        // Fetch the post, comments, and reactions
        PostDto post = postBean.findPostById(postId);
        List<PostReactionDto> allReactionsForPost = postReactionBean.findAllReactionsForPost(postId);
        List<PostCommentDto> allCommentsForPost = postCommentBean.findAllCommentsForPost(postId);

        // Set attributes to pass to JSP
        req.setAttribute("post", post);
        req.setAttribute("reactions", allReactionsForPost);
        req.setAttribute("comments", allCommentsForPost);

        log.info("\n Exited viewPostComments.doGet with the following: " + post.getId() + " comments: " + allCommentsForPost.size() + " reactions: " + allReactionsForPost.size());

        req.getRequestDispatcher("/WEB-INF/userPages/post/postComments.jsp").forward(req, resp);
    }

}