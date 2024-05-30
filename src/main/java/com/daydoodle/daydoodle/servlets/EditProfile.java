package com.daydoodle.daydoodle.servlets;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Base64;

import com.daydoodle.daydoodle.common.UserDetailsDto;
import com.daydoodle.daydoodle.ejb.UserDetailsBean;
import com.daydoodle.daydoodle.entities.User;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.util.logging.Logger;

@WebServlet(name = "EditProfile", value = "/EditProfile")
@MultipartConfig
public class EditProfile extends HttpServlet {

    private static final Logger log = Logger.getLogger(EditProfile.class.getName());

    @Inject
    UserDetailsBean userDetailsBean;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("\n Entered EditProfile.doGet \n");
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        UserDetailsDto thisUser = userDetailsBean.getUserDetailsByUsername(user.getUsername(), userDetailsBean.findAllUserDetails());
        req.setAttribute("user", thisUser);
        log.info("\n Exited EditProfile.doGet \n");
        req.getRequestDispatcher("/WEB-INF/userPages/editProfile.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("\n Entered EditProfile.doPost \n");
        String username = req.getParameter("username");
        String firstName = req.getParameter("firstname");
        String lastName = req.getParameter("lastname");
        String nickname = req.getParameter("nickname");
        String location = req.getParameter("location");
        String birthDateStr = req.getParameter("birthdate");
        String zodiacSign = req.getParameter("zodiacSign");
        String pronouns = req.getParameter("pronouns");
        String description = req.getParameter("description");
        byte[] profilePicture = null;
        String imageFormat = req.getParameter("imageFormat");

        Part profilePicturePart = req.getPart("profilePicture");
        if (profilePicturePart != null && profilePicturePart.getSize() > 0) {
            profilePicture = profilePicturePart.getInputStream().readAllBytes();
        }

        //Update the user details
        if (username != null) {
            if (firstName != null) {
                userDetailsBean.updateFirstName(username, firstName);
            }
            if (lastName != null) {
                userDetailsBean.updateLastName(username, lastName);
            }
            if (nickname != null) {
                userDetailsBean.updateNickname(username, nickname);
            }
            if (location != null) {
                userDetailsBean.updateLocation(username, location);
            }
            if (birthDateStr != null && !birthDateStr.isEmpty()) {
                userDetailsBean.updateBirthDate(username, LocalDate.parse(birthDateStr));
            }
            if (zodiacSign != null) {
                userDetailsBean.updateZodiacSign(username, zodiacSign);
            }
            if (pronouns != null) {
                userDetailsBean.updatePronouns(username, pronouns);
            }
            if (description != null) {
                userDetailsBean.updateDescription(username, description);
            }
            if (profilePicture != null) {
                userDetailsBean.updateProfilePicture(username, profilePicture, imageFormat); // Pass image format along with image data
            }
        }

        log.info("\n Exited EditProfile.doPost \n");
        resp.sendRedirect(req.getContextPath() + "/Profile");
    }

}
