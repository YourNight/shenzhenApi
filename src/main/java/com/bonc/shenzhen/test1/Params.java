package com.bonc.shenzhen.test1;

import com.bonc.shenzhen.restfulApi.RestfulApi;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.IOException;
import java.util.*;

public class Params {

    ParseDataCollection dataCollectionParse = new ParseDataCollection();
    ParseDataSource parseDataSource = new ParseDataSource();


    public JSONArray getInterface() throws IOException {
        JSONArray jsonArray11 = null;
        JSONArray jsonArray1 = dataCollectionParse.parseJson();
        JSONArray jsonArray2 = parseDataSource.parseJson();

        RestfulApi.setDataCollection(jsonArray1);
        RestfulApi.setDataSource(jsonArray2);

        List<Object> list1 = new ArrayList<>();


        for (int i = 0; i < jsonArray1.size(); i++) {
            JSONObject jsonObject1 = (JSONObject) jsonArray1.get(i);
            String sourceDataSourceId = jsonObject1.get("sourceDataSourceId").toString();//数据源ID
            String sourceTable = jsonObject1.get("sourceTable").toString();//源表名
            String targetTable = jsonObject1.get("targetTable").toString();//目标表名
            String sourceTableSchema = jsonObject1.get("sourceTableSchema").toString();//schema
            for (int j = 0; j < jsonArray2.size(); j++) {
                JSONObject jsonObject2 = (JSONObject) jsonArray2.get(j);
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
        jsonArray11 = JSONArray.fromObject(list1);
        System.out.println(jsonArray11.toString());
        return jsonArray11;
    }

    public static void main(String[] args) throws IOException {
        new Params().getInterface();
    }

}
