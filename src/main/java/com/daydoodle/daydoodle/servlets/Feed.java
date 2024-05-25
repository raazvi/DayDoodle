package com.daydoodle.daydoodle.servlets;

import java.io.IOException;

import com.daydoodle.daydoodle.common.*;
import com.daydoodle.daydoodle.ejb.*;
import com.daydoodle.daydoodle.entities.CustomActivity;
import com.daydoodle.daydoodle.entities.Picture;
import com.daydoodle.daydoodle.entities.User;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@WebServlet(name = "Feed", value = "/Feed")
public class Feed extends HttpServlet {

    private static final Logger log = Logger.getLogger(Feed.class.getName());

    @Inject
    PostBean postBean;
    @Inject
    FriendshipBean friendshipBean;
    @Inject
    FunFactBean funFactBean;
    @Inject
    UserDetailsBean userDetailsBean;
    @Inject
    PictureBean pictureBean;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        List<PostDto> allPosts = postBean.findAllPosts();

        List<FriendshipDto> userFriendships = friendshipBean.findFriendshipsByUser(user.getUsername(),friendshipBean.findAllFriendships());
        List<String> userFriends=friendshipBean.findUserFriends(user.getUsername(),userFriendships);

        List<PostDto> friendsPosts=postBean.findFriendsPosts(userFriends,allPosts);
        List<UserDetailsDto> findUserDetails=userDetailsBean.findUserDetailsByUsernames(userFriends);

        FunFactDto funFact = funFactBean.getRandomFunFact();
        List<UserDetailsDto> userDetails= userDetailsBean.findUserDetailsByUsernames(userFriends);
        List<PictureDto> friendsProfilePictures=new ArrayList<>();
        for(UserDetailsDto ud:userDetails) {
            friendsProfilePictures.add(new PictureDto(ud.getProfilePicture().getId(),ud.getProfilePicture().getImageData(),ud.getProfilePicture().getImageName(),ud.getProfilePicture().getImageFormat()));
        }

        // Mapat poza la username si apoi trimis pe feed unde se afiseaza poza in functie de username

        req.setAttribute("currentUser", user);
        req.setAttribute("userDetails", userDetails);
        req.setAttribute("funFact", funFact);
        req.setAttribute("posts",friendsPosts);
        req.getRequestDispatcher("/WEB-INF/userPages/feed.jsp").forward(req,resp);
    }
}