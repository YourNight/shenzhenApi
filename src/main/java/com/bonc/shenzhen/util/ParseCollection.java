package com.bonc.shenzhen.util;

import com.bonc.shenzhen.restfulApi.RestfulApi;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParseCollection {



    UnZipFromPath unZipFromPath = new UnZipFromPath();

    public static void main(String[] args) {

    }

    public JSONArray getInter(String collectionUrl) {
        JSONArray object = null;
        List<JSONObject> list = new ArrayList<>();
        list = unZipFromPath.unzip(collectionUrl);
        List<Object> return_list = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            Map map = new HashMap();
            map = list.get(i);
            JSONArray jsonArray = JSONArray.fromObject(map);
            for (int j = 0; j < jsonArray.size(); j++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(j);
                String SourceTable = jsonObject.getString("sourceTable");
                String TargetTable = jsonObject.getString("targetTable");
                String resourceCode = jsonObject.getString("sourceDataSourceId");
                String schema = jsonObject.getString("sourceTableSchema");
                Map map2 = new HashMap<>();
                map2.put("sourceTable", SourceTable);
                map2.put("targetTable", TargetTable);
                map2.put("sourceDataSourceId", resourceCode);
                map2.put("sourceTableSchema", schema);
                return_list.add(map2);
            }
        }
        object = JSONArray.fromObject(return_list);
        System.out.println(object.toString());
        return object;
    }



}
