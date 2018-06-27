package com.amazon.servlet;

import com.amazon.common.Status;
import com.amazon.config.GlobalStatus;
import com.amazon.util.DBUtil;
import com.amazon.util.DateUtils;
import com.amazon.util.GsonUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddCartServlet extends HttpServlet {
    Status status;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setHeader("content-type","text/html;charset=UTF-8");
        PrintWriter writer = resp.getWriter();
        String uId = req.getParameter("uId");                    //用户ID
        String bId = req.getParameter("bId");                    //用户ID
        System.out.println("uId=" + uId);
        Connection conn;
        try {
            conn = DBUtil.getConnection();
            String addCart = "INSERT INTO cart(u_id, b_id) VALUES (?, ?)";
            PreparedStatement cartStat = conn.prepareStatement(addCart);
            cartStat.setInt(1, Integer.parseInt(uId));
            cartStat.setString(2, bId);
            cartStat.execute();
            status = Status.success();
        } catch (SQLException e) {
            e.printStackTrace();
            status = Status.fail(GlobalStatus.SQL_ERROR, "添加失败");
        } finally {
            writer.println(GsonUtil.getJsonString(status));
            writer.flush();
            writer.close();
        }
    }
}