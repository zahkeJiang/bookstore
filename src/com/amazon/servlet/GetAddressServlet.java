package com.amazon.servlet;

import com.amazon.common.Status;
import com.amazon.config.GlobalStatus;
import com.amazon.entity.Address;
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
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetAddressServlet extends HttpServlet {
    Status status;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setHeader("content-type","text/html;charset=UTF-8");
        PrintWriter writer = resp.getWriter();
        String uId = req.getParameter("uId");                    //用户ID
        Connection conn;
        try {
            conn = DBUtil.getConnection();
            String getAddress = "SELECT a_id, zipCode, province, country, " +
                    "township, street, t_number, remarks FROM address WHERE u_id = ?";
            PreparedStatement addressStat = conn.prepareStatement(getAddress);
            addressStat.setInt(1, Integer.parseInt(uId));
            ResultSet addressSet = addressStat.executeQuery();
            Address address = new Address();
            if (addressSet.next()){
                address.setaId(addressSet.getInt("a_id"));
                address.setCountry(addressSet.getString("country"));
                address.setProvince(addressSet.getString("province"));
                address.setRemarks(addressSet.getString("remarks"));
                address.setStreet(addressSet.getString("street"));
                address.setZipcode(addressSet.getString("zipCode"));
                address.settNumber(addressSet.getString("t_number"));
                address.setTownship(addressSet.getString("township"));
                status = Status.success().add(address);
            }else {
                status = Status.fail(GlobalStatus.NO_DATA, "没有数据");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            status = Status.fail(GlobalStatus.SQL_ERROR, "获取失败");
        } finally {
            writer.println(GsonUtil.getJsonString(status));
            writer.flush();
            writer.close();
        }
    }
}