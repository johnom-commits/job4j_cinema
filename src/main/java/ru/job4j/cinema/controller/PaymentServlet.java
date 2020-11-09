package ru.job4j.cinema.controller;

import ru.job4j.cinema.service.Sell;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/pay"})
public class PaymentServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String row = req.getParameter("row");
        String seat = req.getParameter("seat");
        String username = req.getParameter("username");
        String phone = req.getParameter("phone");
        new Sell().sellTicket(row, seat, username, phone);
        resp.sendRedirect(req.getContextPath());
    }
}
