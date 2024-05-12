package com.daydoodle.daydoodle.servlets;

import java.io.IOException;
import java.util.logging.Logger;

import com.daydoodle.daydoodle.ejb.AuthenticationBean;
import com.daydoodle.daydoodle.ejb.UserBean;
import com.daydoodle.daydoodle.entities.User;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "Login", value = "/Login")
public class Login extends HttpServlet {

    private static final Logger log = Logger.getLogger(Login.class.getName());

    @Inject
    UserBean userBean;

    @Inject
    AuthenticationBean authenticationBean;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("\n Entered Login.doGet method \n");

        log.info("\n Exited Login.doGet method \n");
        req.getRequestDispatcher("/WEB-INF/components/forms/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("\n Entered Login.doPost method \n");

        String username = req.getParameter("j_username");
        String password = req.getParameter("j_password");

        User user = authenticationBean.login(username, password);

        if (user != null) {
            // Authentication successful, store user in session
            req.getSession().setAttribute("user", user);
            log.info("User logged in - Username: " + user.getUsername() + ", Role: " + user.getRole());

            if (user.isFirstLogin()) {
                userBean.setFirstLoginFalse(username);
                log.info("\n Exited Login.doPost method --> first login \n");
                resp.sendRedirect(req.getContextPath() + "/UpdateUserDetails");
            } else {
                // Log before redirecting to Feed page
                log.info("Redirecting to Feed page - Username: " + user.getUsername() + ", Role: " + user.getRole());

                log.info("\n Exited Login.doPost method --> not first login \n");
                resp.sendRedirect(req.getContextPath() + "/Feed");
            }
        } else {
            log.info("\n Exited Login.doPost method -> credentials invalid (returning to login) \n");
            req.setAttribute("message", "Username or password incorrect");
            req.setAttribute("j_username", username);
            req.getRequestDispatcher("/WEB-INF/components/forms/login.jsp").forward(req, resp);
        }
    }
}
