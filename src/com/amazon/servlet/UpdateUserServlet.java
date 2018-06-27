package com.amazon.servlet;

import com.amazon.common.Status;
import com.amazon.config.GlobalStatus;
import com.amazon.util.CodeUtil;
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
import java.util.ArrayList;
import java.util.List;

public class UpdateUserServlet extends HttpServlet {
    Status status;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setHeader("content-type","text/html;charset=UTF-8");
        PrintWriter writer = resp.getWriter();
        String uPhone = req.getParameter("uPhone");
        String uRegister = req.getParameter("uRegister");
        String uName = req.getParameter("uName");
        String uSex = req.getParameter("uSex");
        String uQQ = req.getParameter("uQQ");
        System.out.println("uPhone=" + uPhone);
        System.out.println("uRegister=" + uRegister);
        List<String> param = new ArrayList<>();
        if (!"".equals(uName) && uName != null) {
            param.add("u_name = '" + uName+"'");
        }
        if (!"".equals(uSex) && uSex != null) {
            param.add("u_sex = '" + uSex+"'");
        }
        if (!"".equals(uRegister) && uRegister != null) {
            param.add("u_register = '" + uRegister+"'");
        }
        if (!"".equals(uQQ) && uQQ != null) {
            param.add("u_qq = '" + uQQ+"'");
        }
        String sql1 = "";
        for (int i = 0; i < param.size(); i++) {
            if (i == param.size() - 1) {
                sql1 += param.get(i);
            } else {
                sql1 += param.get(i) + ",";
            }
        }
        Connection conn;
        try {
            conn = DBUtil.getConnection();
            String sql = "UPDATE user " +
                    "SET " + sql1 +
                    " WHERE u_phone = ?";
            System.out.println(sql);
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, uPhone);
            statement.execute();
            status = Status.success();
        } catch (SQLException e) {
            e.printStackTrace();
            status = Status.fail(GlobalStatus.SQL_ERROR, "修改失败");
        } finally {
            writer.println(GsonUtil.getJsonString(status));
            writer.flush();
            writer.close();
        }
    }
}