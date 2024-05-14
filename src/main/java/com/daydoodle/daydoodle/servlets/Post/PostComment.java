package com.daydoodle.daydoodle.servlets.Post;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.logging.Logger;

@WebServlet(name = "PostComment", value = "/PostComment")
public class PostComment extends HttpServlet {

    private static final Logger log = Logger.getLogger(PostComment.class.getName());

}