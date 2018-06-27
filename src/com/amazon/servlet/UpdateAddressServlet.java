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

public class UpdateAddressServlet extends HttpServlet {
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
            String addAddress = "UPDATE address SET zipCode = ?, province = ?, " +
                    "country = ?, township = ?, street = ?, t_number = ?, remarks = ?" +
                    "WHERE u_id = ?";
            PreparedStatement cartStat = conn.prepareStatement(addAddress);
            cartStat.setString(1, zipCode);
            cartStat.setString(2, province);
            cartStat.setString(3, country);
            cartStat.setString(4, township);
            cartStat.setString(5, street);
            cartStat.setString(6, tNumber);
            cartStat.setString(7, remarks);
            cartStat.setInt(8, Integer.parseInt(uId));
            cartStat.execute();
            status = Status.success();
        } catch (SQLException e) {
            e.printStackTrace();
            status = Status.fail(GlobalStatus.SQL_ERROR, "更新失败");
        } finally {
            writer.println(GsonUtil.getJsonString(status));
            writer.flush();
            writer.close();
        }
    }
}