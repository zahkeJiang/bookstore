package com.amazon.servlet;

import com.amazon.common.Status;
import com.amazon.config.GlobalStatus;
import com.amazon.entity.Book;
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

public class SearchBookServlet extends HttpServlet {
    Status status;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setHeader("content-type","text/html;charset=UTF-8");
        PrintWriter writer = resp.getWriter();
        String bName =req.getParameter("bName");
        System.out.println("bName=" + bName);
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            ResultSet resultSet = conn
                    .prepareStatement("SELECT b_id, b_name FROM book WHERE b_name LIKE '%" + bName + "%'")
                    .executeQuery();
            List<Book> books = new ArrayList<>();
            while (resultSet.next()) {
                Book book = new Book();
                book.setbId(resultSet.getString("b_id"));
                System.out.println(resultSet.getString("b_name"));
                book.setbName(resultSet.getString("b_name"));

                books.add(book);
                status = Status.success().add(books);
            }
            if (!(books.size()>0)){
                status = Status.fail(GlobalStatus.NO_DATA, "数据出错");
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
