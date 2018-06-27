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

public class BookServlet extends HttpServlet {
    Status status;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setHeader("content-type","text/html;charset=UTF-8");
        PrintWriter writer = resp.getWriter();
        String bId = req.getParameter("bId");
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            ResultSet resultSet = conn
                    .prepareStatement("SELECT * FROM book WHERE b_id =" + "\""+ bId +"\"")
                    .executeQuery();
            if (resultSet.next()) {
                Book book = new Book();
                book.setbId(resultSet.getString("b_id"));
                book.setbName(resultSet.getString("b_name"));
                book.setbUnitprice(resultSet.getDouble("b_unitPrice"));
                book.setbStar(resultSet.getDouble("b_star"));
                book.setbPicture(resultSet.getString("b_picture"));
                book.setbAuthorOne(resultSet.getString("b_author_one"));
                book.setbDiscription(resultSet.getString("b_discription"));
                book.setbIsbn(resultSet.getString("b_isbn"));
                book.setbType(resultSet.getString("b_type"));
                book.setbPublish(resultSet.getString("b_publish"));
                book.setbLanguage(resultSet.getString("b_language"));
                book.setbRank(resultSet.getLong("b_rank"));
                book.setbWeight(resultSet.getString("b_weight"));
                book.setbStatus(resultSet.getString("b_status"));
                book.setbSize(resultSet.getString("b_size"));
                book.setbFormat(resultSet.getLong("b_format"));
                book.setbAuthorTwo(resultSet.getString("b_author_two"));
                book.setbAuthorThree(resultSet.getString("b_author_three"));
                book.setbAuthorFour(resultSet.getString("b_author_four"));
                book.setbAuthorFive(resultSet.getString("b_author_five"));
                status = Status.success().add(book);
            }else {
                status = Status.fail(GlobalStatus.NO_DATA,"没有数据");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            status = Status.fail(GlobalStatus.SQL_ERROR, "查询异常");
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//            status = Status.fail(GlobalStatus.SERVER_ERROR, "Md5加密出错");
        } finally {
            writer.println(GsonUtil.getJsonString(status));
            writer.flush();
            writer.close();
        }
    }
}
