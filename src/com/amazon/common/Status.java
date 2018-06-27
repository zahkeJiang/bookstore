package com.amazon.common;

import java.util.HashMap;
import java.util.Map;

public class Status {

    //状态码
	private int status;

	private String msg;

    //用户要返回给浏览器的数据
    private Object data = new Object();

    public static Status success(){
        Status result = new Status();
        result.setStatus(0);
        result.setMsg("处理成功");
        return result;
    }

    public static Status fail(int status,String msg){
        Status result = new Status();
        result.setStatus(status);
        result.setMsg(msg);
        return result;
    }

    public Status add(Object value){
        this.setData(value);
        return this;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
