package com.amazon.servlet;

import com.amazon.common.Status;
import com.amazon.config.GlobalStatus;
import com.amazon.entity.Book;
import com.amazon.entity.Order;
import com.amazon.util.DBUtil;
import com.amazon.util.GsonUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderListServlet extends HttpServlet {
    Status status;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setHeader("content-type","text/html;charset=UTF-8");
        PrintWriter writer = resp.getWriter();
        String uId = req.getParameter("uId");
        System.out.println(uId);
        Connection conn;
        try {
            conn = DBUtil.getConnection();
            String orderList = "SELECT o_id, o_bussiness_id, o_count, o_date, o_status ," +
                    "o_deliver, o_deliverfee, u_pay, u_invoicetype,  u_invoicetitle " +
                    "FROM orders WHERE u_id = " + uId;
            ResultSet orderSet = conn
                    .prepareStatement(orderList)
                    .executeQuery();
            List<Order> orders = new ArrayList<>();
            while (orderSet.next()) {
                Order order = new Order();
                order.setoId(orderSet.getInt("o_id"));
                order.setBussinessId(orderSet.getString("o_bussiness_id"));
                order.setoCount(orderSet.getFloat("o_count"));
                order.setoDate(orderSet.getString("o_date"));
                order.setoStatus(orderSet.getString("o_status"));
                order.setoDeliver(orderSet.getString("o_deliver"));
                order.setoDeliverFee(orderSet.getFloat("o_deliverfee"));
                order.setuPay(orderSet.getString("u_pay"));
                order.setuInvoiceType(orderSet.getString("u_invoicetype"));
                order.setuInvoiceTitle(orderSet.getString("u_invoicetitle"));

                orders.add(order);
            }
            status = Status.success().add(orders);
            if (!(orders.size()>0)){
                status = Status.fail(GlobalStatus.NO_DATA, "没有数据");
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
