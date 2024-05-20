package com.daydoodle.daydoodle.servlets.Post;

import java.io.IOException;

import com.daydoodle.daydoodle.common.ActivityDto;
import com.daydoodle.daydoodle.common.CustomActivityDto;
import com.daydoodle.daydoodle.common.PostDto;
import com.daydoodle.daydoodle.ejb.ActivityBean;
import com.daydoodle.daydoodle.ejb.CustomActivityBean;
import com.daydoodle.daydoodle.ejb.PostBean;
import com.daydoodle.daydoodle.entities.User;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.logging.Logger;

@WebServlet(name = "EditPost", value = "/EditPost")
public class EditPost extends HttpServlet {

    private static final Logger log = Logger.getLogger(EditPost.class.getName());

    @Inject
    PostBean postBean;
    @Inject
    ActivityBean activityBean;
    @Inject
    CustomActivityBean customActivityBean;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("\n Entered EditPost.doGet"+ req.getParameter("postId") +"\n");
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        String postIdStr = req.getParameter("postId");
        Long postId = Long.parseLong(postIdStr);
        List<ActivityDto> activities=activityBean.findAllActivities();
        List<CustomActivityDto> customActivities=customActivityBean.findAllCustomActivitiesByUsername(user.getUsername(),customActivityBean.findAllCustomActivities());
        PostDto post=postBean.findPostById(postId);
        req.setAttribute("activities",activities);
        req.setAttribute("customActivities",customActivities);
        req.setAttribute("post", post);
        req.getRequestDispatcher("/WEB-INF/userPages/post/editPost.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("\n Entered EditPost.doPost"+ req.getParameter("postId") +"\n");
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        String postIdStr = req.getParameter("postId");
        Long postId = Long.parseLong(postIdStr);
        String newCaption = req.getParameter("caption");

        String tempActivity=req.getParameter("activity");
        Long activityId, customActivityId;
        char activityType;

        activityType=tempActivity.charAt(0);

        log.info("\n Detecting activity type... \n");
        if(activityType=='a'){
            String activityIdString = tempActivity.substring(1);
            activityId=Long.parseLong(activityIdString);
            customActivityId=null;

        }else{
            String customActivityIdString = tempActivity.substring(1);
            activityId=null;
            customActivityId=Long.parseLong(customActivityIdString);;
        }

        if(activityId!=null){
            log.info("\n Editing a post with a predefined activity! \n");
            postBean.editPostWithActivity(postId,newCaption,activityId);
        }else {
            log.info("\n Editing a post with a custom activity! \n");
            postBean.editPostWithCustomActivity(postId,newCaption,customActivityId);
        }

        log.info("\n Exited AddPost.doPost method. \n");
        resp.sendRedirect(req.getContextPath()+"/Profile");

    }
}