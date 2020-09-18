package com.airconsole.wm.utils;

import com.google.gson.Gson;

public class GsonUtils {
    private static final Gson gson = new Gson();

    public static String toJson(Object object){
        return gson.toJson(object);
    }

    public static <T> T fromJson(String json, Class<T> clazz){
        T obj = null;
        try{
            obj = gson.fromJson(json, clazz);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return obj;
    }
}
