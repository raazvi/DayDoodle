package com.daydoodle.daydoodle.servlets;

import java.io.IOException;
import com.daydoodle.daydoodle.ejb.AuthenticationBean;
import com.daydoodle.daydoodle.ejb.UserBean;
import com.daydoodle.daydoodle.entities.User;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.logging.Logger;

@WebServlet(name = "ChangeDetails", value = "/ChangeDetails")
public class ChangeDetails extends HttpServlet {

    private static final Logger log = Logger.getLogger(ChangeDetails.class.getName());

    @Inject
    UserBean userBean;
    @Inject
    AuthenticationBean authenticationBean;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        String oldPassword = req.getParameter("oldPassword");
        String newPassword = req.getParameter("newPassword");
        String confirmNewPassword = req.getParameter("confirmNewPassword");
        String detail = req.getParameter("detail");

        if ("username".equals(detail)) {
            // Update username logic here (not implemented in the provided code)
        } else if ("password".equals(detail)) {
            oldPassword = authenticationBean.encryptPassword(oldPassword);
            if (oldPassword.equals(user.getPassword())) {
                if (newPassword != null && newPassword.equals(confirmNewPassword)) {
                    try {
                        userBean.updatePassword(user.getUsername(), newPassword);
                        resp.getWriter().write("Password updated successfully.");
                        resp.setStatus(HttpServletResponse.SC_OK);
                    } catch (Exception e) {
                        resp.getWriter().write("Failed to update password: " + e.getMessage());
                        resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    }
                } else {
                    resp.getWriter().write("New passwords do not match.");
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                }
            } else {
                resp.getWriter().write("Authentication failed.");
                resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
        } else {
            resp.getWriter().write("Invalid detail parameter.");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        resp.sendRedirect(req.getContextPath() + "/Profile");
    }
}
