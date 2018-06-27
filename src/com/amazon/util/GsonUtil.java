package com.amazon.util;

import com.google.gson.Gson;

public class GsonUtil {

    public static String getJsonString(Object o){
        return new Gson().toJson(o);
    }

    public static Gson getGson(){
        return new Gson();
    }
}
