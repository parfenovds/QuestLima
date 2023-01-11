package com.javarush.khmelov.lesson14.controller;

import com.javarush.khmelov.lesson14.entity.Role;
import com.javarush.khmelov.lesson14.util.Go;
import com.javarush.khmelov.lesson14.util.Jsp;
import com.javarush.khmelov.lesson14.util.Key;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@SuppressWarnings("serial")
@WebServlet(name = "IndexServlet", value = Go.ROOT)
public class IndexServlet extends HttpServlet {

    @Override
    public void init(ServletConfig config) throws ServletException {
        config.getServletContext().setAttribute(Key.ROLES, Role.values());
        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Jsp.forward(req, resp, Key.INDEX);
    }
}
