package com.daydoodle.daydoodle.servlets.Post;

import java.io.IOException;

import com.daydoodle.daydoodle.ejb.PostBean;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.logging.Logger;

@WebServlet(name = "DeletePost", value = "/DeletePost")
public class DeletePost extends HttpServlet {

    private static final Logger log = Logger.getLogger(DeletePost.class.getName());

    @Inject
    PostBean postBean;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        log.info("\n Entered DeletePost.doGet \n");
        String postIdString = req.getParameter("postId");
        Long postId = Long.parseLong(postIdString);
        postBean.removePost(postId);
        log.info("\n Deleted post with id " + postId + "\n");
        resp.sendRedirect(req.getContextPath() + "/Profile");

    }
}