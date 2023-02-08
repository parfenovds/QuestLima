package com.javarush.parfenov.controller;

import com.javarush.parfenov.util.ConnectionManager;
import com.javarush.parfenov.util.JSP;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "IndexServlet", value = "")
public class IndexServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSP.forward(request, response, "index");
    }
    @Override
    public void destroy() {
        closeConnectionPool();
        super.destroy();
    }
    private void closeConnectionPool() {
        ConnectionManager.closePool();
    }
}
