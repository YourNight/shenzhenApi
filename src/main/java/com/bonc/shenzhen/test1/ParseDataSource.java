package com.bonc.shenzhen.test1;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class ParseDataSource {

    GetAllFiles gal = new GetAllFiles();
    JSONArray json = null;

    public static void main(String[] args) throws IOException {
        JSONArray jsonArray = new ParseDataSource().parseJson();
        for (int j = 0; j < jsonArray.size(); j++) {
            Object o = jsonArray.get(j);
            JSONObject jsonObject2 = (JSONObject) jsonArray.get(j);
            Map<String, String> map = (Map<String, String>) jsonObject2;
            Set<String> set = map.keySet();
            String s = map.get(set.toString().replace("[", "").replace("]", ""));
            System.out.println(set.toString() + "--" + s);
            /*
            String id = jsonObject2.get("id").toString();
            System.out.println(id);*/
            System.out.println(jsonObject2.toString() + j + "++++++++++++++++++++++++++");
            System.out.println(o.toString() + j + "--------------------------------");
        }
    }

    /**
     * @Description: 遍历解析文件
     * @Param: []
     * @return: net.sf.json.JSONArray
     */
    //@RequestMapping("/parseJson")
    public JSONArray parseJson() throws IOException {
        List<String> list2 = gal.getJsonFiles2();
        for (String x : list2) {
            json = getJson(x);
        }
        return json;
    }

    List<Object> list = new ArrayList<>();

    /**
     * @Description: 解析文件
     * @Param: [path]
     * @return: net.sf.json.JSONArray
     */
    public JSONArray getJson(String path) throws IOException {
        JSONArray object = null;
        String str = "";
        String s;
        FileInputStream fileInputStream = new FileInputStream(path);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
        while ((s = bufferedReader.readLine()) != null) {
            str = str + s;
        }
        JSONObject jsonObject = JSONObject.fromObject(str);
        String id = jsonObject.get("id").toString();
        String name = jsonObject.get("name").toString();
        Map map = new HashMap();
        map.put(id, name);
        list.add(map);
        object = JSONArray.fromObject(list);
        System.out.println(object.toString());
        return object;
    }
}


