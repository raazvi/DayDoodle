package com.daydoodle.daydoodle.servlets;

import java.io.IOException;

import com.daydoodle.daydoodle.common.DiaryPageDto;
import com.daydoodle.daydoodle.ejb.DiaryPageBean;
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

@WebServlet(name = "Diary", value = "/Diary")
public class Diary extends HttpServlet {

    private static final Logger log = Logger.getLogger(Diary.class.getName());

    @Inject
    DiaryPageBean diaryPageBean;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("\n Entered Diary.doGet method. \n");
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        List<DiaryPageDto> diaryPages = diaryPageBean.findPagesByUser(user.getUsername(), diaryPageBean.findAllDiaryPages());
        req.setAttribute("pages", diaryPages);

        req.getRequestDispatcher("/WEB-INF/userPages/diary.jsp").forward(req, resp);

    }
}