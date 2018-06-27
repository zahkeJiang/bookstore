package com.amazon.servlet;

import com.amazon.common.Status;
import com.amazon.config.GlobalStatus;
import com.amazon.util.DBUtil;
import com.amazon.util.GsonUtil;
import com.amazon.util.MD5Utils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ForgetPassServlet extends HttpServlet {
    Status status;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setHeader("content-type","text/html;charset=UTF-8");
        PrintWriter writer = resp.getWriter();
        String uPhone = req.getParameter("uPhone");
        String uPassword = req.getParameter("uPassword");
        System.out.println("uPhone=" + uPhone);
        System.out.println("uPassword=" + uPassword);
        Connection conn;
        try {
            conn = DBUtil.getConnection();
            String sql = "UPDATE user " +
                    "SET u_password = ? " +
                    " WHERE u_phone = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, MD5Utils.getMD5(uPassword));
            statement.setString(2, uPhone);
            statement.execute();
            status = Status.success();
        } catch (SQLException e) {
            e.printStackTrace();
            status = Status.fail(GlobalStatus.SQL_ERROR, "重置失败");
        } finally {
            writer.println(GsonUtil.getJsonString(status));
            writer.flush();
            writer.close();
        }
    }
}