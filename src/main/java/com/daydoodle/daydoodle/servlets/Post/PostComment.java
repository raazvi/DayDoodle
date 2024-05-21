package com.daydoodle.daydoodle.servlets.Post;

import java.io.IOException;

import com.daydoodle.daydoodle.common.PostDto;
import com.daydoodle.daydoodle.ejb.NotificationBean;
import com.daydoodle.daydoodle.ejb.PostBean;
import com.daydoodle.daydoodle.ejb.PostCommentBean;
import com.daydoodle.daydoodle.entities.User;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.logging.Logger;

@WebServlet(name = "PostComment", value = "/PostComment")
public class PostComment extends HttpServlet {

    private static final Logger log = Logger.getLogger(PostComment.class.getName());

    @Inject
    PostCommentBean postCommentBean;
    @Inject
    NotificationBean notificationBean;
    @Inject
    PostBean postBean;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("\n Entered PostComment.doGet \n");
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        String postIdStr = req.getParameter("postId");
        Long postId = Long.parseLong(postIdStr);
        String commentContent = req.getParameter("commentContent");
        postCommentBean.addCommentToPost(postId, user.getUsername(), commentContent);
        PostDto thisPost=postBean.findPostById(postId);
        notificationBean.sendCommentOnPostNotification(user.getUsername(),thisPost.getAuthor().getUsername(),postId);

        log.info("\n Redirecting to viewPostComments with the postId: " + postId + "\n");
        resp.sendRedirect(req.getContextPath() + "/ViewPost?postId=" + postId);


    }
}