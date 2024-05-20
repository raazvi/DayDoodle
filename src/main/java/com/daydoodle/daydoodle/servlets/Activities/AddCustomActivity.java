package com.daydoodle.daydoodle.servlets.Activities;

import java.io.IOException;

import com.daydoodle.daydoodle.ejb.CustomActivityBean;
import com.daydoodle.daydoodle.entities.User;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.Objects;
import java.util.logging.Logger;

@WebServlet(name = "AddCustomActivity", value = "/AddCustomActivity")
public class AddCustomActivity extends HttpServlet {

    private static final Logger log = Logger.getLogger(AddCustomActivity.class.getName());
    @Inject
    CustomActivityBean customActivityBean;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        log.info("\n Entered AddCustomActivity.doPost \n");
        String name = req.getParameter("name");
        String description = req.getParameter("description");
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        String page=req.getParameter("page");
        String calendarId=req.getParameter("calendarId");
        String postId=req.getParameter("postId");
        Long calendarIdLong=0L;
        if(calendarId!=null) {
            calendarIdLong=Long.parseLong(calendarId);
        }


        customActivityBean.addCustomActivity(user.getUsername(), name, description);

        if(Objects.equals(page, "addPost")){
            log.info("\n Exited AddCustomActivity.doPost --> AddPost \n");
            resp.sendRedirect(req.getContextPath() + "/AddPost");
        }else if(Objects.equals(page, "addEvent")){
            log.info("\n Exited AddCustomActivity.doPost --> AddEvent \n");
            resp.sendRedirect(req.getContextPath() + "/AddEvent?calendarId="+calendarIdLong);
        }else if(Objects.equals(page,"editPost")){
            log.info("\n Exited AddCustomActivity.doPost --> Edit Post \n");
            resp.sendRedirect(req.getContextPath() + "/EditPost?postId="+postId);
        }else{
            log.info("\n Exited AddCustomActivity.doPost --> Profile \n");
            resp.sendRedirect(req.getContextPath() + "/Profile");
        }


    }
}