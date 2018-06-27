package com.amazon.servlet;

import com.amazon.common.Status;
import com.amazon.config.GlobalStatus;
import com.amazon.entity.Book;
import com.amazon.entity.Cart;
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

public class GetCartServlet extends HttpServlet {
    Status status;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setHeader("content-type","text/html;charset=UTF-8");
        PrintWriter writer = resp.getWriter();
        String uId = req.getParameter("uId");                    //用户ID
        System.out.println("uId=" + uId);
        Connection conn;
        try {
            conn = DBUtil.getConnection();
            String getCart = "SELECT  c.c_id, b.b_id, b.b_name, b.b_unitPrice, b.b_star, b.b_picture " +
                    "FROM cart c, book b WHERE c.u_id = ? AND c.b_id = b.b_id";
            PreparedStatement cartStat = conn.prepareStatement(getCart);
            cartStat.setInt(1, Integer.parseInt(uId));
            ResultSet resultSet = cartStat.executeQuery();
            List<Cart> carts = new ArrayList<>();
            while (resultSet.next()){
                Book book = new Book();
                Cart cart = new Cart();
                cart.setuId(Integer.valueOf(uId));
                cart.setcId(resultSet.getInt("c_id"));
                cart.setbId(resultSet.getString("b_id"));
                book.setbName(resultSet.getString("b_name"));
                book.setbUnitprice(resultSet.getDouble("b_unitPrice"));
                book.setbStar(resultSet.getDouble("b_star"));
                book.setbPicture(resultSet.getString("b_picture"));
                cart.setBook(book);

                carts.add(cart);
            }
            status = Status.success().add(carts);
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