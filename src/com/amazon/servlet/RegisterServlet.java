package com.amazon.servlet;

import com.amazon.common.Status;
import com.amazon.config.GlobalId;
import com.amazon.config.GlobalStatus;
import com.amazon.entity.User;
import com.amazon.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegisterServlet extends HttpServlet {
    Status status;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setHeader("content-type","text/html;charset=UTF-8");
        PrintWriter writer = resp.getWriter();
        String uPhone = req.getParameter("uPhone");
        String uPassword = req.getParameter("uPassword");
        String uName = req.getParameter("uName");
//        System.out.println("uhone");
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            ResultSet resultSet = conn
                    .prepareStatement("SELECT * FROM user WHERE u_phone =" + uPhone)
                    .executeQuery();
            if (resultSet.next()) {
                    status = Status.fail(GlobalStatus.HAS_REGISTERED,"该手机号已被注册");
            }else {
                String sql = "INSERT INTO " +
                        "user(u_phone, u_password, u_name, u_register, u_sex, u_qq, u_pay_one)" +
                        " VALUES (?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setString(1, uPhone);
                statement.setString(2, MD5Utils.getMD5(uPassword));
                statement.setString(3, uName);
                statement.setString(4, "用户" + CodeUtil.getRandom(7));
                statement.setString(5, "未知");
                statement.setString(6, "未知");
                statement.setString(7, "支付宝");
                statement.execute();
                status = Status.success();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            status = Status.fail(GlobalStatus.SQL_ERROR, "注册失败");
        } finally {
            writer.println(GsonUtil.getJsonString(status));
            writer.flush();
            writer.close();
        }
    }
}