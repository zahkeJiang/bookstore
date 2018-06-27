package com.amazon.servlet;

import com.amazon.common.Status;
import com.amazon.config.GlobalStatus;
import com.amazon.entity.User;
import com.amazon.util.DBUtil;
import com.amazon.util.GsonUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CommonServlet extends HttpServlet {
    Status status;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setHeader("content-type","text/html;charset=UTF-8");
        PrintWriter writer = resp.getWriter();
        String uId = req.getParameter("uId");
        System.out.println("uId=" + uId);
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            ResultSet resultSet = conn
                    .prepareStatement("SELECT * FROM user WHERE u_id =" + uId)
                    .executeQuery();
            User user = new User();
            if (resultSet.next()) {
                user.setuId(resultSet.getInt("u_id"));
                user.setuName(resultSet.getString("u_name"));
                user.setuRegister(resultSet.getString("u_register"));
                user.setuSex(resultSet.getString("u_sex"));
                user.setuName(resultSet.getString("u_name"));
                user.setuPhone(resultSet.getString("u_phone"));
                user.setuQQ(resultSet.getString("u_qq"));
            }
            status = Status.success().add(user);
        } catch (SQLException e) {
            e.printStackTrace();
            status = Status.fail(GlobalStatus.SQL_ERROR, "查询异常");
        } finally {
            writer.println(GsonUtil.getJsonString(status));
            writer.flush();
            writer.close();
        }
    }
}
