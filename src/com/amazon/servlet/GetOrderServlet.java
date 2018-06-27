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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GetOrderServlet extends HttpServlet {
    Status status;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setHeader("content-type","text/html;charset=UTF-8");
        PrintWriter writer = resp.getWriter();
        String oId = req.getParameter("oId");
        System.out.println(oId);
        Connection conn;
        try {
            conn = DBUtil.getConnection();
            String orderInfo = "SELECT o_bussiness_id, o_count, o_date, o_status ," +
                    "o_deliver, o_deliverfee, u_pay, u_invoicetype,  u_invoicetitle " +
                    "FROM orders WHERE o_id = " + oId;
            ResultSet orderSet = conn
                    .prepareStatement(orderInfo)
                    .executeQuery();
            Order order = new Order();
            if (orderSet.next()){
                order.setBussinessId(orderSet.getString("o_bussiness_id"));
                order.setoCount(orderSet.getFloat("o_count"));
                order.setoDate(orderSet.getString("o_date"));
                order.setoStatus(orderSet.getString("o_status"));
                order.setoDeliver(orderSet.getString("o_deliver"));
                order.setoDeliverFee(orderSet.getFloat("o_deliverfee"));
                order.setuPay(orderSet.getString("u_pay"));
                order.setuInvoiceType(orderSet.getString("u_invoicetype"));
                order.setuInvoiceTitle(orderSet.getString("u_invoicetitle"));
            }

            List<Book> books = new ArrayList<>();
            String detailsBookId = "SELECT b_id, quantity FROM orderdetails WHERE o_id = " + oId;
            ResultSet bookIdSet = conn
                    .prepareStatement(detailsBookId)
                    .executeQuery();
            while (bookIdSet.next()){
                Book book = new Book();
                String b_id = bookIdSet.getString("b_id");
                book.setbId(b_id);
                book.setQuantity(bookIdSet.getInt("quantity"));

                String bookInfo = "SELECT b_name, b_unitPrice, b_star, b_picture " +
                        "FROM book WHERE b_id =" + "'" + b_id + "'";
                ResultSet resultSet = conn
                        .prepareStatement(bookInfo)
                        .executeQuery();
                while (resultSet.next()) {
                    book.setbName(resultSet.getString("b_name"));
                    book.setbUnitprice(resultSet.getDouble("b_unitPrice"));
                    book.setbStar(resultSet.getDouble("b_star"));
                    book.setbPicture(resultSet.getString("b_picture"));
                }
                books.add(book);
            }

            order.setBooks(books);

            status = Status.success().add(order);
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
