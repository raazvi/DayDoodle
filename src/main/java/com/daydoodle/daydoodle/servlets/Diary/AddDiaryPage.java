package com.daydoodle.daydoodle.servlets.Diary;

import java.io.IOException;

import com.daydoodle.daydoodle.ejb.DiaryPageBean;
import com.daydoodle.daydoodle.entities.User;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.logging.Logger;

@WebServlet(name = "AddDiaryPage", value = "/AddDiaryPage")
public class AddDiaryPage extends HttpServlet {

    private static final Logger log = Logger.getLogger(AddDiaryPage.class.getName());
    @Inject
    DiaryPageBean diaryPageBean;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("\n Entered AddDiaryPage.doGet \n");

        log.info("\n Exited AddDiaryPage.doGet \n");
        req.getRequestDispatcher("/WEB-INF/userPages/addDiaryPage.jsp").forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("\n Entered AddDiaryPage.doPost \n");

        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        String title = req.getParameter("title");
        String moodCategory = req.getParameter("moodCategory");
        String moodValue = req.getParameter("moodValue");
        String entryText = req.getParameter("moreDetails");

        // Combine moodCategory and moodValue
        String mood = moodCategory + "," + moodValue;

        diaryPageBean.createDiaryPage(title,entryText,user.getUsername(),mood);

        resp.sendRedirect(req.getContextPath() + "/Diary");

        log.info("\n Exited AddDiaryPage.doPost \n");
    }
}