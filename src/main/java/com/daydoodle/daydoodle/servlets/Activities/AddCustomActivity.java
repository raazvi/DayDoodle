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

        customActivityBean.addCustomActivity(user.getUsername(), name, description);
        log.info("\n Exited AddCustomActivity.doPost \n");
        resp.sendRedirect(req.getContextPath() + "/Profile");
    }
}