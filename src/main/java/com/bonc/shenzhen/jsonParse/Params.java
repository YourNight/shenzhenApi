package com.bonc.shenzhen.jsonParse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.*;

public class Params {



    public static JSONArray getInterface(JSONArray dataSource,JSONArray dataCollection) {

        List<Object> list1 = new ArrayList<>();

        for (int i = 0; i < dataCollection.size(); i++) {
            JSONObject jsonObject1 = (JSONObject) dataCollection.get(i);
            String sourceDataSourceId = jsonObject1.get("sourceDataSourceId").toString();//数据源ID
            String sourceTable = jsonObject1.get("sourceTable").toString();//源表名
            String targetTable = jsonObject1.get("targetTable").toString();//目标表名
            String sourceTableSchema = jsonObject1.get("sourceTableSchema").toString();//schema
            for (int j = 0; j < dataSource.size(); j++) {
                JSONObject jsonObject2 = (JSONObject) dataSource.get(j);
                Map<String, String> map = (Map<String, String>) jsonObject2;
                Set<String> set = map.keySet();
                String resouceCode = map.get(set.toArray()[0].toString());
                String id = set.toArray()[0].toString();
                if (sourceDataSourceId.equals(id)) {
                    Map map1 = new HashMap();
                    map1.put("tableCode", sourceTable);
                    map1.put("schema", sourceTableSchema);
                    map1.put("resourceCode", resouceCode);
                    list1.add(map1);

                    Map map2 = new HashMap();
                    map2.put("tableCode", targetTable);
                    map2.put("schema", sourceTableSchema);
                    map2.put("resourceCode", resouceCode);
                    list1.add(map2);
                }
            }
        }
        return JSONArray.fromObject(list1);
    }

}
