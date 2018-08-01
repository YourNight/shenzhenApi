package com.bonc.shenzhen.util;

import net.sf.json.JSONObject;

import java.io.*;

/**
 * Created by liuhaolong on 2018/8/1.
 */
public class JsonReader {
    public static void parse(JSONObject jsonStr){
        jsonStr.keySet().stream().forEach(key ->{
            System.out.println(key+"   ----->   "+jsonStr.get(key).toString());
        });
        System.out.println(JSONObject.fromObject(jsonStr.get("444")));



    }

    public static JSONObject getJson(String path){
        JSONObject jsonFrom = null;
        try {
            String str = "";
            FileInputStream fileInputStream = new FileInputStream(path);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
            String s;
            while ((s = bufferedReader.readLine())!=null) {
                str = str +s ;
            }
            jsonFrom = JSONObject.fromObject(str);
            bufferedReader.close();
            fileInputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonFrom;
    }

    public static void main(String[] args) {
        parse(getJson("C:/Users/BONC/Desktop/018950/52e246be-6234-4acd-b4af-df7e9c7daa65.json"));
    }
}
