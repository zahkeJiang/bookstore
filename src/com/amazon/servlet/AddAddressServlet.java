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

public class AddAddressServlet extends HttpServlet {
    Status status;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setHeader("content-type","text/html;charset=UTF-8");
        PrintWriter writer = resp.getWriter();
        String uId = req.getParameter("uId");                    //用户ID
        String zipCode = req.getParameter("zipCode");                    //邮编
        String province = req.getParameter("province");                    //省
        String country = req.getParameter("country");                    //国家
        String township = req.getParameter("township");                    //市、区
        String street = req.getParameter("street");                    //路
        String tNumber = req.getParameter("tNumber");                    //门牌号
        String remarks = req.getParameter("remarks");                    //备注
        System.out.println("uId=" + uId);
        Connection conn;
        try {
            conn = DBUtil.getConnection();
            String addAddress = "INSERT INTO address (u_id, zipCode, province, " +
                    "country, township, street, t_number, remarks) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement cartStat = conn.prepareStatement(addAddress);
            cartStat.setInt(1, Integer.parseInt(uId));
            cartStat.setString(2, zipCode);
            cartStat.setString(3, province);
            cartStat.setString(4, country);
            cartStat.setString(5, township);
            cartStat.setString(6, street);
            cartStat.setString(7, tNumber);
            cartStat.setString(8, remarks);
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