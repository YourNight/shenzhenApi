package com.bonc.shenzhen.util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;

import java.util.*;

public class ParseTable {

    @Value("${datasource.url}")
    String datasourceUrl;

    UnZipFromPath unZipFromPath = new UnZipFromPath();

    public static void main(String[] args) {
        JSONArray jsonArray = new ParseTable().getData();
        for (int j = 0; j < jsonArray.size(); j++) {
            JSONObject jsonObject2 = (JSONObject) jsonArray.get(j);
            Map<String, String> map = (Map<String, String>) jsonObject2;
            Set<String> set = map.keySet();
            String s = map.get(set.toArray()[0].toString());
            System.out.println(set.toArray()[0].toString() + "--" + s);
            System.out.println(jsonObject2.toString() + j + "++++++++++++++++++++++++++");
        }
    }

    public JSONArray getData() {
        JSONArray object = null;
        List<Object> list2 = new ArrayList<>();
        List<JSONObject> list = new ArrayList<>();
        list = unZipFromPath.unzip(datasourceUrl);
        for (int i = 0; i < list.size(); i++) {
            Map map = new HashMap();
            map = list.get(i);

            JSONArray jsonArray = JSONArray.fromObject(map);
            for (int j = 0; j < jsonArray.size(); j++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(j);
                String id = jsonObject.get("id").toString();
                String name = jsonObject.get("name").toString();
                Map map2 = new HashMap();
                map2.put(id,name);
               /* map2.put("id", id);
                map2.put("name", name);*/
                list2.add(map2);
                object = JSONArray.fromObject(list2);
                System.out.println(object.toString());
            }
        }

        return object;
    }
}

