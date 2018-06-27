package com.amazon.servlet;

import com.amazon.common.Status;
import com.amazon.config.GlobalStatus;
import com.amazon.util.DBUtil;
import com.amazon.util.GsonUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RmoveCartServlet extends HttpServlet {
    Status status;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setHeader("content-type","text/html;charset=UTF-8");
        PrintWriter writer = resp.getWriter();
        String cId = req.getParameter("cId");                    //购物车ID
        System.out.println("cId=" + cId);
        Connection conn;
        try {
            conn = DBUtil.getConnection();
            String removeCart = "DELETE FROM cart WHERE c_id=? ";
            PreparedStatement cartStat = conn.prepareStatement(removeCart);
            cartStat.setInt(1, Integer.parseInt(cId));
            cartStat.execute();
            status = Status.success();
        } catch (SQLException e) {
            e.printStackTrace();
            status = Status.fail(GlobalStatus.SQL_ERROR, "移除失败");
        } finally {
            writer.println(GsonUtil.getJsonString(status));
            writer.flush();
            writer.close();
        }
    }
}