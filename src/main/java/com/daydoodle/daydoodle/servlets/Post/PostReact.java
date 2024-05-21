package com.daydoodle.daydoodle.servlets.Post;

import java.io.IOException;

import com.daydoodle.daydoodle.common.PostDto;
import com.daydoodle.daydoodle.ejb.NotificationBean;
import com.daydoodle.daydoodle.ejb.PostBean;
import com.daydoodle.daydoodle.ejb.PostReactionBean;
import com.daydoodle.daydoodle.entities.PostReaction;
import com.daydoodle.daydoodle.entities.User;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.logging.Logger;

@WebServlet(name = "PostReact", value = "/PostReact")
public class PostReact extends HttpServlet {

    private static final Logger log = Logger.getLogger(PostReact.class.getName());

    @Inject
    PostReactionBean postReactionBean;
    @Inject
    PostBean postBean;
    @Inject
    NotificationBean notificationBean;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        String postIdString = req.getParameter("postId");
        String reaction= req.getParameter("reaction");
        Long postId = Long.parseLong(postIdString);
        PostDto thisPost=postBean.findPostById(postId);
        log.info("\n Entered PostReact.doGet for the post: "+postId+" with the reaction "+reaction+" \n");

        if(reaction != null){
            if(reaction.equals("like")){
                log.info("\n Added like reaction to post --> forwarding to Feed\n");
                postReactionBean.removeOtherReactionsFromUser(postId,user.getUsername());
                postReactionBean.addReactionToPost(postId, user.getUsername(), PostReaction.ReactionType.LIKE);
                notificationBean.sendReactOnPostNotification(user.getUsername(),thisPost.getAuthor().getUsername(),postId);
                resp.sendRedirect(req.getContextPath()+"/Feed");

            }else if (reaction.equals("star")){
                log.info("\n Added star reaction to post --> forwarding to Feed\n");
                postReactionBean.removeOtherReactionsFromUser(postId,user.getUsername());
                postReactionBean.addReactionToPost(postId, user.getUsername(), PostReaction.ReactionType.STAR);
                notificationBean.sendReactOnPostNotification(user.getUsername(),thisPost.getAuthor().getUsername(),postId);
                resp.sendRedirect(req.getContextPath()+"/Feed");
            }
        }


    }
}