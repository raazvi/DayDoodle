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

import java.util.logging.Logger;

@WebServlet(name = "ViewDiaryPage", value = "/ViewDiaryPage")
public class ViewDiaryPage extends HttpServlet {

    private static final Logger log = Logger.getLogger(ViewDiaryPage.class.getName());
    @Inject
    DiaryPageBean diaryPageBean;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("\n Entered ViewDiaryPage.doGet \n");
        String pageId = req.getParameter("pageId");
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        DiaryPageDto diaryPage=diaryPageBean.findPageById(Long.parseLong(pageId),diaryPageBean.findAllDiaryPages());

        req.setAttribute("diaryEntry", diaryPage);
        req.getRequestDispatcher("/WEB-INF/userPages/viewDiaryPage.jsp").forward(req, resp);
    }
}