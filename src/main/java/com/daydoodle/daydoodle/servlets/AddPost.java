package com.daydoodle.daydoodle.servlets;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Logger;

import com.daydoodle.daydoodle.common.ActivityDto;
import com.daydoodle.daydoodle.common.CustomActivityDto;
import com.daydoodle.daydoodle.ejb.ActivityBean;
import com.daydoodle.daydoodle.ejb.CustomActivityBean;
import com.daydoodle.daydoodle.ejb.PostBean;
import com.daydoodle.daydoodle.entities.Picture;
import com.daydoodle.daydoodle.entities.User;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

@WebServlet(name = "AddPost", value = "/AddPost")
@MultipartConfig
public class AddPost extends HttpServlet {

    private static final Logger log = Logger.getLogger(AddPost.class.getName());

    @Inject
    PostBean postBean;
    @Inject
    ActivityBean activityBean;
    @Inject
    CustomActivityBean customActivityBean;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        log.info("\n Entered AddPost.doGet \n");
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        List<ActivityDto> activities = activityBean.findAllActivities();
        List<CustomActivityDto> customActivities = customActivityBean.findAllCustomActivitiesByUsername(user.getUsername(), customActivityBean.findAllCustomActivities());

        req.setAttribute("activities", activities);
        req.setAttribute("customActivities", customActivities);
        log.info("\n Exited AddPost.doGet \n");
        req.getRequestDispatcher("/WEB-INF/userPages/addPost.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("\n Entered AddPost.doPost method with the caption: " + req.getParameter("caption") + " \n");

        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        String username = user.getUsername();

        String caption = req.getParameter("caption");
        String tempActivity = req.getParameter("activity");
        Long activityId, customActivityId;
        char activityType;

        activityType = tempActivity.charAt(0);

        log.info("\n Detecting activity type... \n");
        if (activityType == 'a') {
            String activityIdString = tempActivity.substring(1);
            activityId = Long.parseLong(activityIdString);
            customActivityId = null;
        } else {
            String customActivityIdString = tempActivity.substring(1);
            activityId = null;
            customActivityId = Long.parseLong(customActivityIdString);
        }

        Picture picture = null;
        Part picturePart = req.getPart("picture");
        if (picturePart != null && picturePart.getSize() > 0) {
            InputStream inputStream = picturePart.getInputStream();
            byte[] imageData = inputStream.readAllBytes();
            String imageName = picturePart.getSubmittedFileName();
            String imageFormat = picturePart.getContentType();

            picture = new Picture();
            picture.setImageData(imageData);
            picture.setImageName(imageName);
            picture.setImageFormat(imageFormat);
        }

        if (activityId != null) {
            log.info("\n Creating a new post with a predefined activity! \n");
            postBean.createPostWithActivity(username, caption, activityId, picture);
        } else {
            log.info("\n Creating a new post with a custom activity! \n");
            postBean.createPostWithCustomActivity(username, caption, customActivityId, picture);
        }

        log.info("\n Exited AddPost.doPost method. \n");
        resp.sendRedirect(req.getContextPath() + "/Feed");
    }
}
