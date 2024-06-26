package com.daydoodle.daydoodle.servlets;

import com.daydoodle.daydoodle.ejb.UserDetailsBean;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.logging.Logger;

@WebServlet(name = "UpdateUserDetails", value = "/UpdateUserDetails")
@MultipartConfig
public class UpdateUserDetails extends HttpServlet {

    private static final Logger log = Logger.getLogger(UpdateUserDetails.class.getName());

    @Inject
    UserDetailsBean userDetailsBean;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("\n Entered UpdateUserDetails.doGet \n");

        log.info("\n Exited UpdateUserDetails.doGet, redirecting to updateUserDetails.jsp \n");
        req.getRequestDispatcher("/WEB-INF/userPages/updateUserDetails.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        log.info("\n Entered UpdateUserDetails.doPost \n");

        String firstName = req.getParameter("firstname");
        String lastName = req.getParameter("lastname");
        String nickname = req.getParameter("nickname");
        String location = req.getParameter("location");
        String birthDateStr = req.getParameter("birthdate");
        String zodiacSign = req.getParameter("zodiacSign");
        String pronouns = req.getParameter("pronouns");
        String description = req.getParameter("description");
        String username = req.getParameter("username");

        LocalDate birthDate = null;
        if (birthDateStr != null && !birthDateStr.isEmpty()) {
            try {
                birthDate = LocalDate.parse(birthDateStr);
            } catch (DateTimeParseException e) {
                // Handle invalid date format
                log.warning("Invalid birth date format: " + birthDateStr);
            }
        }

        // Process uploaded profile picture if needed
        Part profilePicturePart = req.getPart("profilePicture");

        // Update user details
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
            if (birthDate != null) {
                userDetailsBean.updateBirthDate(username, birthDate);
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

            if (profilePicturePart != null && profilePicturePart.getSize() > 0) {
                byte[] imageData = new byte[(int) profilePicturePart.getSize()];
                profilePicturePart.getInputStream().read(imageData);
                String imageFormat = req.getParameter("imageFormat"); // Retrieve image format from request
                userDetailsBean.updateProfilePicture(username, imageData, imageFormat);
            }
        }

        log.info("\n Exited UpdateUserDetails.doPost \n");
        resp.sendRedirect(req.getContextPath() + "/Profile");

    }
}