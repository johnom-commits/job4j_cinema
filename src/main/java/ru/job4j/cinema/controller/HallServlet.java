package ru.job4j.cinema.controller;

import ru.job4j.cinema.domain.Seat;
import ru.job4j.cinema.persistence.PsqlStore;
import ru.job4j.cinema.persistence.SeatsDAO;
import ru.job4j.cinema.service.FormTable;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = {"/hall"})
public class HallServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain");
        resp.setCharacterEncoding("UTF-8");
        String row = req.getParameter("row");
        String seat = req.getParameter("seat");
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        var htmlTable = FormTable.getTable(row, seat);

        writer.println(htmlTable);
        writer.flush();
    }
}
