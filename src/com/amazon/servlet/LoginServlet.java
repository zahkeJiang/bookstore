package com.amazon.servlet;

import com.amazon.common.Status;
import com.amazon.config.GlobalStatus;
import com.amazon.entity.User;
import com.amazon.util.DBUtil;
import com.amazon.util.GsonUtil;
import com.amazon.util.MD5Utils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class LoginServlet extends HttpServlet {
    Status status;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setHeader("content-type","text/html;charset=UTF-8");
        PrintWriter writer = resp.getWriter();
        String uPhone = req.getParameter("uPhone");
        String uPassword = req.getParameter("uPassword");
        System.out.println("uPhone =" + uPhone);
        System.out.println("uPassword =" + uPassword);
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            ResultSet resultSet = conn
                    .prepareStatement("SELECT * FROM user WHERE u_phone =" + uPhone)
                    .executeQuery();
            User user = new User();
            if (resultSet.next()) {
                int uId = resultSet.getInt("u_id");
                user.setuId(uId);
                user.setuName(resultSet.getString("u_name"));
                user.setuRegister(resultSet.getString("u_register"));
                user.setuSex(resultSet.getString("u_sex"));
                user.setuName(resultSet.getString("u_name"));
                user.setuPhone(resultSet.getString("u_phone"));
                user.setuQQ(resultSet.getString("u_qq"));
                String u_password = resultSet.getString("u_password");
                if (MD5Utils.getMD5(uPassword).equals(u_password)){
                    String cartSql = "SELECT COUNT(c_id) AS counts FROM cart WHERE u_id = " + uId;
                    ResultSet counts = conn
                            .prepareStatement(cartSql)
                            .executeQuery();
                    if (counts.next()){
                        user.setCarts(counts.getInt("counts"));
                    }
                    status = Status.success().add(user);
                }else {
                    status = Status.fail(GlobalStatus.PASSWORD_ERROR, "密码错误");
                }
            } else {
                status = Status.fail(GlobalStatus.NO_DATA,"账户不存在");
            }
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
