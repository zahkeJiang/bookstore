package com.amazon.servlet;

import com.amazon.common.Status;
import com.amazon.config.GlobalId;
import com.amazon.config.GlobalStatus;
import com.amazon.entity.User;
import com.amazon.util.CodeUtil;
import com.amazon.util.DBUtil;
import com.amazon.util.GsonUtil;
import com.amazon.util.JuheUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JuheServlet extends HttpServlet {
    Status status;
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setHeader("content-type","text/html;charset=UTF-8");
        PrintWriter writer = resp.getWriter();
        String uPhone = req.getParameter("uPhone");
        String isReset = req.getParameter("isReset");
        System.out.println("uPhone=" + uPhone);
        Connection conn = null;
        String code = CodeUtil.getRandom(4);
        try {
            conn = DBUtil.getConnection();
            ResultSet resultSet = conn
                    .prepareStatement("SELECT * FROM user WHERE u_phone =" + uPhone)
                    .executeQuery();
            if ("1".equals(isReset)){
                if (resultSet.next()) {
                    //发送短信验证码
                    status = JuheUtil.getRequest2(GlobalId.JUHE_TEMPLATE_ID, uPhone, code);
//                status = Status.success().add(code);
                }else {
                    status = Status.fail(GlobalStatus.NOT_REGISTERED,"该手机号没有注册");
                }
            }else {
                if (!resultSet.next()) {
                    //发送短信验证码
                    status = JuheUtil.getRequest2(GlobalId.JUHE_TEMPLATE_ID, uPhone, code);
//                status = Status.success().add(code);
                }else {
                    status = Status.fail(GlobalStatus.HAS_REGISTERED,"该手机号已被注册");
                }
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
