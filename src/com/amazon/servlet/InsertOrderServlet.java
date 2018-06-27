package com.amazon.servlet;

import com.amazon.common.Status;
import com.amazon.config.GlobalStatus;
import com.amazon.util.*;

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

public class InsertOrderServlet extends HttpServlet {
    Status status;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setHeader("content-type","text/html;charset=UTF-8");
        PrintWriter writer = resp.getWriter();
        String uId = req.getParameter("uId");                    //用户ID
        String[] bIds = req.getParameterValues("bId");           //商品ID
        String[] quantity = req.getParameterValues("quantity");  //数量
        String uPay = req.getParameter("uPay");                  //支付类型
        String invoiceTitle = req.getParameter("invoiceTitle");  //发票抬头
        String invoiceType = req.getParameter("invoiceType");    //发票类型
        String deliverFee = req.getParameter("deliverFee");      //运费
        String aId = req.getParameter("aId");                    //地址ID
        String isCart = req.getParameter("isCart");             //是否为购物者内付款
        System.out.println("uId=" + uId);
        Connection conn;
        try {
            Float count = 0f;
            conn = DBUtil.getConnection();
            if ("1".equals(isCart)) {  //是从购物车结算，则清空购物车的商品
                String deleteCart = "DELETE FROM cart WHERE u_id = " + uId
                        + " AND b_id = ?";
                PreparedStatement cartStat = conn.prepareStatement(deleteCart);
                for (int i = 0; i < bIds.length; i++) {
                    cartStat.setString(1, bIds[i]);
                    cartStat.execute();
                }
            }

            //通过书籍ID获取书籍价格
            String bookPrice = "SELECT b_unitPrice FROM book WHERE b_id=?";
            for (int i = 0; i < bIds.length; i++) {
                PreparedStatement priceStat = conn.prepareStatement(bookPrice);
                priceStat.setString(1, bIds[i]);
                ResultSet resultSet = priceStat.executeQuery();
                if (resultSet.next()) {
                    float b_unitPrice = resultSet.getFloat("b_unitPrice");
                    count += b_unitPrice * Float.valueOf(quantity[i]);
                }else {
                    status = Status.fail(GlobalStatus.NO_DATA,"商品详情出错");
                }
            }
            //生成订单号
            String bussinessId = DateUtils.getSystemTimeInMM();
            //生成订单
            String sql = "INSERT INTO " +
                    "orders(o_bussiness_id, o_count, u_id, a_id, o_date, o_status, o_deliver, " +
                    "o_deliverfee, u_pay, u_invoicetype, u_invoicetitle)" +
                    " VALUES (?, ?, ?, ?, now(), ?, ?, ?, ?, ?, ?)";
            PreparedStatement orderStat = conn.prepareStatement(sql);
            orderStat.setString(1, bussinessId);
            orderStat.setFloat(2, count);
            orderStat.setInt(3, Integer.parseInt(uId));
            orderStat.setInt(4, Integer.parseInt(aId));
            orderStat.setString(5, "未发货");
            orderStat.setString(6, "快递送货上门");
            orderStat.setFloat(7, Float.parseFloat(deliverFee));
            orderStat.setString(8, uPay);
            orderStat.setString(9, invoiceType);
            orderStat.setString(10, invoiceTitle);
            orderStat.execute();

            //获取订单号
            Integer oId = null;
            String getoId = "SELECT o_id FROM orders WHERE o_bussiness_id = " + bussinessId;
            PreparedStatement oIdStat = conn.prepareStatement(getoId);
            ResultSet resultSet = oIdStat.executeQuery();
            if (resultSet.next()) {
                oId = resultSet.getInt("o_id");
                System.out.println("oId="+oId);
            } else {
                status = Status.fail(GlobalStatus.SQL_ERROR, "创建订单失败");
            }

            //存入订单详情
            String orderDetail = "INSERT INTO orderdetails(o_id, b_id, preferential, quantity)" +
                    " VALUES (" + oId + ", ?, ?, ?)";
            for (int i = 0; i < bIds.length; i++) {
                PreparedStatement detailStat = conn.prepareStatement(orderDetail);
                detailStat.setString(1, bIds[i]);
                System.out.println("bId1"+bIds[i]);
                detailStat.setFloat(2, 1);
                detailStat.setInt(3, Integer.parseInt(quantity[i]));
                detailStat.execute();
            }
            status = Status.success();
        } catch (SQLException e) {
            e.printStackTrace();
            status = Status.fail(GlobalStatus.SQL_ERROR, "存入失败");
        } finally {
            writer.println(GsonUtil.getJsonString(status));
            writer.flush();
            writer.close();
        }
    }
}